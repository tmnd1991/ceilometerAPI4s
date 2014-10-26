package org.openstack.api.restful.ceilometer.v2.responses

import org.openstack.api.restful.ceilometer.v2.elements.Resource
/**
 * Created by tmnd on 21/10/14.
 */
case class ResourceList(resources : Seq[Resource]) {
  require(resources != null)
  require(resources.nonEmpty)
}
