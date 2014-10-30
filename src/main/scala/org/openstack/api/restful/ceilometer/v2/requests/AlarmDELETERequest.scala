package org.openstack.api.restful.ceilometer.v2.requests

/**
 * Created by tmnd on 21/10/14.
 */
case class AlarmDELETERequest(alarm_id : String){
  lazy val queryMap = Map("alarm_id" -> alarm_id)
  lazy val relativeURL = "/v2/resources/"+alarm_id
}
