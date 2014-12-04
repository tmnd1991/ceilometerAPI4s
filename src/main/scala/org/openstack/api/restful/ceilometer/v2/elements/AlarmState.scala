package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer AlarmState representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
case class AlarmState(s : String)
object AlarmState{
  val values = Map("ok" -> OKSTATE,
    "alarm" -> ALARMSTATE,
    "insufficient data" -> INSDATASTATE)
  object OKSTATE extends AlarmState("ok")
  object ALARMSTATE extends AlarmState("alarm")
  object INSDATASTATE extends AlarmState("insufficient data")
}
