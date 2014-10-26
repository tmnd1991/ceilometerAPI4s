package org.openstack.api.restful.ceilometer.v2.elements

/**
 * Created by tmnd on 21/10/14.
 */
class AlarmChangeType(val s : String)
object AlarmChangeType{
  val values = Map("creation" -> CREATION,
                   "rule change" -> RULECHANGE,
                   "state transition" -> STATETRANSITION,
                   "deletion" -> DELETION)
  object CREATION extends AlarmChangeType("creation")
  object RULECHANGE extends AlarmChangeType("rule change")
  object STATETRANSITION extends AlarmChangeType("state transition")
  object DELETION extends AlarmChangeType("deletion")
}