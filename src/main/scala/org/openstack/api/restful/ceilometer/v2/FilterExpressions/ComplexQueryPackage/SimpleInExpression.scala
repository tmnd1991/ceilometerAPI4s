package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue

/**
 * @author Antonio Murgia
 * @version 18/10/14
 * ceilometer SimpleInExpression representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 */
case class SimpleInExpression(fieldName : String, values : Seq[FieldValue], `type` : String) extends Expression{
  override def toString = fieldName + " in " + values.mkString(", ")
}
