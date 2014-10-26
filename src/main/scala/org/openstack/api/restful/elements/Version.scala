package org.openstack.api.restful.elements

import java.util.Date
//import myUtils.DateUtils
import spray.json.DefaultJsonProtocol

/**
 * Created by tmnd on 10/10/14.
 */
case class Version(status : String, updated : Date, id : String, links : Seq[Link]) {
  require(status != null)
  require(status.nonEmpty)
  require(updated != null)
  require(links != null)
  require(links.nonEmpty)
}