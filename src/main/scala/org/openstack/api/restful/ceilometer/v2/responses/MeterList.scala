package org.openstack.api.restful.ceilometer.v2.responses

import org.openstack.api.restful.ceilometer.v2.elements.Meter

/**
 * Created by tmnd on 21/10/14.
 */
case class MeterList(meters : Seq[Meter]) {

}
