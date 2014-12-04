package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query

/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class AlarmHistoryGETRequest(alarm_id : String, q : Seq[Query]) {

}
