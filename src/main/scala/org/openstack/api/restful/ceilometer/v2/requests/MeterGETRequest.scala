package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
import spray.json._

/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class MeterGETRequest(meter_name : String, q : Option[Seq[Query]] = None, limit : Int = 0) {
  def relativeURL = "/v2/meters/" + meter_name

}
object MeterGETRequestJsonConversion extends DefaultJsonProtocol{
  implicit object MeterGETRequestJsonFormat extends RootJsonFormat[MeterGETRequest]{
    override def read(json: JsValue) = MeterGETRequest("will never be called", None, 0)
    override def write(obj: MeterGETRequest) = {
      JsObject("q" -> obj.q.get.toJson,
               "limit" -> obj.limit.toJson)
    }
  }
}