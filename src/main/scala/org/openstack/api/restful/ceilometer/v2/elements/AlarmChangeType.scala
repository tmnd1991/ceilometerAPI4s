package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer AlarmChangeType representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
class AlarmChangeType(val s : String)
object AlarmChangeType{
  val values = Map(CREATION.s -> CREATION,
                   RULECHANGE.s -> RULECHANGE,
                   STATETRANSITION.s -> STATETRANSITION,
                   DELETION.s -> DELETION)
  object CREATION extends AlarmChangeType("creation")
  object RULECHANGE extends AlarmChangeType("rule change")
  object STATETRANSITION extends AlarmChangeType("state transition")
  object DELETION extends AlarmChangeType("deletion")
}