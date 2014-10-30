package org.openstack.api.restful.ceilometer.v2.requests

/**
 * Created by tmnd on 21/10/14.
 */

case class ResourceGETRequest(resource_id : String) {
  lazy val queryMap = Map("resource_id" -> resource_id)
  lazy val relativeURL = "/v2/resources/"+resource_id
}
