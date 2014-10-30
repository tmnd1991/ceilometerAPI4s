package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

import org.openstack.api.restful.FilterExpressions.FieldValue

/**
 * Created by tmnd on 18/10/14.
 */
case class SimpleInExpression(fieldName : String, values : Seq[FieldValue], `type` : String) extends Expression{
  override def toString = fieldName + " in " + values.mkString(", ")
}
