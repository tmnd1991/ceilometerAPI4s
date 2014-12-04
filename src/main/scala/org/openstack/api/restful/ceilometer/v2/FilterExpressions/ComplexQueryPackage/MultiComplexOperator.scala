package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * ceilometer MultiComplexOperator representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */
abstract class MultiComplexOperator(val s : String)
object MultiComplexOperator{
  val values = Map("and" -> AndOperator,
                   "or" -> OrOperator)
  object AndOperator extends MultiComplexOperator("and")
  object OrOperator extends MultiComplexOperator("or")
}
