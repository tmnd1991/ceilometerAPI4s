package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

import org.openstack.api.restful.FilterExpressions.FieldValue

/**
 * Created by tmnd on 18/10/14.
 */
case class SimpleExpression(o : SimpleOperator, fieldName : String, value : FieldValue, `type` : String) extends Expression{
  override def toString = fieldName + " " + o.s + " " + value
}
