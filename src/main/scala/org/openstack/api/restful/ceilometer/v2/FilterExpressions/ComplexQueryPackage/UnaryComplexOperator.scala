package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * @author Antonio Murgia
 * @version 18/10/14
 * ceilometer UnaryComplexOperator representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 */
class UnaryComplexOperator(val s : String)
object UnaryComplexOperator{
  val values = Map("not" -> NotOperator)
  object NotOperator extends UnaryComplexOperator("not")
}
