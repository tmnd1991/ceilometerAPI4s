package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 18/10/14.
 */
abstract class Expression {
  def AND(e : Expression) = {
    MultiComplexExpression(MultiComplexOperator.AndOperator,List(this, e))
  }
  def OR(e : Expression) = {
    MultiComplexExpression(MultiComplexOperator.OrOperator,List(this, e))
  }
}
