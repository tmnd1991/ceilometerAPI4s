package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.FilterExpressions.Query

/**
 * Created by tmnd on 21/10/14.
 */
case class ResourcesListGETRequest(q : Seq[Query], meter_links : Option[Int]) {
  import org.openstack.api.restful.FilterExpressions.JsonConversions._
  import spray.json._

  lazy val queryMap = {
    import spray.json.DefaultJsonProtocol._
    if (meter_links == None)
      Map("q" -> q.toJson.compactPrint.toString)
    else
      Map("q" -> q.toJson.compactPrint.toString,"meter_links"->meter_links.get.toJson.toString)
  }
  lazy val relativeURL = "/v2/resources"
}