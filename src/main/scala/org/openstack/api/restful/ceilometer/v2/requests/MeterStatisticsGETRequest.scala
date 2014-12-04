package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.elements.{Meter, Aggregate}


/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class MeterStatisticsGETRequest(meter_name : String,
                                     q : Seq[Query],
                                     groupby : Seq[String] = Seq.empty,
                                     period : Option[Int] = None,
                                     aggregate : Seq[Aggregate] = Seq.empty){
  lazy val relativeURL = "/v2/meters/" + meter_name + "/statistics"
}
object MeterStatisticsGETRequestJsonProtocol extends spray.json.DefaultJsonProtocol {
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
  implicit object MeterStatisticsGETRequestJsonFormat extends RootJsonFormat[MeterStatisticsGETRequest] {
    override def write(obj: MeterStatisticsGETRequest) = {
      val map = scala.collection.mutable.Map[String,JsValue]()
      map("q") = obj.q.toJson
      if (obj.groupby.nonEmpty)
        map("groupby") = obj.groupby.toJson
      if (obj.period != None)
        map("period") = obj.period.get.toJson
      if (obj.aggregate.nonEmpty)
        map("aggregate") = obj.groupby.toJson
      JsObject(map.toMap)
    }

    override def read(json: JsValue): MeterStatisticsGETRequest = ???
  }
}
