package org.openstack.clients.ceilometer.v2

import java.io.{InputStreamReader, BufferedReader}
import java.net.URL
import java.util.Date


import myUtils.DateUtils
import spray.json._
import spray.json.MyMod._

import org.eclipse.jetty.client.{HttpClient, HttpExchange}
import org.eclipse.jetty.client.util.StringContentProvider
import org.eclipse.jetty.http.HttpMethod

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.{FieldValue, Query}
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.elements.{Aggregate, Statistics, Meter}
import org.openstack.api.restful.ceilometer.v2.requests.{MeterStatisticsGETRequest, MetersListGETRequest}
import org.openstack.api.restful.ceilometer.v2.requests.MetersListGETRequestJsonConversion._
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
  private val tokenProvider = KeystoneTokenProvider.getInstance(keystoneUrl, tenantName, username, password)
  private val httpClient = new HttpClient()
  httpClient.setConnectTimeout(connectTimeout)
  httpClient.setFollowRedirects(false)
  httpClient.setStopTimeout(readTimeout)
  httpClient.setRequestBufferSize(16384)
  httpClient.setResponseBufferSize(32768)
  httpClient.start()

  /**
   *
   * @param q any number of queries to filter the meters
   * @return tries to return a collection of meters filtered by the query params if an error occurs return None
   */
  def tryListMeters(q : Query*) : Option[Seq[Meter]] = {
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
          header("X-Auth-Token",s).
          content(new StringContentProvider(body)).
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
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if any error occurs an empty Seq is returned
   */
  def listMeters(q : Query*) : Seq[Meter] = tryListMeters(q:_*).getOrElse(List.empty)

  /**
   * @return Some collection of meters avaiable if an error occurs returns None
   */
  def tryListMeters : Option[Seq[Meter]] = tryListMeters()

  /**
   * @return a collection of meters avaiable if an error occurs an empty Seq is returned
   */
  def listMeters : Seq[Meter] = tryListMeters.getOrElse(List.empty)

  /**
   *
   * @param m the Meter the Statistics is relative to.
   * @return Some statistics about that meter or None if an error occurs
   */
  def tryGetStatistics(m : Meter) : Option[Seq[Statistics]] = {
    val uri = new URL(ceilometerUrl.toString + "/v2/meters/" + m.name + "/statistics").toURI
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val resp = httpClient.newRequest(uri).method(HttpMethod.GET).header("X-Auth-Token",s).send
        val body = resp.getContentAsString
        val json = body.tryParseJson
        if (json != None) {
          import spray.json.DefaultJsonProtocol._
          json.get.tryConvertTo[List[Statistics]]
        }
        else None
      }
      case _ => None
    }
  }

  /**
   *
   * @param m the Meter the Statistics is relative to.
   * @return Statistics about that meter or an empty Seq if an error occurs
   */
  def getStatistics(m : Meter) : Seq[Statistics] = tryGetStatistics(m).getOrElse(List.empty)

  /**
   * @param from
   * @param to
   * @param m the Meter the Statistics is relative to.
   * @return Some statistics about that meter from a Date to another or None if an error occurs
   */
  def tryGetStatistics(m : Meter, from : Date, to : Date) : Option[Seq[Statistics]] = {
    if (to before from) None
    else{
      import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.Goodies._
      val exp = ("timestamp" GT from) AND ("timestamp" LE to)
      val query = ComplexQuery(exp)
      val request = MeterStatisticsGETRequest(m.name, List(query))
      val body = request.toJson.toString
      val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
      tokenProvider.tokenOption match{
        case Some(s : String) => {
          val resp = httpClient.newRequest(uri).
            method(HttpMethod.GET).
            header("X-Auth-Token",s).
            content(new StringContentProvider(body)).send
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

  /**
   * @param from
   * @param to
   * @param m the Meter the Statistics is relative to.
   * @return statistics about that meter from a Date to another or an empty Seq if an error occurs
   */
  def getStatistics(m : Meter, from : Date, to : Date) : Seq[Statistics] = tryGetStatistics(m, from, to).getOrElse(List.empty)

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
  def getInstance(ceilometerUrl : URL, keystoneUrl : URL, tenantName : String,  username : String, password : String, connectTimeout : Int = 10000, readTimeout : Int = 10000) = {
    this.synchronized{
      val hashcode = getHashCode(ceilometerUrl,keystoneUrl,tenantName,username,password)
      if (!instances.contains(hashCode))
        instances(hashCode) = new CeilometerClient(ceilometerUrl, keystoneUrl, tenantName,  username, password, connectTimeout, readTimeout)
      instances(hashCode)
    }
  }
  private def getHashCode(vals : Any*) = vals.mkString("").hashCode
}