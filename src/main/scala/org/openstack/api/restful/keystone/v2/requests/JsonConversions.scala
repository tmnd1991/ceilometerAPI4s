package org.openstack.api.restful.keystone.v2.requests

/**
 * @author Antonio Murgia
 * @version 10/11/14
 */

import spray.json._

import org.openstack.api.restful.keystone.v2.elements.JsonConversions._

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
object JsonConversions extends DefaultJsonProtocol{
  implicit val TokenPOSTRequestJsonFormat = jsonFormat1(TokenPOSTRequest)
}

