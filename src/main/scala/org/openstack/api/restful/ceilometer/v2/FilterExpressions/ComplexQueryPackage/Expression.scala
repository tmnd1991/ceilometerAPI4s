package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * abstraction over ceilometer expression implemented subclasses are ComplexExpression, SimpleExpression and
 * SimpleInExpression
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * While using boolean operators to aggregate expressions ALWAYS use parenthesis.
 * The use of operators without parenthesis is misleading and NOT SUPPORTED (the associativity of the operators is
 * always on the LEFT).
 * @author Antonio Murgia
 * @version 18/10/14
 */

abstract class Expression {
  def AND(e : Expression) = {
    MultiComplexExpression(MultiComplexOperator.AndOperator,List(this,e))
  }
  def OR(e : Expression) = {
    MultiComplexExpression(MultiComplexOperator.OrOperator,List(this,e))
  }
}