package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
abstract class MultiComplexOperator(val s : String)
object MultiComplexOperator{
  val values = Map("and" -> AndOperator,
                   "or" -> OrOperator)
  object AndOperator extends MultiComplexOperator("and")
  object OrOperator extends MultiComplexOperator("or")
}
