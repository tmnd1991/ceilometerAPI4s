package org.openstack.api.restful.ceilometer.v2.elements

/**
 * Created by tmnd on 21/10/14.
 */
class MeterType(val s : String)
object MeterType{
  val values = Map("gauge" -> GAUGE,
                   "delta" -> DELTA,
                   "cumulative" -> CUMULATIVE
                  )
  object GAUGE extends MeterType("gauge")
  object DELTA extends MeterType("delta")
  object CUMULATIVE extends MeterType("cumulative")
}