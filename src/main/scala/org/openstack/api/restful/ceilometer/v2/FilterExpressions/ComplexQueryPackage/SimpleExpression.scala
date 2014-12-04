package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue

/**
 * ceilometer SimpleExpression representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */
case class SimpleExpression(o : SimpleOperator, fieldName : String, value : FieldValue, `type` : String) extends Expression{
  override def toString = fieldName + " " + o.s + " " + value
}
