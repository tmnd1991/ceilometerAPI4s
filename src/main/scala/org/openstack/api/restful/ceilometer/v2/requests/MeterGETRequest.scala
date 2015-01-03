package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._


/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class MeterGETRequest(meter_name : String, q : Option[Seq[Query]] = None, limit : Int = 0) {
  def relativeURL = "/v2/meters/" + meter_name

}
object MeterGETRequestJsonConversion extends spray.json.DefaultJsonProtocol{
  import spray.json._
  implicit object MeterGETRequestJsonFormat extends RootJsonFormat[MeterGETRequest]{
    override def read(json: JsValue) = MeterGETRequest("will never be called", None, 0)
    override def write(obj: MeterGETRequest) = {
      val mapb = scala.collection.mutable.Map[String,JsValue]()
      if (obj.q.isDefined)
        mapb + ("q" -> obj.q.get.toJson)
      if (obj.limit != 0)
        mapb + ("limit" -> obj.limit.toJson)
      JsObject(mapb.toMap)
    }
  }
}