package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue

/**
 * Created by tmnd on 18/10/14.
 */
case class SimpleExpression(o : SimpleOperator, fieldName : String, value : FieldValue, `type` : String) extends Expression{
  override def toString = fieldName + " " + o.s + " " + value
}
