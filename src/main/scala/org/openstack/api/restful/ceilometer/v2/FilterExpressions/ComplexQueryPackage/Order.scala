package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
abstract class Order(val s : String)
object Order{
  val values = Map("ASC"->ASC,
                  "DESC" -> DESC)
  object ASC extends Order("ASC")
  object DESC extends Order("DESC")
}
