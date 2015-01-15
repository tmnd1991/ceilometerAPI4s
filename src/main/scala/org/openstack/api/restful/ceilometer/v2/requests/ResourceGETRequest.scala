package org.openstack.api.restful.ceilometer.v2.requests

/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
import it.unibo.ing.utils._

case class ResourceGETRequest(resource_id : String) {
  def queryMap = Map("resource_id" -> resource_id)
  def relativeURL = "/v2/resources" / resource_id
}
