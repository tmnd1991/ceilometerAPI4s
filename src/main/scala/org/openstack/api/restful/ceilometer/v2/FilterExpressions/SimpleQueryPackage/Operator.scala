package org.openstack.api.restful.FilterExpressions.SimpleQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
abstract class Operator(val s : String) extends Serializable

object Operator{
  object lt extends Operator("lt")
  object le extends Operator("le")
  object eq extends Operator("eq")
  object ne extends Operator("ne")
  object ge extends Operator("ge")
  object gt extends Operator("gt")

  val values = Map("lt" -> lt,
  "le" -> le,
  "eq" -> org.openstack.api.restful.FilterExpressions.SimpleQueryPackage.Operator.eq,
  "ne" -> org.openstack.api.restful.FilterExpressions.SimpleQueryPackage.Operator.ne,
  "ge" -> ge,
  "gt" -> gt)
}