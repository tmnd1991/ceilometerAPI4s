package org.openstack.api.restful.FilterExpressions.ComplexQueryPackage

/**
 * Created by tmnd on 19/10/14.
 */
case class Filter(e : Expression) {
  override def toString = e.toString
}
