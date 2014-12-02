package org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage

import org.openstack.api.restful.ceilometer.v2.FilterExpressions._


/**
 * @author Antonio Murgia
 * @version 21/10/14
 *          Some shorthand syntax to create SimpleQueries
 */
object Goodies {
  implicit class FieldName(val s : String) extends AnyVal {
    def ====(fv: FieldValue) = SimpleQuery(s, Operator.eq, fv, scala.Some(fv.getType))
    def !!==(fv: FieldValue) = SimpleQuery(s, Operator.le, fv, scala.Some(fv.getType))
    def >>>>(fv: FieldValue) = SimpleQuery(s, Operator.gt, fv, scala.Some(fv.getType))
    def >>==(fv: FieldValue) = SimpleQuery(s, Operator.ge, fv, scala.Some(fv.getType))
    def <<<<(fv: FieldValue) = SimpleQuery(s, Operator.lt, fv, scala.Some(fv.getType))
    def <<==(fv: FieldValue) = SimpleQuery(s, Operator.le, fv, scala.Some(fv.getType))
  }
}
