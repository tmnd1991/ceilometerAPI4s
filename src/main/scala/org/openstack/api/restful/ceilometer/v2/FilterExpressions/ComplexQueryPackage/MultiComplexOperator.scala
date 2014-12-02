package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * @author Antonio Murgia
 * @version 18/10/14
 * ceilometer MultiComplexOperator representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 */
abstract class MultiComplexOperator(val s : String)
object MultiComplexOperator{
  val values = Map("and" -> AndOperator,
                   "or" -> OrOperator)
  object AndOperator extends MultiComplexOperator("and")
  object OrOperator extends MultiComplexOperator("or")
}
