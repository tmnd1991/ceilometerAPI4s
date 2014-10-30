/**
 * Created by tmnd on 19/10/14.
 */

import org.openstack.api.restful.FilterExpressions.ComplexQueryPackage.ComplexQuery
import org.openstack.api.restful.FilterExpressions.SimpleQueryPackage.SimpleQuery
import org.scalatest._

import spray.json._

import org.openstack.api.restful.ceilometer.v2.elements._
import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
import org.openstack.api.restful.FilterExpressions.SimpleQueryPackage.JsonConversions._
import org.openstack.api.restful.FilterExpressions.ComplexQueryPackage.JsonConversions._
import org.openstack.api.restful.FilterExpressions.JsonConversions._

class JsonTests extends FlatSpec with Matchers {
  "A Resource" should "be mapped correctly" in{
    val parsedResource = """{
      "links": [
      {
        "href": "http://localhost:8777/v2/resources/bd9431c1-8d69-4ad3-803a-8d4a6b89fd36",
        "rel": "self"
      },
      {
        "href": "http://localhost:8777/v2/meters/volume?q.field=resource_id&q.value=bd9431c1-8d69-4ad3-803a-8d4a6b89fd36",
        "rel": "volume"
      }
      ],
      "metadata": {
        "name1": "value1",
        "name2": "value2"
      },
      "project_id": "35b17138-b364-4e6a-a131-8f3099c5be68",
      "resource_id": "bd9431c1-8d69-4ad3-803a-8d4a6b89fd36",
      "source": "openstack",
      "user_id": "efd87807-12d2-4b38-9c70-5f5c2ac427ff"
    }""".parseJson.convertTo[Resource]
    parsedResource.links.size should be (2)
    parsedResource.first_sample_timestamp should be (None)
    parsedResource.last_sample_timestamp should be (None)
    parsedResource.metadata should be (Map("name1"->"value1","name2"->"value2"))
    parsedResource.project_id should be ("35b17138-b364-4e6a-a131-8f3099c5be68")
    parsedResource.resource_id should be ("bd9431c1-8d69-4ad3-803a-8d4a6b89fd36")
    parsedResource.source should be ("openstack")
    parsedResource.user_id should be ("efd87807-12d2-4b38-9c70-5f5c2ac427ff")
  }
  "A Meter" should  "be mapped correctly" in{
    val parsedMeter = """{
          "meter_id": "YmQ5NDMxYzEtOGQ2OS00YWQzLTgwM2EtOGQ0YTZiODlmZDM2K2luc3RhbmNl\n",
          "name": "instance",
          "project_id": "35b17138-b364-4e6a-a131-8f3099c5be68",
          "resource_id": "bd9431c1-8d69-4ad3-803a-8d4a6b89fd36",
          "source": "openstack",
          "type": "gauge",
          "unit": "instance",
          "user_id": "efd87807-12d2-4b38-9c70-5f5c2ac427ff"
      }""".parseJson.convertTo[Meter]
    parsedMeter.meter_id should be ("YmQ5NDMxYzEtOGQ2OS00YWQzLTgwM2EtOGQ0YTZiODlmZDM2K2luc3RhbmNl\n")
    parsedMeter.name should be ("instance")
    parsedMeter.project_id should be ("35b17138-b364-4e6a-a131-8f3099c5be68")
    parsedMeter.resource_id should be ("bd9431c1-8d69-4ad3-803a-8d4a6b89fd36")
    parsedMeter.source should be ("openstack")
    parsedMeter.`type` should be (MeterType.GAUGE)
    parsedMeter.unit should be ("instance")
    parsedMeter.user_id should be ("efd87807-12d2-4b38-9c70-5f5c2ac427ff")
  }

