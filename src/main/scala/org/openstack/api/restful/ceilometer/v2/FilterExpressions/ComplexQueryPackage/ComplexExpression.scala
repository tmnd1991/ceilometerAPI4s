package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

/**
 * abstract ceilometer ComplexExpression representation
 * implementations are MultiComplexExpression and UnaryComplexExpression
 * as explained at http://wiki.openstack.org/wiki/Ceilometer/ComplexFilterExpressionsInAPIQueries
 * @author Antonio Murgia
 * @version 18/10/14
 */
abstract class ComplexExpression extends Expression
