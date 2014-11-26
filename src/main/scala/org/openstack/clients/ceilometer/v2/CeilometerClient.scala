package org.openstack.clients.ceilometer.v2




import java.net.URL
import uk.co.bigbeeconsultants.http.header.{Header, Headers, MediaType}
import uk.co.bigbeeconsultants.http.request.RequestBody
import uk.co.bigbeeconsultants.http.url.Href
import uk.co.bigbeeconsultants.http.{Config, ModdedHttpClient}
import spray.json._
import org.openstack.api.restful.ceilometer.v2.elements.Meter
import org.openstack.api.restful.ceilometer.v2.requests.MetersListGETRequest
import org.openstack.api.restful.ceilometer.v2.requests.MetersListGETRequestJsonConversion._
import org.openstack.api.restful.keystone.v2.KeystoneTokenProvider
import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._

/**
 * Created by tmnd on 26/11/14.
 */
class CeilometerClient(ceilometerUrl : URL,
                       keystoneUrl : URL,
                       tenantName : String,
                       username : String,
                       password : String,
                       connectTimeout : Int = 10000,
                       readTimeout : Int = 10000) {
  private val tokenProvider = KeystoneTokenProvider.getInstance(keystoneUrl, tenantName, username, password)
  private val httpClient = new ModdedHttpClient(Config(connectTimeout        = connectTimeout,
                                                       readTimeout           = readTimeout,
                                                       followRedirects       = false))

  def listMeters = {
    val request = new MetersListGETRequest(List())
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val resp = httpClient.get(new URL(ceilometerUrl.toString + request.relativeURL),
                       Headers(Header("X-Auth-Token",s)))
        import spray.json.DefaultJsonProtocol._
        resp.body.toString.parseJson.convertTo[Seq[Meter]]
      }
    }
  }
  def listMeters(q : Seq[Query]) = {
    val request = new MetersListGETRequest(q)
    val body = request.toJson.toString
    tokenProvider.tokenOption match{
      case Some(s : String) => {
        val resp = httpClient.get(
          new URL(ceilometerUrl.toString + request.relativeURL),
          Headers(Header("X-Auth-Token",s)),
          Some(RequestBody(body,MediaType.APPLICATION_JSON)))
        import spray.json.DefaultJsonProtocol._
        resp.body.toString.parseJson.convertTo[List[Meter]]
      }
    }
  }
}
object CeilometerClient{
  private val instances : scala.collection.mutable.Map[Int,CeilometerClient] = scala.collection.mutable.Map()

  def getInstance(ceilometerUrl : URL, keystoneUrl : URL, tenantName : String,  username : String, password : String) = {
    val hashcode = getHashCode(ceilometerUrl,keystoneUrl,tenantName,username,password)
    if (!instances.contains(hashCode))
      instances(hashCode) = new CeilometerClient(ceilometerUrl, keystoneUrl, tenantName,  username, password)
    instances(hashCode)
  }

  private def getHashCode(vals : Any*) = {
    vals.mkString("").hashCode
  }
}