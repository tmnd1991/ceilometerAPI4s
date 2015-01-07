package org.openstack.clients.ceilometer.v2

import java.net.URL
import java.util.Date
import java.util.concurrent.TimeUnit


import spray.json._
import spray.json.MyMod._

import org.eclipse.jetty.client.HttpClient
import org.eclipse.jetty.client.util.StringContentProvider
import org.eclipse.jetty.http.HttpMethod

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.elements._
import org.openstack.api.restful.ceilometer.v2.requests._


import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.requests.MetersListGETRequestJsonConversion._
import org.openstack.api.restful.ceilometer.v2.requests.MeterGETRequestJsonConversion._
import org.openstack.api.restful.ceilometer.v2.requests.ResourcesListGETRequestJsonConversion._

import org.openstack.api.restful.ceilometer.v2.requests.MeterStatisticsGETRequestJsonProtocol._
import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
import org.openstack.api.restful.keystone.v2.KeystoneTokenProvider

/**
 * High level object that offers services to query a ceilometer endpoint
 * @author Antonio Murgia
 * @version 26/11/14
 */

class CeilometerClient(ceilometerUrl : URL,
                       keystoneUrl : URL,
                       tenantName : String,
                       username : String,
                       password : String,
                       connectTimeout : Int = 10000,
                       readTimeout : Int = 10000) {
  private val oldsample_size = 800
  private val responseBufferSize = 524288
  private val requestBufferSize = 16384
  private val tokenProvider = KeystoneTokenProvider.getInstance(keystoneUrl, tenantName, username, password)
  private val httpClient = new HttpClient()
  httpClient.setConnectTimeout(connectTimeout)
  httpClient.setFollowRedirects(false)
  httpClient.setStopTimeout(readTimeout)
  httpClient.setRequestBufferSize(requestBufferSize)
  httpClient.setResponseBufferSize(responseBufferSize)
  httpClient.start()

  /**
   *
   * @param q any number of queries to filter the meters
   * @return tries to return a collection of meters filtered by the query params if an error occurs return None
   */
  def tryListMeters(q : Seq[Query]) : Option[Seq[Meter]] = {
    val request = new MetersListGETRequest(q)
    val body = if (q.isEmpty)
      ""
    else
      request.toJson.compactPrint
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
        val resp = httpClient.newRequest(uri).
          method(HttpMethod.GET).
          header("Content-Type","application/json").
          header("X-Auth-Token",s).
          content(new StringContentProvider(body)).
          timeout(readTimeout, TimeUnit.MILLISECONDS).
          send()
        try{
          val json = resp.getContentAsString.tryParseJson
          if (json != None){
            import spray.json.DefaultJsonProtocol._
            json.get.tryConvertTo[Seq[Meter]]
          }
          else
            None
        }
        catch{
          case t : Throwable => None
        }
      }
      case _ => None
    }
  }

  /**
   * @return Some collection of meters avaiable if an error occurs returns None
   */
  def tryListMeters : Option[Seq[Meter]] = tryListMeters(Seq.empty)

  /**
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if any error occurs an empty Seq is returned
   */
  def listMeters(q : Query*) : Seq[Meter] = tryListMeters(q).getOrElse(List.empty)

  /**
   * @return a collection of meters avaiable if an error occurs an empty Seq is returned
   */
  def listMeters : Seq[Meter] = tryListMeters.getOrElse(List.empty)

  def tryGetStatistics(meterName : String) : Option[Seq[Statistics]] = {
    val uri = new URL(ceilometerUrl.toString + "/v2/meters/" + meterName + "/statistics").toURI
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val resp = httpClient.newRequest(uri).
          method(HttpMethod.GET).
          header("X-Auth-Token",s).
          timeout(readTimeout, TimeUnit.MILLISECONDS).
          send
        val body = resp.getContentAsString
        val json = body.tryParseJson
        if (json != None) {
          import spray.json.DefaultJsonProtocol._
          val result = json.get.tryConvertTo[List[Statistics]]
          result
        }
        else None
      }
      case _ => None
    }
  }

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Statistics is relative to.
   * @return Some statistics about that meter from a Date to another or None if an error occurs
   */
  def tryGetStatistics(meterName : String, from : Date, to : Date) : Option[Seq[Statistics]] = {
    if (to before from) None
    else{
      import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
      val queries = List(("timestamp" >>>> from),("timestamp" <<== to))
      val request = MeterStatisticsGETRequest(meterName, queries)
      val body = request.toJson.toString
      val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
      tokenProvider.tokenOption match{
        case Some(s : String) => {
          val resp = httpClient.newRequest(uri).
            method(HttpMethod.GET).
            header("X-Auth-Token",s).
            header("Content-Type","application/json").
            content(new StringContentProvider(body)).
            timeout(readTimeout, TimeUnit.MILLISECONDS).
            send
          val json = resp.getContentAsString.tryParseJson
          if (json != None) {
            import spray.json.DefaultJsonProtocol._
            json.get.tryConvertTo[List[Statistics]]
          }
          else None
        }
        case _ => None
      }
    }
  }

  def getStatistics(meterName : String) : Seq[Statistics] = tryGetStatistics(meterName).getOrElse(List.empty)

  /**
   * @param from
   * @param to
   * @param meterName the name of the Meter the Statistics is relative to.
   * @return statistics about that meter from a Date to another or an empty Seq if an error occurs
   */
  def getStatistics(meterName : String, from : Date, to : Date) : Seq[Statistics] = tryGetStatistics(meterName, from, to).getOrElse(List.empty)

  /*
  def tryGetSamples(meterName : String, from : Date, to : Date) : Option[Seq[OldSample]] = {
    if (to before from) None
    else{
      import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
      val queries = List(("timestamp" >>>> from),("timestamp" <<== to))
      val request = MeterGETRequest(meterName, Some(queries), responseBufferSize/oldsample_size)
      val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
      tokenProvider.tokenOption match{
        case Some(s : String) => {
          val body = request.toJson.compactPrint
          val resp = httpClient.newRequest(uri).
            method(HttpMethod.GET).
            header("X-Auth-Token",s).
            header("Content-Type","application/json").
            content(new StringContentProvider(body)).
            timeout(readTimeout, TimeUnit.MILLISECONDS).
            send
          val json = resp.getContentAsString.tryParseJson
          if (json.isDefined) {
            import spray.json.DefaultJsonProtocol._
            json.get.tryConvertTo[List[OldSample]]
          }
          else None
        }
        case _ => None
      }
    }
  }

  def getSamples(meterName : String, from : Date, to : Date) : Seq[OldSample] = tryGetSamples(meterName, from , to).getOrElse(Seq.empty)
  */
  def tryGetSamples(resource_id : String, from : Date, to :Date) : Option[Seq[Sample]] = {
    if (to before from) None
    else{
      import org.openstack.api.restful.ceilometer.v2.requests.SamplesGETRequestJsonConversion._
      import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
      val queries = List(("timestamp" >>>> from),("timestamp" <<== to),("resource_id" ==== resource_id))
      val request = SamplesGETRequest(Some(queries), responseBufferSize / oldsample_size)
      val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
      tokenProvider.tokenOption match{
        case Some(s : String) => {
          val body = request.toJson.compactPrint
          val resp = httpClient.newRequest(uri).
            method(HttpMethod.GET).
            header("X-Auth-Token",s).
            header("Content-Type","application/json").
            content(new StringContentProvider(body)).
            timeout(readTimeout, TimeUnit.MILLISECONDS).
            send
          val json = resp.getContentAsString.tryParseJson
          if (json.isDefined) {
            import spray.json.DefaultJsonProtocol._
            json.get.tryConvertTo[List[Sample]]
          }
          else None
        }
        case _ => None
      }
    }
  }
  def getSample(resource_id : String, from : Date, to :Date) : Seq[Sample] = tryGetSamples(resource_id, from, to).getOrElse(Seq.empty)

  def tryListResources : Option[Seq[Resource]] = {
    val req = ResourcesListGETRequest()
    val uri = new URL(ceilometerUrl.toString + req.relativeURL).toURI
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val resp = httpClient.newRequest(uri).
          method(HttpMethod.GET).
          header("X-Auth-Token",s).
          timeout(readTimeout, TimeUnit.MILLISECONDS).
          send
        val json = resp.getContentAsString.tryParseJson
        if (json.isDefined) {
          import spray.json.DefaultJsonProtocol._
          json.get.convertTo[List[Resource]]
          json.get.tryConvertTo[List[Resource]]
        }
        else None
      }
      case _ => None
    }
  }

  def listResources : Seq[Resource] = tryListResources.getOrElse(Seq.empty)

  /**
   * called to shutdown the httpClient
   */
  def shutdown() = {
    if (httpClient.isStarted)
      httpClient.stop()
  }
}

/**
 * Implementation of the flyweight pattern to get a CeilometerClient
 */
object CeilometerClient{
  private val instances : scala.collection.mutable.Map[Int,CeilometerClient] = scala.collection.mutable.Map()
  def getInstance(ceilometerUrl : URL, keystoneUrl : URL, tenantName : String,  username : String, password : String, connectTimeout : Int = 30000, readTimeout : Int = 30000) = {
    this.synchronized{
      val hashcode = getHashCode(ceilometerUrl,keystoneUrl,tenantName,username,password)
      if (!instances.contains(hashCode))
        instances(hashCode) = new CeilometerClient(ceilometerUrl, keystoneUrl, tenantName,  username, password, connectTimeout, readTimeout)
      instances(hashCode)
    }
  }
  private def getHashCode(vals : Any*) = vals.mkString("").hashCode
}