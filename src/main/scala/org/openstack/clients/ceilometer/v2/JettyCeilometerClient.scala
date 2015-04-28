package org.openstack.clients.ceilometer.v2

import java.net.URL
import java.util.Date

import org.eclipse.jetty.client.{HttpClient, ContentExchange}
import org.eclipse.jetty.io.ByteArrayBuffer
import org.eclipse.jetty.util.thread.ExecutorThreadPool
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.elements._
import org.openstack.api.restful.ceilometer.v2.requests._
import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.requests.ResourcesListGETRequestJsonConversion._
import org.openstack.api.restful.ceilometer.v2.requests.MeterGETRequestJsonConversion._
import org.openstack.api.restful.ceilometer.v2.requests.MetersListGETRequestJsonConversion._
import org.openstack.api.restful.ceilometer.v2.requests.MeterStatisticsGETRequestJsonProtocol._
import org.openstack.api.restful.keystone.v2.KeystoneTokenProvider
import org.slf4j.{LoggerFactory, Logger}
import spray.json._
import it.unibo.ing.utils._
/**
 * Created by tmnd91 on 28/04/15.
 */
class JettyCeilometerClient(ceilometerUrl : URL,
keystoneUrl : URL,
tenantName : String,
username : String,
password : String,
connectTimeout : Int = 10000,
readTimeout : Int = 10000) extends ICeilometerClient {
  override val logger: Logger = LoggerFactory.getLogger(this.getClass)
  private val sample_size = 800
  private val responseBufferSize = 524288
  private val requestBufferSize = 16384
  private val tokenProvider = KeystoneTokenProvider.getInstance(keystoneUrl, tenantName, username, password)
  private val httpClient = new HttpClient()
  private val tPool = new ExecutorThreadPool(256)
  httpClient.setConnectTimeout(connectTimeout)
  httpClient.setThreadPool(tPool)
  httpClient.setMaxRedirects(1)
  httpClient.setRequestBufferSize(requestBufferSize)
  httpClient.setResponseBufferSize(responseBufferSize)
  httpClient.start()

  /**
   * @return a collection of resources filtered by the query params
   */
  override def listResources(q: Seq[Query]): Seq[Resource] = {
    tokenProvider.tokenOption match{
      case Some(s : String) =>
        val req = ResourcesListGETRequest(q)
        val jsonReq = req.toJson
        val uri = (ceilometerUrl / req.relativeURL).toURI
        val exchange = new ContentExchange()
        exchange.setURI(uri)
        exchange.setMethod("GET")
        exchange.setRequestHeader("X-Auth-Token",s)
        if (!jsonReq.asJsObject.fields.isEmpty)
          exchange.setRequestContent(new ByteArrayBuffer(jsonReq.compactPrint.getBytes))
        exchange.setTimeout(readTimeout)
        httpClient.send(exchange)
        val state = exchange.waitForDone()
        import spray.json.DefaultJsonProtocol._
        exchange.getResponseContent.parseJson.convertTo[List[Resource]]
      case _ => throw new RuntimeException("Cannot get Auth-Token")
    }
  }

  /**
   *
   * @param meterName the name of the meter
   * @return a collection of statistics, if an error occurs return None
   */
  override def getStatistics(meterName: String): Seq[Statistics] ={
    val uri = new URL(ceilometerUrl.toString / "/v2/meters/" / meterName / "/statistics").toURI
    tokenProvider.tokenOption match{
      case Some(s : String) =>
        val exchange = new ContentExchange()
        exchange.setURI(uri)
        exchange.setRequestHeader("X-Auth-Token",s)
        exchange.setMethod("GET")
        exchange.setTimeout(readTimeout)
        httpClient.send(exchange)
        val state = exchange.waitForDone()
        val body = exchange.getResponseContent
        import spray.json.DefaultJsonProtocol._
        body.parseJson.convertTo[List[Statistics]]
      case _ => throw new RuntimeException("Cannot get Auth-Token")
    }
  }

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Statistics is relative to.
   * @return Some statistics about that meter from a Date to another or None if an error occurs
   */
  override def getStatistics(meterName: String, from: Date, to: Date): Seq[Statistics] = {
    require(from before to)
    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
    val queries = List(("timestamp" >>>> from),("timestamp" <<== to))
    val request = MeterStatisticsGETRequest(meterName, queries)
    val body = request.toJson.toString
    val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
    tokenProvider.tokenOption match{
      case Some(s : String) =>
        val exchange = new ContentExchange()
        exchange.setURI(uri)
        exchange.setMethod("GET")
        exchange.setRequestHeader("X-Auth-Token",s)
        exchange.setRequestContentType("application/json")
        exchange.setRequestContent(new ByteArrayBuffer(body.getBytes))
        exchange.setTimeout(readTimeout)
        httpClient.send(exchange)
        val state = exchange.waitForDone()
        import spray.json.DefaultJsonProtocol._
        exchange.getResponseContent.parseJson.convertTo[List[Statistics]]
        case _ => throw new RuntimeException("Cannot get Auth-Token")
    }
  }

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  override def getSamplesOfMeter(meterName: String, from: Date, to: Date): Seq[OldSample] = {
    require(from before to)
    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
    val queries = List(("timestamp" >>>> from), ("timestamp" <<== to))
    val request = MeterGETRequest(meterName, Some(queries), responseBufferSize / sample_size)
    val uri = (ceilometerUrl / request.relativeURL).toURI
    tokenProvider.tokenOption match {
      case Some(s: String) =>
        val body = request.toJson.compactPrint
        val exchange = new ContentExchange()
        exchange.setURI(uri)
        exchange.setMethod("GET")
        exchange.setRequestHeader("X-Auth-Token", s)
        exchange.setRequestContentType("application/json")
        exchange.setRequestContent(new ByteArrayBuffer(body.getBytes))
        exchange.setTimeout(readTimeout)
        httpClient.send(exchange)
        val state = exchange.waitForDone()
        import spray.json.DefaultJsonProtocol._
        exchange.getResponseContent.parseJson.convertTo[List[OldSample]]
      case _ => throw new RuntimeException("Cannot get Auth-Token")

    }
  }

  /**
   * @param from
   * @param to
   * @param resource_id the resource_id of the meter the Samples are relative to.
   * @return Some Samples about that resource from a Date to another or None if an error occurs
   */
  override def getSamplesOfResource(resource_id: String, from: Date, to: Date): Seq[Sample] = {
    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
    require(from before to)
    getSamplesOfResource(resource_id,("timestamp" >>>> from), ("timestamp" <<== to))
  }

  /**
   * @param queries to be issued
   * @param resource_id the id of the Resource the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  override def getSamplesOfResource(resource_id: String, queries: Query*): Seq[Sample] = {
    import org.openstack.api.restful.ceilometer.v2.requests.SamplesGETRequestJsonConversion._
    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
    val request = SamplesGETRequest(Some(queries :+ ("resource_id" ==== resource_id)), responseBufferSize / sample_size)
    val uri = (ceilometerUrl / request.relativeURL).toURI
    tokenProvider.tokenOption match {
      case Some(s: String) =>
        val body = request.toJson.compactPrint
        val exchange = new ContentExchange()
        exchange.setURI(uri)
        exchange.setMethod("GET")
        exchange.setRequestHeader("X-Auth-Token", s)
        exchange.setRequestContentType("application/json")
        exchange.setRequestContent(new ByteArrayBuffer(body.getBytes))
        exchange.setTimeout(readTimeout)
        httpClient.send(exchange)
        val state = exchange.waitForDone()
        exchange.getResponseContent.parseJson.convertTo[List[Sample]]
      case _ => throw new RuntimeException("Cannot get Auth-Token")
    }
  }

  /**
   *
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if an error occurs return None
   */
  override def listMeters(q: Seq[Query]): Seq[Meter] = {
    val request = new MetersListGETRequest(q)
    val body = if (q.isEmpty) ""
    else request.toJson.compactPrint
    tokenProvider.tokenOption match{
      case Some(s : String) =>
        val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
        val exchange = new ContentExchange()
        exchange.setURI(uri)
        exchange.setMethod("GET")
        exchange.setRequestHeader("X-Auth-Token",s)
        exchange.setRequestContentType("application/json")
        exchange.setTimeout(readTimeout)
        httpClient.send(exchange)
        val state = exchange.waitForDone()
        import spray.json.DefaultJsonProtocol._
        exchange.getResponseContent.parseJson.convertTo[Seq[Meter]]
      case _ => throw new RuntimeException("Cannot get Auth-Token")
    }
  }
}
