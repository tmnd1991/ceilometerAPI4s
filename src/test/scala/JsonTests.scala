/**
 * Created by tmnd on 19/10/14.
 */

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.ComplexQuery
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.SimpleQuery
import org.openstack.api.restful.keystone.v2.responses.TokenResponse
import org.scalatest._

import spray.json._

import org.openstack.api.restful.ceilometer.v2.elements._
import org.openstack.api.restful.ceilometer.v2.elements.JsonConversions._
import org.openstack.api.restful.keystone.v2.responses.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.JsonConversions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._

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

  "A TokenResponse" should "be mapped correctly" in{
    val tokenResponse =
      """{"access": {"token": {"issued_at": "2014-11-09T16:04:11.661062", "expires": "2014-11-09T17:04:11Z", "id": "MIIRigYJKoZIhvcNAQcCoIIRezCCEXcCAQExDTALBglghkgBZQMEAgEwgg-YBgkqhkiG9w0BBwGggg-JBIIPxXsiYWNjZXNzIjogeyJ0b2tlbiI6IHsiaXNzdWVkX2F0IjogIjIwMTQtMTEtMDlUMTY6MDQ6MTEuNjYxMDYyIiwgImV4cGlyZXMiOiAiMjAxNC0xMS0wOVQxNzowNDoxMVoiLCAiaWQiOiAicGxhY2Vob2xkZXIiLCAidGVuYW50IjogeyJkZXNjcmlwdGlvbiI6ICIiLCAiZW5hYmxlZCI6IHRydWUsICJpZCI6ICJjMWMyYmVjMWZmYjQ0MjhjYmM5MGU5ZDA3MWQyNGE2YSIsICJuYW1lIjogImNlaWxvbWV0ZXJfcHJvamVjdCJ9fSwgInNlcnZpY2VDYXRhbG9nIjogW3siZW5kcG9pbnRzIjogW3siYWRtaW5VUkwiOiAiaHR0cDovLzE5Mi4xNjguNzAuMTo4Nzc0L3YyL2MxYzJiZWMxZmZiNDQyOGNiYzkwZTlkMDcxZDI0YTZhIiwgInJlZ2lvbiI6ICJSZWdpb25PbmUiLCAiaW50ZXJuYWxVUkwiOiAiaHR0cDovLzE5Mi4xNjguNzAuMTo4Nzc0L3YyL2MxYzJiZWMxZmZiNDQyOGNiYzkwZTlkMDcxZDI0YTZhIiwgImlkIjogIjA1NDcwM2VlNTlmZjQ5NmU4ZDI2ZDZmYmM0YWQ2OWJjIiwgInB1YmxpY1VSTCI6ICJodHRwOi8vMTM3LjIwNC41Ny4xNTA6ODc3NC92Mi9jMWMyYmVjMWZmYjQ0MjhjYmM5MGU5ZDA3MWQyNGE2YSJ9XSwgImVuZHBvaW50c19saW5rcyI6IFtdLCAidHlwZSI6ICJjb21wdXRlIiwgIm5hbWUiOiAibm92YSJ9LCB7ImVuZHBvaW50cyI6IFt7ImFkbWluVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6OTY5Ni8iLCAicmVnaW9uIjogIlJlZ2lvbk9uZSIsICJpbnRlcm5hbFVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjk2OTYvIiwgImlkIjogIjI5ZmEyYjI1YTk4NTQwMDk4YjRkZjUzNzg0MDExNTU5IiwgInB1YmxpY1VSTCI6ICJodHRwOi8vMTM3LjIwNC41Ny4xNTA6OTY5Ni8ifV0sICJlbmRwb2ludHNfbGlua3MiOiBbXSwgInR5cGUiOiAibmV0d29yayIsICJuYW1lIjogIm5ldXRyb24ifSwgeyJlbmRwb2ludHMiOiBbeyJhZG1pblVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzYvdjIvYzFjMmJlYzFmZmI0NDI4Y2JjOTBlOWQwNzFkMjRhNmEiLCAicmVnaW9uIjogIlJlZ2lvbk9uZSIsICJpbnRlcm5hbFVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzYvdjIvYzFjMmJlYzFmZmI0NDI4Y2JjOTBlOWQwNzFkMjRhNmEiLCAiaWQiOiAiOTQwMWY2N2M0M2QzNDU2OWE2ZGIxNTY2YWRhZTQ3OGEiLCAicHVibGljVVJMIjogImh0dHA6Ly8xMzcuMjA0LjU3LjE1MDo4Nzc2L3YyL2MxYzJiZWMxZmZiNDQyOGNiYzkwZTlkMDcxZDI0YTZhIn1dLCAiZW5kcG9pbnRzX2xpbmtzIjogW10sICJ0eXBlIjogInZvbHVtZXYyIiwgIm5hbWUiOiAiY2luZGVydjIifSwgeyJlbmRwb2ludHMiOiBbeyJhZG1pblVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzQvdjMiLCAicmVnaW9uIjogIlJlZ2lvbk9uZSIsICJpbnRlcm5hbFVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzQvdjMiLCAiaWQiOiAiMzVlMjE2ZjkzZDNlNGMzMDk1YTM5YjdjODc4ZDNlZmIiLCAicHVibGljVVJMIjogImh0dHA6Ly8xMzcuMjA0LjU3LjE1MDo4Nzc0L3YzIn1dLCAiZW5kcG9pbnRzX2xpbmtzIjogW10sICJ0eXBlIjogImNvbXB1dGV2MyIsICJuYW1lIjogIm5vdmF2MyJ9LCB7ImVuZHBvaW50cyI6IFt7ImFkbWluVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6OTI5MiIsICJyZWdpb24iOiAiUmVnaW9uT25lIiwgImludGVybmFsVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6OTI5MiIsICJpZCI6ICI0YTBlY2NiMDM5NzI0ZmI5OWM0M2NlYTUxMDRmNTNiMiIsICJwdWJsaWNVUkwiOiAiaHR0cDovLzEzNy4yMDQuNTcuMTUwOjkyOTIifV0sICJlbmRwb2ludHNfbGlua3MiOiBbXSwgInR5cGUiOiAiaW1hZ2UiLCAibmFtZSI6ICJnbGFuY2UifSwgeyJlbmRwb2ludHMiOiBbeyJhZG1pblVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzcvIiwgInJlZ2lvbiI6ICJSZWdpb25PbmUiLCAiaW50ZXJuYWxVUkwiOiAiaHR0cDovLzE5Mi4xNjguNzAuMTo4Nzc3LyIsICJpZCI6ICIwNmU4Y2E0ZjE2YWU0YTYzOGY1NTIzYjI2NjZhNDRkOCIsICJwdWJsaWNVUkwiOiAiaHR0cDovLzEzNy4yMDQuNTcuMTUwOjg3NzcvIn1dLCAiZW5kcG9pbnRzX2xpbmtzIjogW10sICJ0eXBlIjogIm1ldGVyaW5nIiwgIm5hbWUiOiAiY2VpbG9tZXRlciJ9LCB7ImVuZHBvaW50cyI6IFt7ImFkbWluVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6ODAwMC92MSIsICJyZWdpb24iOiAiUmVnaW9uT25lIiwgImludGVybmFsVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6ODAwMC92MSIsICJpZCI6ICI3NGQ1ZDM1YjExMzE0Nzc2YmYxMWIyMzU3YzViZWMzNiIsICJwdWJsaWNVUkwiOiAiaHR0cDovLzEzNy4yMDQuNTcuMTUwOjgwMDAvdjEifV0sICJlbmRwb2ludHNfbGlua3MiOiBbXSwgInR5cGUiOiAiY2xvdWRmb3JtYXRpb24iLCAibmFtZSI6ICJoZWF0In0sIHsiZW5kcG9pbnRzIjogW3siYWRtaW5VUkwiOiAiaHR0cDovLzE5Mi4xNjguNzAuMTo4Nzc2L3YxL2MxYzJiZWMxZmZiNDQyOGNiYzkwZTlkMDcxZDI0YTZhIiwgInJlZ2lvbiI6ICJSZWdpb25PbmUiLCAiaW50ZXJuYWxVUkwiOiAiaHR0cDovLzE5Mi4xNjguNzAuMTo4Nzc2L3YxL2MxYzJiZWMxZmZiNDQyOGNiYzkwZTlkMDcxZDI0YTZhIiwgImlkIjogIjAxNjk0MWNiZjYxNDQ1ZTY5YjQ4MGRmZWU4MmY0OTQxIiwgInB1YmxpY1VSTCI6ICJodHRwOi8vMTM3LjIwNC41Ny4xNTA6ODc3Ni92MS9jMWMyYmVjMWZmYjQ0MjhjYmM5MGU5ZDA3MWQyNGE2YSJ9XSwgImVuZHBvaW50c19saW5rcyI6IFtdLCAidHlwZSI6ICJ2b2x1bWUiLCAibmFtZSI6ICJjaW5kZXIifSwgeyJlbmRwb2ludHMiOiBbeyJhZG1pblVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzMvc2VydmljZXMvQWRtaW4iLCAicmVnaW9uIjogIlJlZ2lvbk9uZSIsICJpbnRlcm5hbFVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjg3NzMvc2VydmljZXMvQ2xvdWQiLCAiaWQiOiAiMmY1MGQ5ZDIxNmM3NDkwZWEzM2VmYzUwZTRlOTA4ZjMiLCAicHVibGljVVJMIjogImh0dHA6Ly8xMzcuMjA0LjU3LjE1MDo4NzczL3NlcnZpY2VzL0Nsb3VkIn1dLCAiZW5kcG9pbnRzX2xpbmtzIjogW10sICJ0eXBlIjogImVjMiIsICJuYW1lIjogImVjMiJ9LCB7ImVuZHBvaW50cyI6IFt7ImFkbWluVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6ODAwNC92MS9jMWMyYmVjMWZmYjQ0MjhjYmM5MGU5ZDA3MWQyNGE2YSIsICJyZWdpb24iOiAiUmVnaW9uT25lIiwgImludGVybmFsVVJMIjogImh0dHA6Ly8xOTIuMTY4LjcwLjE6ODAwNC92MS9jMWMyYmVjMWZmYjQ0MjhjYmM5MGU5ZDA3MWQyNGE2YSIsICJpZCI6ICI0ODMyZDk1ZjE1YzA0YTM5OWNiMWE3NmRlMTRkYzAyOCIsICJwdWJsaWNVUkwiOiAiaHR0cDovLzEzNy4yMDQuNTcuMTUwOjgwMDQvdjEvYzFjMmJlYzFmZmI0NDI4Y2JjOTBlOWQwNzFkMjRhNmEifV0sICJlbmRwb2ludHNfbGlua3MiOiBbXSwgInR5cGUiOiAib3JjaGVzdHJhdGlvbiIsICJuYW1lIjogImhlYXQifSwgeyJlbmRwb2ludHMiOiBbeyJhZG1pblVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjM1MzU3L3YyLjAiLCAicmVnaW9uIjogIlJlZ2lvbk9uZSIsICJpbnRlcm5hbFVSTCI6ICJodHRwOi8vMTkyLjE2OC43MC4xOjUwMDAvdjIuMCIsICJpZCI6ICI2NGNlNWIxZDY5ZjM0OGFhYjc2NTIyMGVmOWQ4YTRlOCIsICJwdWJsaWNVUkwiOiAiaHR0cDovLzEzNy4yMDQuNTcuMTUwOjUwMDAvdjIuMCJ9XSwgImVuZHBvaW50c19saW5rcyI6IFtdLCAidHlwZSI6ICJpZGVudGl0eSIsICJuYW1lIjogImtleXN0b25lIn1dLCAidXNlciI6IHsidXNlcm5hbWUiOiAiYW11cmdpYSIsICJyb2xlc19saW5rcyI6IFtdLCAiaWQiOiAiNjNkOTUwYTgxM2YxNGQwMTk5NWNmNmJhYmI2MjY5YjgiLCAicm9sZXMiOiBbeyJuYW1lIjogIl9tZW1iZXJfIn0sIHsibmFtZSI6ICJhZG1pbiJ9XSwgIm5hbWUiOiAiYW11cmdpYSJ9LCAibWV0YWRhdGEiOiB7ImlzX2FkbWluIjogMCwgInJvbGVzIjogWyI5ZmUyZmY5ZWU0Mzg0YjE4OTRhOTA4NzhkM2U5MmJhYiIsICIyYzM0Y2QxNzdmMzc0N2EyYTU5MjJjZWUzNzc1ZWY5YSJdfX19MYIBhTCCAYECAQEwXDBXMQswCQYDVQQGEwJVUzEOMAwGA1UECAwFVW5zZXQxDjAMBgNVBAcMBVVuc2V0MQ4wDAYDVQQKDAVVbnNldDEYMBYGA1UEAwwPd3d3LmV4YW1wbGUuY29tAgEBMAsGCWCGSAFlAwQCATANBgkqhkiG9w0BAQEFAASCAQAn0vY6mlt0qHbIaskVLbPGw+1EiDRanom4RWmM6hC4MpABnQG3wUTszZzTHoXGbUmRd288CO5DmDbDwmma7Xjmwpdo8FMy6KzSJ1U650hqAKkU7udgy+Qn4DmsJgBO2iVWxH7qB-vHBsrhufPNJdmbkblOetJAjeFGWIlH1hLvGnjQaDjRUa6jR8FjSF04AvfRQbBiUcW9VdvtmywifIjdYClk0clKY0tLCz+W-wlybKRcNX7DzMpPLKF71t08xZQwCN3cuWuu+xhsVMoU1HJjicMJCm2Vl+BoWIlwcPJRy7G5PXOsxq25BGIuqGI7kq-JInNFGMkD5dPi-ng9yaxe", "tenant": {"description": "", "enabled": true, "id": "c1c2bec1ffb4428cbc90e9d071d24a6a", "name": "ceilometer_project"}}, "serviceCatalog": [{"endpoints": [{"adminURL": "http://192.168.70.1:8774/v2/c1c2bec1ffb4428cbc90e9d071d24a6a", "region": "RegionOne", "internalURL": "http://192.168.70.1:8774/v2/c1c2bec1ffb4428cbc90e9d071d24a6a", "id": "054703ee59ff496e8d26d6fbc4ad69bc", "publicURL": "http://137.204.57.150:8774/v2/c1c2bec1ffb4428cbc90e9d071d24a6a"}], "endpoints_links": [], "type": "compute", "name": "nova"}, {"endpoints": [{"adminURL": "http://192.168.70.1:9696/", "region": "RegionOne", "internalURL": "http://192.168.70.1:9696/", "id": "29fa2b25a98540098b4df53784011559", "publicURL": "http://137.204.57.150:9696/"}], "endpoints_links": [], "type": "network", "name": "neutron"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8776/v2/c1c2bec1ffb4428cbc90e9d071d24a6a", "region": "RegionOne", "internalURL": "http://192.168.70.1:8776/v2/c1c2bec1ffb4428cbc90e9d071d24a6a", "id": "9401f67c43d34569a6db1566adae478a", "publicURL": "http://137.204.57.150:8776/v2/c1c2bec1ffb4428cbc90e9d071d24a6a"}], "endpoints_links": [], "type": "volumev2", "name": "cinderv2"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8774/v3", "region": "RegionOne", "internalURL": "http://192.168.70.1:8774/v3", "id": "35e216f93d3e4c3095a39b7c878d3efb", "publicURL": "http://137.204.57.150:8774/v3"}], "endpoints_links": [], "type": "computev3", "name": "novav3"}, {"endpoints": [{"adminURL": "http://192.168.70.1:9292", "region": "RegionOne", "internalURL": "http://192.168.70.1:9292", "id": "4a0eccb039724fb99c43cea5104f53b2", "publicURL": "http://137.204.57.150:9292"}], "endpoints_links": [], "type": "image", "name": "glance"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8777/", "region": "RegionOne", "internalURL": "http://192.168.70.1:8777/", "id": "06e8ca4f16ae4a638f5523b2666a44d8", "publicURL": "http://137.204.57.150:8777/"}], "endpoints_links": [], "type": "metering", "name": "ceilometer"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8000/v1", "region": "RegionOne", "internalURL": "http://192.168.70.1:8000/v1", "id": "74d5d35b11314776bf11b2357c5bec36", "publicURL": "http://137.204.57.150:8000/v1"}], "endpoints_links": [], "type": "cloudformation", "name": "heat"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8776/v1/c1c2bec1ffb4428cbc90e9d071d24a6a", "region": "RegionOne", "internalURL": "http://192.168.70.1:8776/v1/c1c2bec1ffb4428cbc90e9d071d24a6a", "id": "016941cbf61445e69b480dfee82f4941", "publicURL": "http://137.204.57.150:8776/v1/c1c2bec1ffb4428cbc90e9d071d24a6a"}], "endpoints_links": [], "type": "volume", "name": "cinder"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8773/services/Admin", "region": "RegionOne", "internalURL": "http://192.168.70.1:8773/services/Cloud", "id": "2f50d9d216c7490ea33efc50e4e908f3", "publicURL": "http://137.204.57.150:8773/services/Cloud"}], "endpoints_links": [], "type": "ec2", "name": "ec2"}, {"endpoints": [{"adminURL": "http://192.168.70.1:8004/v1/c1c2bec1ffb4428cbc90e9d071d24a6a", "region": "RegionOne", "internalURL": "http://192.168.70.1:8004/v1/c1c2bec1ffb4428cbc90e9d071d24a6a", "id": "4832d95f15c04a399cb1a76de14dc028", "publicURL": "http://137.204.57.150:8004/v1/c1c2bec1ffb4428cbc90e9d071d24a6a"}], "endpoints_links": [], "type": "orchestration", "name": "heat"}, {"endpoints": [{"adminURL": "http://192.168.70.1:35357/v2.0", "region": "RegionOne", "internalURL": "http://192.168.70.1:5000/v2.0", "id": "64ce5b1d69f348aab765220ef9d8a4e8", "publicURL": "http://137.204.57.150:5000/v2.0"}], "endpoints_links": [], "type": "identity", "name": "keystone"}], "user": {"username": "amurgia", "roles_links": [], "id": "63d950a813f14d01995cf6babb6269b8", "roles": [{"name": "_member_"}, {"name": "admin"}], "name": "amurgia"}, "metadata": {"is_admin": 0, "roles": ["9fe2ff9ee4384b1894a90878d3e92bab", "2c34cd177f3747a2a5922cee3775ef9a"]}}}
        |
      """.stripMargin.parseJson.convertTo[TokenResponse]
  }
}