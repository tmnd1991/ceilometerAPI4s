package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.elements.AlarmState

/**
 * Created by tmnd on 21/10/14.
 */
case class AlarmStatePUTRequest(alarm_id : String, state : AlarmState) {

}
