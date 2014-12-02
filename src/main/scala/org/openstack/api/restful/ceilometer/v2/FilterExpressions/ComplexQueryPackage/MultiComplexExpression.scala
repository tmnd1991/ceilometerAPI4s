package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * @author Antonio Murgia
 * @version 18/10/14
 * ceilometer MultiComplexExpression representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 */
case class MultiComplexExpression(o : MultiComplexOperator, expressions : Seq[Expression]) extends ComplexExpression{
  override def toString = expressions.mkString(" " + o.s + " ")
}
