package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer AlarmState representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
case class AlarmState(s : String) extends Serializable
object AlarmState{
  val values = Map(OKSTATE.s -> OKSTATE,
                  ALARMSTATE.s -> ALARMSTATE,
                  INSDATASTATE.s -> INSDATASTATE)
  object OKSTATE extends AlarmState("ok")
  object ALARMSTATE extends AlarmState("alarm")
  object INSDATASTATE extends AlarmState("insufficient data")
}
