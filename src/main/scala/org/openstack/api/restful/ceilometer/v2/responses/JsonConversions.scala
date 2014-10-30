package org.openstack.api.restful.ceilometer.v2.responses

import spray.json._
import org.openstack.api.restful.MalformedJsonException
import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.elements.{Statistics, Resource, Meter, OldSample, Alarm}
/**
 * Created by tmnd on 21/10/14.
 */

object JsonConversions extends DefaultJsonProtocol{

  implicit object ResourceListJsonFormat extends JsonFormat[ResourceList] {
    def write(rl: ResourceList) =
      JsArray(rl.resources.map(_.toJson).toVector)

    def read(value: JsValue) = value match {
      case JsArray(x) =>
        ResourceList(x.map(_.convertTo[Resource]))
      case _ => throw new MalformedJsonException
    }
  }

  implicit object MeterListJsonFormat extends JsonFormat[MeterList] {
    def write(ml: MeterList) =
      JsArray(ml.meters.map(_.toJson).toVector)

    def read(value: JsValue) = value match {
      case JsArray(x) =>
        MeterList(x.map(_.convertTo[Meter]))
      case _ => throw new MalformedJsonException
    }
  }

  implicit object OldSampleListJsonFormat extends JsonFormat[OldSampleList] {
    def write(osl: OldSampleList) =
      JsArray(osl.samples.map(_.toJson).toVector)

    def read(value: JsValue) = value match {
      case JsArray(x) =>
        OldSampleList(x.map(_.convertTo[OldSample]))
      case _ => throw new MalformedJsonException
    }
  }

  implicit object StatisticsListJsonFormat extends JsonFormat[StatisticsList] {
    def write(sl: StatisticsList) =
      JsArray(sl.statistics.map(_.toJson).toVector)

    def read(value: JsValue) = value match {
      case JsArray(x) =>
        StatisticsList(x.map(_.convertTo[Statistics]))
      case _ => throw new MalformedJsonException
    }
  }

  implicit object AlarmListJsonFormat extends JsonFormat[AlarmList] {
    def write(al: AlarmList) =
      JsArray(al.alarms.map(_.toJson).toVector)

    def read(value: JsValue) = value match {
      case JsArray(x) =>
        AlarmList(x.map(_.convertTo[Alarm]))
      case _ => throw new MalformedJsonException
    }
  }
}