package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

import java.io.Serializable

/**
 * ceilometer MultiComplexOperator representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */
/*
abstract class MultiComplexOperator(val s : String) extends Serializable{
}
*/
abstract class MultiComplexOperator(val s : String) extends Serializable

object MultiComplexOperator{
  val values = Map(AndOperator.s -> AndOperator,
                   OrOperator.s -> OrOperator)
  object AndOperator extends MultiComplexOperator("and")
  object OrOperator extends MultiComplexOperator("or")
  /*
  val AndOperator = new MultiComplexOperator("and") {}
  */
  /*object OrOperator extends AnyVal with MultiComplexOperator{
    val s = "or"
  }*/
}