  "An OldSample" should "be mapped correctly" in{
    val parsedOldSample = """{
      "counter_name": "instance",
      "counter_type": "gauge",
      "counter_unit": "instance",
      "counter_volume": 1.0,
      "message_id": "5460acce-4fd6-480d-ab18-9735ec7b1996",
      "project_id": "35b17138-b364-4e6a-a131-8f3099c5be68",
      "recorded_at": "2014-10-20T18:40:41.883960",
      "resource_id": "bd9431c1-8d69-4ad3-803a-8d4a6b89fd36",
      "resource_metadata": {
        "name1": "value1",
        "name2": "value2"
      },
      "source": "openstack",
      "timestamp": "2014-10-20T18:40:41.883967",
      "user_id": "efd87807-12d2-4b38-9c70-5f5c2ac427ff"
    }""".parseJson.convertTo[OldSample]
    parsedOldSample.counter_name should be ("instance")
    parsedOldSample.counter_type should be (MeterType.GAUGE)
    parsedOldSample.counter_unit should be ("instance")
    parsedOldSample.counter_volume should be (1.0)
    parsedOldSample.message_id should be ("5460acce-4fd6-480d-ab18-9735ec7b1996")
    parsedOldSample.project_id should be ("35b17138-b364-4e6a-a131-8f3099c5be68")
    myUtils.TimestampUtils.format(parsedOldSample.recorded_at) should be ("2014-10-20T18:40:41.883960")
    parsedOldSample.resource_id should be ("bd9431c1-8d69-4ad3-803a-8d4a6b89fd36")
    parsedOldSample.resource_metadata should be (Map("name1"->"value1","name2"->"value2"))
    parsedOldSample.source should be ("openstack")
    myUtils.TimestampUtils.format(parsedOldSample.timestamp) should be ("2014-10-20T18:40:41.883967")
    parsedOldSample.user_id should be ("efd87807-12d2-4b38-9c70-5f5c2ac427ff")
  }
  "A Sample" should "be mapped correctly" in{
    val parsedSample = """{
                             "id": "982bd7fc-5888-11e4-8f7f-fa163ec5f46c",
                             "metadata": {
                                 "name1": "value1",
                                 "name2": "value2"
                             },
                             "meter": "instance",
                             "project_id": "35b17138-b364-4e6a-a131-8f3099c5be68",
                             "recorded_at": "2014-10-20T18:40:41.976617",
                             "resource_id": "bd9431c1-8d69-4ad3-803a-8d4a6b89fd36",
                             "source": "openstack",
                             "timestamp": "2014-10-20T18:40:41.976606",
                             "type": "gauge",
                             "unit": "instance",
                             "user_id": "efd87807-12d2-4b38-9c70-5f5c2ac427ff",
                             "volume": 1.0
                         }""".parseJson.convertTo[Sample]
    parsedSample.id should be ("982bd7fc-5888-11e4-8f7f-fa163ec5f46c")
    parsedSample.metadata should be (Map("name1"-> "value1", "name2"->"value2"))
    parsedSample.meter should be ("instance")
    parsedSample.project_id should be ("35b17138-b364-4e6a-a131-8f3099c5be68")
    myUtils.TimestampUtils.format(parsedSample.recorded_at) should be ("2014-10-20T18:40:41.976617")
    parsedSample.resource_id should be ("bd9431c1-8d69-4ad3-803a-8d4a6b89fd36")
    parsedSample.source should be ("openstack")
    myUtils.TimestampUtils.format(parsedSample.timestamp) should be ("2014-10-20T18:40:41.976606")
    parsedSample.`type` should be (MeterType.values("gauge"))
    parsedSample.unit should be ("instance")
    parsedSample.user_id should be ("efd87807-12d2-4b38-9c70-5f5c2ac427ff")
    parsedSample.volume should be (1.0)
  }

  "A Statistics" should "be mapped correctly" in {
    val statisticsSample = """{
                                 "avg": 4.5,
                                 "count": 10,
                                 "duration": 300.0,
                                 "duration_end": "2013-01-04T16:47:00",
                                 "duration_start": "2013-01-04T16:42:00",
                                 "max": 9.0,
                                 "min": 1.0,
                                 "period": 7200,
                                 "period_end": "2013-01-04T18:00:00",
                                 "period_start": "2013-01-04T16:00:00",
                                 "sum": 45.0,
                                 "unit": "GiB"
                             }""".parseJson.convertTo[Statistics]
    statisticsSample.avg should be (4.5)
    statisticsSample.count should be (10)
    statisticsSample.duration should be (300.0)
    myUtils.DateUtils.format(statisticsSample.duration_end,"yyyy-MM-dd'T'HH:mm:ss") should be ("2013-01-04T16:47:00")
    myUtils.DateUtils.format(statisticsSample.duration_start,"yyyy-MM-dd'T'HH:mm:ss") should be ("2013-01-04T16:42:00")
    statisticsSample.max should be (9.0)
    statisticsSample.min should be (1.0)
    statisticsSample.period should be (7200)
    myUtils.DateUtils.format(statisticsSample.period_end,"yyyy-MM-dd'T'HH:mm:ss") should be ("2013-01-04T18:00:00")
    myUtils.DateUtils.format(statisticsSample.period_start,"yyyy-MM-dd'T'HH:mm:ss") should be ("2013-01-04T16:00:00")
    statisticsSample.sum should be (45.0)
    statisticsSample.unit should be ("GiB")
  }

