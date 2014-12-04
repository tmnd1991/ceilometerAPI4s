package org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.{Query, FieldValue}

/**
 * ceilometer SimpleQuery representation
 * @author Antonio Murgia
 * @version 19/10/14
 */
case class SimpleQuery(field : String, op : Operator, value : FieldValue, `type` : Option[String] = None) extends Query