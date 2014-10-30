package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */

import org.openstack.api.restful.FilterExpressions.ComplexQueryPackage.JsonConversions.FilterJsonFormat
import org.openstack.api.restful.FilterExpressions.Query
import spray.json.JsString

case class ComplexQuery(filter : Filter, limit : Option[Int] = None , orderBy : Option[Seq[OrderBy]] = None) extends Query{

}
object ComplexQuery{
  def apply(filter : String, limit : Option[Int], orderBy : Option[Seq[OrderBy]]) : ComplexQuery = {
    ComplexQuery(FilterJsonFormat.read(JsString(filter)), limit, orderBy)
  }
}