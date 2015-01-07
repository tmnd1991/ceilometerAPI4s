package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query

/**
 * @author Antonio Murgia
 * @version 21/10/14.
 */
case class ResourcesListGETRequest(q : Seq[Query] = List(), meter_links : Option[Int] = None) {
  def relativeURL = "/v2/resources"
}

object ResourcesListGETRequestJsonConversion extends spray.json.DefaultJsonProtocol{
  import spray.json._
  import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
  implicit object ResourcesListGETRequestJsonFormat extends RootJsonFormat[ResourcesListGETRequest]{
    override def write(obj: ResourcesListGETRequest) = {
      val mapb = scala.collection.mutable.Map[String,JsValue]()
      if (!obj.q.isEmpty)
        mapb("q") = obj.q.toJson
      if (obj.meter_links.isDefined)
        mapb("limit") = obj.meter_links.get.toJson
      JsObject(mapb.toMap)
    }
    override def read(json: JsValue) = ???
  }
}