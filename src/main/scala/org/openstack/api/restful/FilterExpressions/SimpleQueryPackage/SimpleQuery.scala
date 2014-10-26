package org.openstack.api.restful.FilterExpressions.SimpleQueryPackage

import myUtils.TypeExtractor
import org.openstack.api.restful.FilterExpressions.{Query, FieldValue}

/**
 * Created by tmnd on 19/10/14.
 */
case class SimpleQuery(field : String, op : Operator, value : FieldValue, `type` : String) extends Query{
}