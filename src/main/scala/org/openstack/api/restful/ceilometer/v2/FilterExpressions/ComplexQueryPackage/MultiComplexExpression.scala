package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
case class MultiComplexExpression(o : MultiComplexOperator, expressions : Seq[Expression]) extends ComplexExpression{
  override def toString = expressions.mkString(" " + o.s + " ")
}
