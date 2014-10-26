package org.openstack.api.restful.compute.v2.responses

import org.openstack.api.restful.elements.Version

/**
 * Created by tmnd on 10/10/14.
 */
case class Root(versions : Seq[Version]){
  require((versions.nonEmpty) && (versions != null))
}