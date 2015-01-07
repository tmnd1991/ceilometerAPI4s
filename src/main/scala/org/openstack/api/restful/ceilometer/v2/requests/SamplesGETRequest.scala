package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query

/**
 * Created by tmnd91 on 07/01/15.
 */
case class SamplesGETRequest(q : Option[List[Query]], limit : Int = 0) {
  def relativeURL = "/v2/samples/"
}
object SamplesGETRequestJsonConversion extends spray.json.DefaultJsonProtocol{
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
  implicit object SamplesGETRequestJsonFormat extends RootJsonFormat[SamplesGETRequest]{
    override def read(json: JsValue) = SamplesGETRequest(None, 0)
    override def write(obj: SamplesGETRequest) = {
      val mapb = scala.collection.mutable.Map[String,JsValue]()
      if (obj.q.isDefined)
        mapb("q") = obj.q.get.toJson
      if (obj.limit != 0)
        mapb("limit") = obj.limit.toJson
      JsObject(mapb.toMap)
    }
  }
}