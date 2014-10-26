package org.openstack.api.restful.ceilometer.v2.elements

/**
 * Created by tmnd on 21/10/14.
 */
class AlarmType(val s : String)
object AlarmType{
  val values = Map("threshold" -> THRESHOLD,
                   "combination" -> COMBINATION)
  object THRESHOLD extends AlarmType("threshold")
  object COMBINATION extends AlarmType("combination")
}
