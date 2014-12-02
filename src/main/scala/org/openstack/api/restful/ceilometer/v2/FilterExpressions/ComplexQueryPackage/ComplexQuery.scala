package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * @author Antonio Murgia
 * @version 18/10/14
 * ceilometer ComplexQuery representation
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 */

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.JsonConversions.FilterJsonFormat
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import spray.json.JsString

case class ComplexQuery(filter : Filter, limit : Option[Int] = None , orderBy : Option[Seq[OrderBy]] = None) extends Query{

}
object ComplexQuery{
  def apply(filter : String, limit : Option[Int], orderBy : Option[Seq[OrderBy]]) : ComplexQuery = {
    ComplexQuery(FilterJsonFormat.read(JsString(filter)), limit, orderBy)
  }
}