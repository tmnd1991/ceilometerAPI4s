package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer AlarmTimeConstraint representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
case class AlarmTimeConstraint(description : String, duration : Int, name : String, start : String, timezone : String) {

}
/*
description
Type:	unicode
The description of the constraint

duration
Type:	integer
How long the constraint should last, in seconds

name
Type:	unicode
The name of the constraint

start
Type:	cron
Start point of the time constraint, in cron format

timezone
Type:	unicode
Timezone of the constraint

 */