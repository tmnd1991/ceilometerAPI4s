package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query

/**
 * @author Antonio Murgia
 * @version 21/10/14.
 */
case class ResourcesListGETRequest(q : Seq[Query], meter_links : Option[Int]) {

  def queryMap = {
    import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
    import spray.json._
    if (meter_links == None){
      import spray.json.DefaultJsonProtocol._
      Map("q" -> q.toJson.compactPrint.toString)
    }
    else{
      import spray.json.DefaultJsonProtocol._
      Map("q" -> q.toJson.compactPrint.toString,"meter_links"->meter_links.get.toJson.toString)
    }
  }
  def relativeURL = "/v2/resources"
}