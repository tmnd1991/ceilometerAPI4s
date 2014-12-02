package org.openstack.api.restful.ceilometer.v2.elements

/**
 * @author Antonio Murgia
 * @version 22/10/14
 * ceilometer AlarmType representation
 */
class AlarmType(val s : String)
object AlarmType{
  val values = Map(THRESHOLD.s -> THRESHOLD,
                   COMBINATION.s -> COMBINATION)
  object THRESHOLD extends AlarmType("threshold")
  object COMBINATION extends AlarmType("combination")
}
