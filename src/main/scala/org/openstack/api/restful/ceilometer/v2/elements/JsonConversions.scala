package org.openstack.api.restful.ceilometer.v2.elements



/**
 *
 * spray json conversion for all the classes of this package (not subpackages)<br/>
 * the list is:
 *<ul>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.MeterType MeterType]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.Resource Resource]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.Meter Meter]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.Sample Sample]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.OldSample OldSample]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.Aggregate Aggregate]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.Statistics Statistics]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmState AlarmState]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.StatisticType StatisticType]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmCombinationRule AlarmCombinationRule]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmTimeConstraint AlarmTimeConstraint]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmThresholdRule AlarmThresholdRule]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmType AlarmType]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.Alarm Alarm]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmChangeType AlarmChangeType]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.elements.AlarmChange AlarmChange]]</li>
 * <li>java.sql.Timestamp</li>
 *</ul>
 *
 * @author Antonio Murgia
 * @version 22/10/14
 */
object JsonConversions extends spray.json.DefaultJsonProtocol{
  import java.sql.Timestamp

  import spray.json._
  import DefaultJsonProtocol._
  import org.openstack.api.restful.MalformedJsonException
  import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.JsonConversions._
  import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
  import org.openstack.api.restful.elements.JsonConversions._
  import it.unibo.ing.utils._

