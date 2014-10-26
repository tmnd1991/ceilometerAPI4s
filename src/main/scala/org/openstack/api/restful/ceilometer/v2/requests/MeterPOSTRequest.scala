package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.elements.OldSample

/**
 * Created by tmnd on 21/10/14.
 */
case class MeterPOSTRequest(meter_name : String, samples : Seq[OldSample]) {

}
