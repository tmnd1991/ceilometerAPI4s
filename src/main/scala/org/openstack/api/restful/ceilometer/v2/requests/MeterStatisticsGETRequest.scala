package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.elements.Aggregate
/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class MeterStatisticsGETRequest(meter_name : String, q : Seq[Query], groupby : Seq[String], period : Int, aggregate : Seq[Aggregate]) {

}