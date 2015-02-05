package org.openstack.api.restful.keystone.v2

import java.net.URL
import java.util.Date
import it.unibo.ing.utils._
import org.eclipse.jetty.client._
import org.eclipse.jetty.io.{ByteArrayBuffer, Buffer}

import org.openstack.api.restful.keystone.v2.elements.{PasswordCredential, OpenStackCredential}
import org.openstack.api.restful.keystone.v2.requests.TokenPOSTRequest
import org.openstack.api.restful.keystone.v2.responses.TokenResponse

import spray.json._
import spray.json.MyMod._
import org.openstack.api.restful.keystone.v2.requests.JsonConversions._
import org.openstack.api.restful.keystone.v2.responses.JsonConversions._

import scala.collection._

/**
 * Implementation of TokenProvider that provides token fetched from openstack Keystone
 * @author Antonio Murgia
 * @version 02/11/2014
 */
private class KeystoneTokenProvider(host : URL, tenantName : String,  username : String, password : String)
  extends TokenProvider
{
  case class TokenInfo(id : String, localIssuedAt : Date, duration : Long)

  private val client = new HttpClient()
  //these values are a bit random, there's some reasoning behind the choice, but not too much.
  //I don't really know the drawbacks we get from increasing their size.
  val hash = (host.toString+tenantName+username+password).hashCode
  client.setRequestBufferSize(16384)
  client.setResponseBufferSize(32768)
  client.start

  private val tokens : mutable.Map[Int, TokenInfo] = mutable.Map()
  private var _timeOffset : Option[Long] = None

  override def invalidate = {
    this.synchronized{
      tokens(hash) = newToken
    }
  }

  override def token = {
    this.synchronized{
      if (!tokens.contains(hash) || isExpired(hash)){
        val tokenInfo = newToken
        tokens(hash) = tokenInfo
      }
      tokens(hash).id
    }
  }

  override def timeOffset = if (_timeOffset.isDefined) _timeOffset.get
                            else throw new RuntimeException("timeOffset not set")


  /**
   *
   * @return a new Token for the passed values of username, password, host and tenant name got from Keystone
   *         RESTFUL API.
   */
  private def newToken = {
    val a = TokenPOSTRequest(OpenStackCredential(tenantName,PasswordCredential(username,password)))
    val aString = a.toJson.toString
    val url = new URL(host.toString + a.relativeURL).toURI

    val exchange = new ContentExchange()
    exchange.setMethod("POST")
    exchange.setRequestContentType("application/json")
    exchange.setRequestContent(new ByteArrayBuffer(aString.getBytes))
    exchange.setURI(url)
    client.send(exchange)
      /*POST(url).
      header("Content-Type","application/json").
      content(new StringContentProvider(aString))
      */
    val exchangeState = exchange.waitForDone()
    val body = exchange.getResponseContent
    /*
    val response = request.send()
    val body = response.getContentAsString
    */
    val tokenResponse = body.parseJson.convertTo[TokenResponse]
    _timeOffset = Some(tokenResponse.access.token.issued_at - new Date())
    new TokenInfo(tokenResponse.access.token.id,
                  new Date(),
                  tokenResponse.access.token.expires - tokenResponse.access.token.issued_at)
  }

  private def isExpired(hash : Int) = {
    if (tokens.contains(hash)){
      val tokenInfo = tokens(hash)
      new Date().after(new Date(tokenInfo.localIssuedAt.getTime + tokenInfo.duration))
    }
    else true
  }
}

/**
 * Implements the Flyweight pattern to retrieve an instance of KeystoneTokenProvider.
 * Stores tokens in order to not retrieve them everytime.
 */
object KeystoneTokenProvider{
  private val providers : mutable.Map[Int,KeystoneTokenProvider] = mutable.Map()

  /**
   *
   * @param host the Keystone endpoint
   * @param tenantName the Keystone tenant name
   * @param username the Keystone username
   * @param password the Keystone password
   * @return
   */
  def getInstance(host : URL, tenantName : String,  username : String, password : String) : TokenProvider = {
    val hashed = getHashCode(host, tenantName, username, password).hashCode
    if (providers.contains(hashed)){
      providers(hashed)
    }
    else{
      val newProvider = new KeystoneTokenProvider(host, tenantName,  username, password)
      providers(hashed) = newProvider
      newProvider
    }
  }
  private def getHashCode(vals : Any*) = vals.mkString("").hashCode
}