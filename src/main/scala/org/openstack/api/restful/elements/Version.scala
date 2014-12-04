package org.openstack.api.restful.elements

import java.util.Date

/**
 * @author Antonio Murgia
 * @version 04/12/2014
 */
case class Version(status : String, updated : Date, id : String, links : Seq[Link]) {
  require(status != null)
  require(status.nonEmpty)
  require(updated != null)
  require(links != null)
  require(links.nonEmpty)
}