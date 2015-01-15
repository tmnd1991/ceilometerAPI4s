package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer AlarmType representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
class AlarmType(val s : String) extends Serializable
object AlarmType{
  val values = Map(THRESHOLD.s -> THRESHOLD,
                   COMBINATION.s -> COMBINATION)
  object THRESHOLD extends AlarmType("threshold")
  object COMBINATION extends AlarmType("combination")
}
