package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue

/**
 * Created by tmnd on 18/10/14.
 */
case class SimpleInExpression(fieldName : String, values : Seq[FieldValue], `type` : String) extends Expression{
  override def toString = fieldName + " in " + values.mkString(", ")
}
