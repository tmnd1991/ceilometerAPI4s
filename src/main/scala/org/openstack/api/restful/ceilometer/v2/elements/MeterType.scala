package org.openstack.api.restful.ceilometer.v2.elements

/**
 * @author Antonio Murgia
 * @version 22/10/14
 * ceilometer MeterType representation
 */
class MeterType(val s : String){
  override def toString = s
}
object MeterType{
  val values = Map(GAUGE.s -> GAUGE,
                   DELTA.s -> DELTA,
                   CUMULATIVE.s -> CUMULATIVE
                  )
  object GAUGE extends MeterType("gauge")
  object DELTA extends MeterType("delta")
  object CUMULATIVE extends MeterType("cumulative")
}