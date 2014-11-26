package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
abstract class SimpleOperator(val s : String){
}
object SimpleOperator{
  val values = Map("=" -> Equals,
                   "!=" -> NotEquals,
                   ">" -> GreaterThan,
                   ">=" -> GreaterOrEqualsThan,
                   "<" -> LesserThan,
                   "<=" -> LesserOrEqualsThan)
  object Equals extends SimpleOperator("=")
  object NotEquals extends SimpleOperator("!=")
  object GreaterThan extends SimpleOperator(">")
  object GreaterOrEqualsThan extends SimpleOperator(">=")
  object LesserThan extends SimpleOperator("<")
  object LesserOrEqualsThan extends SimpleOperator("<=")
}