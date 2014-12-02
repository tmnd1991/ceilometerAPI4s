package org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage

import myUtils.TypeExtractor
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.{Query, FieldValue}

/**
 * @author Antonio Murgia
 * @version 19/10/14
 * ceilometer SimpleQuery representation
 */
case class SimpleQuery(field : String, op : Operator, value : FieldValue, `type` : Option[String] = None) extends Query