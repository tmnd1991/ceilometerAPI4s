package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer MeterType representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
abstract class MeterType(val s : String) extends Serializable{
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