  implicit object MeterTypeJsonFormat extends JsonFormat[MeterType] {
    def write(mt: MeterType) = JsString(mt.s)

    def read(value: JsValue) = value match {
      case JsString(x) => MeterType.values.getOrElse(x,throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }
  }

  implicit object TimestampJsonFormat extends JsonFormat[java.sql.Timestamp] {
    override def read(json: JsValue) = json match{
      case s : JsString => TimestampUtils.parseOption(s.value) match{
        case Some(t : java.sql.Timestamp) => t
        case _ => throw new MalformedJsonException
      }
      case _ => throw new MalformedJsonException
    }

    override def write(obj: Timestamp) =  JsString(TimestampUtils.format(obj))
  }

  implicit val ResourceJsonFormat = jsonFormat8(Resource)

  //implicit val MeterJsonFormat = jsonFormat8(Meter)
  implicit object MeterJsonFormat extends RootJsonFormat[Meter]{
    override def write(obj: Meter) = JsObject(
      "meter_id" -> obj.meter_id.toJson,
      "name" -> obj.name.toJson,
      "project_id" -> {
        if (obj.user_id == null) JsNull
        else obj.user_id.toJson
      },
      "resource_id" -> obj.resource_id.toJson,
      "source" -> obj.source.toJson,
      "type" -> obj.`type`.toJson,
      "unit" -> obj.unit.toJson,
      "user_id" -> {
        if (obj.user_id == null) JsNull
        else obj.user_id.toJson
      }
    )

    override def read(json: JsValue) = json match{
      case obj : JsObject =>{
        if (obj.fields.size == 8){
          try{
            Meter(obj.fields("meter_id").asInstanceOf[JsString].value,
                  obj.fields("name").asInstanceOf[JsString].value,
                  {
                    if (obj.fields("project_id") == JsNull)
                      null
                    else
                      obj.fields("project_id").asInstanceOf[JsString].value
                  },
                  obj.fields("resource_id").asInstanceOf[JsString].value,
                  obj.fields("source").asInstanceOf[JsString].value,
                  obj.fields("type").asInstanceOf[JsString].convertTo[MeterType],
                  obj.fields("unit").asInstanceOf[JsString].value,
                  {
                    if (obj.fields("user_id") == JsNull)
                      null
                    else
                      obj.fields("user_id").asInstanceOf[JsString].value
                  })
          }
          catch{
            case t : Throwable => throw new MalformedJsonException(t.getMessage)
          }
        }
        else
          throw new MalformedJsonException
      }
      case _ => throw new MalformedJsonException
    }
  }

  implicit val SampleJsonFormat = jsonFormat12(Sample)

  implicit val OldSampleJsonFormat = jsonFormat12(OldSample)

  implicit val AggregateJsonFormat = jsonFormat2(Aggregate)

  implicit object StatisticsJsonFormat extends RootJsonFormat[Statistics]{
    override def read(json: JsValue) = json match{
      case obj : JsObject => try{

        val avg = obj.fields("avg").convertTo[Float]
        val count = obj.fields("count").convertTo[Int]
        val duration = obj.fields("duration").convertTo[Float]
        val duration_end = DateUtils.parse(obj.fields("duration_end").convertTo[String],"yyyy-MM-dd'T'HH:mm:ss")
        val duration_start = DateUtils.parse(obj.fields("duration_start").convertTo[String],"yyyy-MM-dd'T'HH:mm:ss")
        val max = obj.fields("max").convertTo[Float]
        val min = obj.fields("min").convertTo[Float]
        val period = obj.fields("period").convertTo[Int]
        val period_end = DateUtils.parse(obj.fields("period_end").convertTo[String],"yyyy-MM-dd'T'HH:mm:ss")
        val period_start = DateUtils.parse(obj.fields("period_start").convertTo[String],"yyyy-MM-dd'T'HH:mm:ss")
        val sum = obj.fields("sum").convertTo[Float]
        val unit = obj.fields("unit").convertTo[String]
        val jsGroupby = obj.fields.get("groupby")
        val groupby = if (jsGroupby == None || jsGroupby.get == JsNull) None
                      else Some(jsGroupby.get.convertTo[Map[String,String]])
        val jsAgg = obj.fields.get("aggregate")
        val agg = if (jsAgg == None || jsAgg.get == JsNull) None
                  else Some(jsAgg.get.convertTo[Map[String,Float]])
        Statistics(agg, avg, count, duration, duration_end, duration_start, groupby, max, min, period, period_end, period_start, sum, unit)
      }
      catch{
        case _ : Throwable => throw new MalformedJsonException
      }
      case _ => throw new MalformedJsonException
    }

    override def write(obj: Statistics) = {
      val map = Map("aggregate" -> obj.aggregate.toJson,
                    "avg" -> obj.avg.toJson,
                    "count" -> obj.count.toJson,
                    "duration" -> obj.duration.toJson,
                    "duration_end" -> DateUtils.format(obj.duration_end,"yyyy-MM-dd'T'HH:mm:ss"),
                    "duration_start" -> DateUtils.format(obj.duration_start,"yyyy-MM-dd'T'HH:mm:ss"),
                    "max" -> obj.max.toJson,
                    "min" -> obj.min.toJson,
                    "period" -> obj.period.toJson,
                    "period_end" -> DateUtils.format(obj.period_end,"yyyy-MM-dd'T'HH:mm:ss"),
                    "period_start" -> DateUtils.format(obj.period_start,"yyyy-MM-dd'T'HH:mm:ss"),
                    "sum" -> obj.sum.toJson,
                    "unit" -> obj.unit.toJson)

      if(obj.groupby == None)
        JsObject("aggregate" -> obj.aggregate.toJson,
          "avg" -> obj.avg.toJson,
          "count" -> obj.count.toJson,
          "duration" -> obj.duration.toJson,
          "duration_end" -> DateUtils.format(obj.duration_end,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "duration_start" -> DateUtils.format(obj.duration_start,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "max" -> obj.max.toJson,
          "min" -> obj.min.toJson,
          "period" -> obj.period.toJson,
          "period_end" -> DateUtils.format(obj.period_end,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "period_start" -> DateUtils.format(obj.period_start,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "sum" -> obj.sum.toJson,
          "unit" -> obj.unit.toJson)
      else
        JsObject("aggregate" -> obj.aggregate.toJson,
          "avg" -> obj.avg.toJson,
          "count" -> obj.count.toJson,
          "duration" -> obj.duration.toJson,
          "duration_end" -> DateUtils.format(obj.duration_end,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "duration_start" -> DateUtils.format(obj.duration_start,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "max" -> obj.max.toJson,
          "min" -> obj.min.toJson,
          "period" -> obj.period.toJson,
          "period_end" -> DateUtils.format(obj.period_end,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "period_start" -> DateUtils.format(obj.period_start,"yyyy-MM-dd'T'HH:mm:ss").toJson,
          "sum" -> obj.sum.toJson,
          "unit" -> obj.unit.toJson,
          "groupby" -> obj.groupby.get.toJson)
    }
  }

  implicit object AlarmStateJsonFormat extends JsonFormat[AlarmState]{
    def write(as: AlarmState) =
      JsString(as.s)

    def read(value: JsValue) = value match {
      case JsString(x) =>
        try{
          AlarmState.values(x)
        }
        catch{
          case _ : NoSuchElementException => throw new MalformedJsonException
        }
      case _ => throw new MalformedJsonException
    }
  }

  implicit object StatisticTypeJsonFormat extends JsonFormat[StatisticType]{
    override def read(json: JsValue) = json match{
      case s : JsString => StatisticType.values.getOrElse(s.value,throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }

    override def write(obj: StatisticType) = JsString(obj.s)
  }

  implicit val AlarmThresholdRuleJsonFormat = jsonFormat8(AlarmThresholdRule)

  implicit val AlarmCombinationRuleJsonFormat = jsonFormat2(AlarmCombinationRule)

  implicit val AlarmTimeConstraintJsonFormat = jsonFormat5(AlarmTimeConstraint)

  implicit object AlarmTypeJsonFormat extends JsonFormat[AlarmType]{
    override def read(json: JsValue): AlarmType = json match{
      case s : JsString => AlarmType.values.getOrElse(s.value, throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }

    override def write(obj: AlarmType) = JsString(obj.s)
  }

  implicit val AlarmJsonFormat = jsonFormat17(Alarm)

  implicit object AlarmChangeTypeJsonFormat extends JsonFormat[AlarmChangeType]{
    override def read(json: JsValue) = json match{
      case s : JsString => AlarmChangeType.values.getOrElse(s.value, throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }

    override def write(obj: AlarmChangeType) = JsString(obj.s)
  }

  implicit val AlarmChangeJsonFormat = jsonFormat8(AlarmChange)
}
