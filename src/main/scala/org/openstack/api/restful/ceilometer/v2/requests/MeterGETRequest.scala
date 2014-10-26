package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.FilterExpressions.Query

/**
 * Created by tmnd on 21/10/14.
 */
case class MeterGETRequest(meter_name : String, q : Seq[Query], limit : Int) {

}
