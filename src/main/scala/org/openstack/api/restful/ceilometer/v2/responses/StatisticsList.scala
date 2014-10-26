package org.openstack.api.restful.ceilometer.v2.responses

/**
 * Created by tmnd on 21/10/14.
 */
import org.openstack.api.restful.ceilometer.v2.elements.Statistics
case class StatisticsList(statistics : Seq[Statistics]) {

}
