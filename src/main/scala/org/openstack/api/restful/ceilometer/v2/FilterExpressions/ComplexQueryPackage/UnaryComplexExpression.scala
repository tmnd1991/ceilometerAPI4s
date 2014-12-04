package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * ceilometer UnaryComplexExpression representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */
case class UnaryComplexExpression(o : UnaryComplexOperator, e : Expression) extends ComplexExpression{
  override def toString = o.s + e.toString
}