  "Aggregate" should "be mapped correctly" in{
    val aggregate = """{
                          "func": "cardinality",
                          "param": "resource_id"
                      }""".parseJson.convertTo[Aggregate]
    aggregate.func should be ("cardinality")
    aggregate.param should be ("resource_id")
  }

  "Alarm" should "be mapped correctly" in{
    val alarm = """{
                  |    "alarm_actions": [
                  |        "http://site:8000/alarm"
                  |    ],
                  |    "alarm_id": null,
                  |    "combination_rule": {
                  |        "alarm_ids": [
                  |            "739e99cb-c2ec-4718-b900-332502355f38",
                  |            "153462d0-a9b8-4b5b-8175-9e4b05e9b856"
                  |        ],
                  |        "operator": "or"
                  |    },
                  |    "description": "An alarm",
                  |    "enabled": true,
                  |    "insufficient_data_actions": [
                  |        "http://site:8000/nodata"
                  |    ],
                  |    "name": "SwiftObjectAlarm",
                  |    "ok_actions": [
                  |        "http://site:8000/ok"
                  |    ],
                  |    "project_id": "c96c887c216949acbdfbd8b494863567",
                  |    "repeat_actions": false,
                  |    "state": "ok",
                  |    "state_timestamp": "2014-10-20T18:40:42.241264",
                  |    "threshold_rule": null,
                  |    "time_constraints": [
                  |        {
                  |            "description": "nightly build every night at 23h for 3 hours",
                  |            "duration": 10800,
                  |            "name": "SampleConstraint",
                  |            "start": "0 23 * * *",
                  |            "timezone": "Europe/Ljubljana"
                  |        }
                  |    ],
                  |    "timestamp": "2014-10-20T18:40:42.241256",
                  |    "type": "combination",
                  |    "user_id": "c96c887c216949acbdfbd8b494863567"
                  |}""".stripMargin.parseJson.convertTo[Alarm]
  }
  "AlarmChange" should "be mapped correctly" in{
    val alarmChange = """{
                        |    "alarm_id": "e8ff32f772a44a478182c3fe1f7cad6a",
                        |    "detail": "{\"threshold\": 42.0, \"evaluation_periods\": 4}",
                        |    "on_behalf_of": "92159030020611e3b26dde429e99ee8c",
                        |    "project_id": "b6f16144010811e387e4de429e99ee8c",
                        |    "timestamp": "2014-10-20T18:40:42.466390",
                        |    "type": "rule change",
                        |    "user_id": "3e5d11fda79448ac99ccefb20be187ca"
                        |}""".stripMargin.parseJson.convertTo[AlarmChange]
  }

  "Query" should "be mapped correctly" in{
    import DefaultJsonProtocol._
    val simpleQuery = """[{
                        |    "field": "resource_id",
                        |    "op": "eq",
                        |    "type": "string",
                        |    "value": "bd9431c1-8d69-4ad3-803a-8d4a6b89fd36"
                        |}]""".stripMargin.parseJson.convertTo[List[SimpleQuery]]
  }

  "ComplexQuery" should "be mapped correctly" in{
    val complexQuery = """{
                         |    "filter": "{\"and\": [{\"and\": [{\"=\": {\"counter_name\": \"cpu_util\"}}, {\">\": {\"counter_volume\": 0.23}}, {\"<\": {\"counter_volume\": 0.26}}]}, {\"or\": [{\"and\": [{\">\": {\"timestamp\": \"2013-12-01T18:00:00\"}}, {\"<\": {\"timestamp\": \"2013-12-01T18:15:00\"}}]}, {\"and\": [{\">\": {\"timestamp\": \"2013-12-01T18:30:00\"}}, {\"<\": {\"timestamp\": \"2013-12-01T18:45:00\"}}]}]}]}",
                         |    "limit": 42,
                         |    "orderby": "[{\"counter_volume\": \"ASC\"}, {\"timestamp\": \"DESC\"}]"
                         |}""".stripMargin.parseJson.convertTo[ComplexQuery]
  }
}