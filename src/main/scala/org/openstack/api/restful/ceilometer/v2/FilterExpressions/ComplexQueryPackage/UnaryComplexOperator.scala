package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * ceilometer UnaryComplexOperator representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */
class UnaryComplexOperator(val s : String) extends Serializable
object UnaryComplexOperator{
  val values = Map(NotOperator.s -> NotOperator)
  object NotOperator extends UnaryComplexOperator("not")
}
