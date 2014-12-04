package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * ceilometer MultiComplexExpression representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */

case class MultiComplexExpression(o : MultiComplexOperator, expressions : Seq[Expression]) extends ComplexExpression{
  override def toString = expressions.mkString(" " + o.s + " ")
}
