package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer AlarmCombinationRule representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
case class AlarmCombinationRule(alarm_ids : Seq[String],
                                operator : String) {
  require(operator == "or" || operator == "and")
}
/*
alarm_ids
Type:	list(unicode)
List of alarm identifiers to combine

operator
Type:	Enum(or, and)
How to combine the sub-alarms
*/