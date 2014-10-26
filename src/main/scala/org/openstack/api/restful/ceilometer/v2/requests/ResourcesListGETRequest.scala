package org.openstack.api.restful.ceilometer.v2.requests


import org.openstack.api.restful.FilterExpressions.Query

/**
 * Created by tmnd on 21/10/14.
 */
case class ResourcesListGETRequest(q : Seq[Query], meter_links : Option[Int]) {
}
