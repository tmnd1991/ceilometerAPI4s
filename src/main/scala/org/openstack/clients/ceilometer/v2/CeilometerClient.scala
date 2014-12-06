package org.openstack.clients.ceilometer.v2


import java.io.{InputStreamReader, BufferedReader}
import java.net.URL
import java.util.Date


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
  httpClient.start()

  /**
   *
   * @return a collection of meters avaiable if an error occurs the collection will be empty
   */
  def listMeters : Seq[Meter] = {
    val request = new MetersListGETRequest(List.empty)
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val uri = new URL(ceilometerUrl.toString + request.relativeURL).toURI
        val resp = httpClient.newRequest(uri).
          method(HttpMethod.GET).
          header("X-Auth-Token",s).send()
        try{
          val json = resp.getContentAsString.tryParseJson
          if (json != None){
            import spray.json.DefaultJsonProtocol._
            json.get.tryConvertTo[Seq[Meter]].
              getOrElse(List.empty)
          }
          else
            List.empty
        }
        catch{
          case t : Throwable => List.empty
        }
      }
      case _ => List.empty
    }
  }

  /**
   *
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if an error occurs the collection will be empty
   */
  def listMeters(q : Query*) : Seq[Meter] = {
    val request = new MetersListGETRequest(q)
    val body = request.toJson.compactPrint
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
            json.get.tryConvertTo[Seq[Meter]].
              getOrElse(List.empty)
          }
          else
            List.empty
        }
        catch{
          case t : Throwable => List.empty
        }
      }
      case _ => List.empty
    }
  }

  /**
   *
   * @param m the Meter the Statistics is relative to.
   * @return All the statistics about that meter
   */
  def getStatistics(m : Meter) : List[Statistics] = {
    val uri = new URL(ceilometerUrl.toString + "v2/meters/" + m.name + "/statistics").toURI
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val resp = httpClient.newRequest(uri).method(HttpMethod.GET).header("X-Auth-Token",s).send
        val json = resp.getContentAsString.tryParseJson
        if (json != None) {
          import spray.json.DefaultJsonProtocol._
          json.get.tryConvertTo[List[Statistics]].getOrElse(List.empty)
        }
        else List.empty
      }
      case _ => List.empty
    }
  }
  /**
   * @param from
   * @param to
   * @param m the Meter the Statistics is relative to.
   * @return All the statistics about that meter from a Date to another
   */
  def getStatistics(m : Meter, from : Date, to : Date) : List[Statistics] = {
    if (to before from)
      List.empty
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
            json.get.tryConvertTo[List[Statistics]].getOrElse(List.empty)
          }
          else List.empty
        }
        case _ => List.empty
      }
    }
  }

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
  def getInstance(ceilometerUrl : URL, keystoneUrl : URL, tenantName : String,  username : String, password : String) = {
    val hashcode = getHashCode(ceilometerUrl,keystoneUrl,tenantName,username,password)
    if (!instances.contains(hashCode))
      instances(hashCode) = new CeilometerClient(ceilometerUrl, keystoneUrl, tenantName,  username, password)
    instances(hashCode)
  }

  private def getHashCode(vals : Any*) = vals.mkString("").hashCode

}