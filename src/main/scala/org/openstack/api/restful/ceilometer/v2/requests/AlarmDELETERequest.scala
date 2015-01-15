package org.openstack.api.restful.ceilometer.v2.requests

/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
import it.unibo.ing.utils._

case class AlarmDELETERequest(alarm_id : String){
  def queryMap = Map("alarm_id" -> alarm_id)
  def relativeURL = "/v2/resources/" / alarm_id
}
