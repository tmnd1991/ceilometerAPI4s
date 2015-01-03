package org.openstack.api.restful.ceilometer.v2.requests


import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query

/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class MetersListGETRequest(q : Seq[Query]){
  def relativeURL = "/v2/meters/"
}


object MetersListGETRequestJsonConversion extends spray.json.DefaultJsonProtocol{
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
  implicit val MetersListGETRequestJsonFormat = jsonFormat1(MetersListGETRequest.apply)
}