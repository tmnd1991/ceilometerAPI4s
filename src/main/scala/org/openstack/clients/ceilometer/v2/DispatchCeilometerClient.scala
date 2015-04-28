//package org.openstack.clients.ceilometer.v2
//
//import java.net.URL
//import java.util.Date
//
//import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
//import org.openstack.api.restful.ceilometer.v2.elements._
//import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
//import org.openstack.api.restful.ceilometer.v2.requests._
//import org.openstack.api.restful.ceilometer.v2.requests.ResourcesListGETRequestJsonConversion._
//import org.openstack.api.restful.ceilometer.v2.requests.MetersListGETRequestJsonConversion._
//import org.openstack.api.restful.ceilometer.v2.requests.MeterGETRequestJsonConversion._
//import org.openstack.api.restful.ceilometer.v2.requests.MeterStatisticsGETRequestJsonProtocol._
//import org.openstack.api.restful.keystone.v2.KeystoneTokenProvider
//import org.scalatest.path
//import org.slf4j.{LoggerFactory, Logger}
//import spray.json._
//import dispatch._, Defaults._
///**
// * Created by tmnd91 on 27/04/15.
// */
//class DispatchCeilometerClient(ceilometerUrl : URL,
//                               keystoneUrl : URL,
//                               tenantName : String,
//                               username : String,
//                               password : String,
//                               connectTimeout : Int = 10000,
//                               readTimeout : Int = 10000) extends ICeilometerClient{
//  override val logger: Logger = LoggerFactory.getLogger(this.getClass)
//  private val tokenProvider = KeystoneTokenProvider.getInstance(keystoneUrl, tenantName, username, password)
//  private val http = Http.configure(_.setAllowPoolingConnection(true).setConnectionTimeoutInMs(connectTimeout).setRequestTimeoutInMs(readTimeout))
//  val ceilometerHost = host(ceilometerUrl.getHost,ceilometerUrl.getPort)
//  /**
//   * @return a collection of resources filtered by the query params
//   */
//  override def listResources(q: Seq[Query]): Future[Seq[Resource]] = {
//    tokenProvider.tokenOption match{
//      case Some(s : String) =>
//        val req = ResourcesListGETRequest(q)
//        val jsonReq = req.toJson
//        val myReq = url(ceilometerUrl + req.relativeURL).
//          setHeader("X-Auth-Token",s).
//          setContentType("application/json","utf-8").
//          setBody(if (jsonReq.asJsObject.fields.nonEmpty) jsonReq.compactPrint
//        else "").GET
//        Future{
//          val resp = http(myReq).apply()
//          import spray.json.DefaultJsonProtocol._
//          resp.getResponseBody.parseJson.convertTo[List[Resource]]
//        }
//      case _ => throw new RuntimeException("cannot get Auth-Token")
//    }
//  }
//
//  /**
//   *
//   * @param meterName the name of the meter
//   * @return a collection of statistics, if an error occurs return None
//   */
//  override def getStatistics(meterName: String): Future[Seq[Statistics]] = {
//    tokenProvider.tokenOption match{
//      case Some(s : String) =>
//        val myReq = (ceilometerHost / "v2" / "meters" / meterName / "statistics").
//          setHeader("X-Auth-Token",s).GET
//        Future{
//          val resp = http(myReq).apply()
//          import spray.json.DefaultJsonProtocol._
//          resp.getResponseBody.parseJson.convertTo[List[Statistics]]
//        }
//      case _ => throw new RuntimeException("cannot get Auth-Token")
//    }
//  }
//
//  /**
//   * @param from
//   * @param to
//   * @param meterName the name of the meter the Statistics is relative to.
//   * @return Some statistics about that meter from a Date to another or None if an error occurs
//   */
//  override def getStatistics(meterName: String, from: Date, to: Date): Future[Seq[Statistics]] = {
//    if (to before from) throw new RuntimeException("from after date")
//    else{
//      import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
//      val queries = List(("timestamp" >>>> from),("timestamp" <<== to))
//      val request = MeterStatisticsGETRequest(meterName, queries)
//      val body = request.toJson.toString
//
//      tokenProvider.tokenOption match{
//        case Some(s : String) =>
//          val myReq = url(ceilometerUrl + request.relativeURL).
//            setHeader("X-Auth-Token",s).
//            setContentType("application/json","utf-8").
//            setBody(body).GET
//          Future{
//            val resp = http(myReq).apply()
//            import spray.json.DefaultJsonProtocol._
//            resp.getResponseBody.parseJson.convertTo[List[Statistics]]
//          }
//        case _ => throw new RuntimeException("cannot get Auth-Token")
//      }
//    }
//  }
//
//  /**
//   * @param from
//   * @param to
//   * @param meterName the name of the meter the Samples are relative to.
//   * @return Some Samples about that meter from a Date to another or None if an error occurs
//   */
//  override def getSamplesOfMeter(meterName: String, from: Date, to: Date): Future[Seq[OldSample]] = {
//    if (to before from) throw new RuntimeException("from after date")
//    else{
//      import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
//      val queries = List(("timestamp" >>>> from),("timestamp" <<== to))
//      val request = MeterGETRequest(meterName, Some(queries), 32)
//      tokenProvider.tokenOption match{
//        case Some(s : String) =>
//          val body = request.toJson.compactPrint
//          val myReq = url(ceilometerUrl + request.relativeURL).
//            setContentType("application/json","utf-8").
//            setHeader("X-Auth-Token",s).
//            setBody(body).GET
//          Future{
//            val resp = http(myReq).apply()
//            import spray.json.DefaultJsonProtocol._
//            resp.getResponseBody.parseJson.convertTo[List[OldSample]]
//          }
//        case _ => throw new RuntimeException("cannot get Auth-Token")
//      }
//    }
//  }
//
//  /**
//   * @param from
//   * @param to
//   * @param resource_id the resource_id of the meter the Samples are relative to.
//   * @return Some Samples about that resource from a Date to another or None if an error occurs
//   */
//  override def getSamplesOfResource(resource_id: String, from: Date, to: Date): Future[Seq[Sample]] = {
//    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
//    if (to before from) throw new RuntimeException("from after date")
//    else getSamplesOfResource(resource_id,("timestamp" >>>> from), ("timestamp" <<== to))
//  }
//
//  /**
//   * @param queries to be issued
//   * @param resource_id the id of the Resource the Samples are relative to.
//   * @return Some Samples about that meter from a Date to another or None if an error occurs
//   */
//  override def getSamplesOfResource(resource_id: String, queries: Query*): Future[Seq[Sample]] = {
//    import org.openstack.api.restful.ceilometer.v2.requests.SamplesGETRequestJsonConversion._
//    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
//    val request = SamplesGETRequest(Some(queries :+ ("resource_id" ==== resource_id)), 32)
//    tokenProvider.tokenOption match{
//      case Some(s : String) => {
//        val body = request.toJson.compactPrint
//        val myReq = url(ceilometerUrl + request.relativeURL).
//          setContentType("application/json","utf-8").
//          setHeader("X-Auth-Token",s).
//          setBody(body).GET
//        Future{
//          val resp = http(myReq).apply()
//          import spray.json.DefaultJsonProtocol._
//          resp.getResponseBody.parseJson.convertTo[List[Sample]]
//        }
//      }
//      case _ => throw new RuntimeException("cannot get Auth-Token")
//    }
//  }
//
//  /**
//   *
//   * @param q any number of queries to filter the meters
//   * @return a collection of meters filtered by the query params if an error occurs return None
//   */
//  override def listMeters(q: Seq[Query]): Future[Seq[Meter]] = {
//    val request = new MetersListGETRequest(q)
//    val body = if (q.isEmpty) ""
//    else request.toJson.compactPrint
//    tokenProvider.tokenOption match{
//      case Some(s : String) => {
//        val myReq = url(ceilometerUrl + request.relativeURL).
//          setContentType("application/json","utf-8").
//          setHeader("X-Auth-Token",s).GET
//        Future{
//          val resp = http(myReq).apply()
//          import spray.json.DefaultJsonProtocol._
//          resp.getResponseBody.parseJson.convertTo[Seq[Meter]]
//        }
//      }
//      case _ => throw new RuntimeException("cannot get Auth-Token")
//    }
//  }
//}
