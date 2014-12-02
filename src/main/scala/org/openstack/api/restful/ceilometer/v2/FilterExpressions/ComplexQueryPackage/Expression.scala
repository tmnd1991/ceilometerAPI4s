package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * @author Antonio Murgia
 * @version 18/10/14
 * abstraction over ceilometer expression implemented subclasses are ComplexExpression, SimpleExpression and
 * SimpleInExpression
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 */

abstract class Expression {
  def AND(e : Expression) = {
    MultiComplexExpression(MultiComplexOperator.AndOperator,List(this,e))
  }
  def OR(e : Expression) = {
    MultiComplexExpression(MultiComplexOperator.OrOperator,List(this,e))
  }
}