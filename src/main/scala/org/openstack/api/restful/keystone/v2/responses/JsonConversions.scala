package org.openstack.api.restful.keystone.v2.responses

import java.net.URL

import spray.json._

import org.openstack.api.restful.keystone.v2.elements.JsonConversions._
import org.openstack.api.restful.MalformedJsonException

/**
 * @author Antonio Murgia
 */
object JsonConversions extends DefaultJsonProtocol{
  implicit val TokenResponseJsonFormat = jsonFormat1(TokenResponse)
}
