package org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage

/**
 * ceilometer SimpleQuery Permitted Operators
 * @author Antonio Murgia
 * @version 19/10/14
 */
abstract class Operator(val s : String) extends Serializable

object Operator{
  object lt extends Operator("lt")
  object le extends Operator("le")
  object eq extends Operator("eq")
  object ne extends Operator("ne")
  object ge extends Operator("ge")
  object gt extends Operator("gt")

  val values = Map(lt.s -> lt,
  le.s -> le,
  org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Operator.eq.s -> org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Operator.eq,
  org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Operator.ne.s -> org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Operator.ne,
  ge.s -> ge,
  gt.s -> gt)
}