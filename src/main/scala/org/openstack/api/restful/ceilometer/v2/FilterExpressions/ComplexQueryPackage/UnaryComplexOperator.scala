package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
class UnaryComplexOperator(val s : String)
object UnaryComplexOperator{
  val values = Map("not" -> NotOperator)
  object NotOperator extends UnaryComplexOperator("not")
}