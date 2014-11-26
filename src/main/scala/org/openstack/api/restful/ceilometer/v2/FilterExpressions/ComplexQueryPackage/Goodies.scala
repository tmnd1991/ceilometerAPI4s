package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage


import org.openstack.api.restful.ceilometer.v2.FilterExpressions._
/**
 * Created by tmnd on 19/10/14.
 */
object Goodies {
  def NOT(e : Expression) = UnaryComplexExpression(UnaryComplexOperator.NotOperator,e)
  implicit class FieldName(val s : String) extends AnyVal{
    def EQ(v : FieldValue) = SimpleExpression(SimpleOperator.Equals,s,v,v.getType)
    def GT(v : FieldValue) = SimpleExpression(SimpleOperator.GreaterThan,s,v,v.getType)
    def GE(v : FieldValue) = SimpleExpression(SimpleOperator.GreaterOrEqualsThan,s,v,v.getType)
    def LT(v : FieldValue) = SimpleExpression(SimpleOperator.LesserThan,s,v,v.getType)
    def LE(v : FieldValue) = SimpleExpression(SimpleOperator.LesserOrEqualsThan,s,v,v.getType)
    def NOTEQ(v : FieldValue) = SimpleExpression(SimpleOperator.NotEquals,s,v,v.getType)
  }
}
