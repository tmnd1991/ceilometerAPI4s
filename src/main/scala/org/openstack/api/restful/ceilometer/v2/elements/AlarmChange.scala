package org.openstack.api.restful.ceilometer.v2.elements

/**
 * Created by tmnd on 21/10/14.
 */
import java.util.Date

case class AlarmChange(alarm_id : String,
                       detail : String,
                       event_id : Option[String],
                       on_behalf_of : String,
                       project_id : String,
                       timestamp : java.sql.Timestamp,
                       `type`: AlarmChangeType,
                       user_id : String) {

}
/*
alarm_id
Type:	unicode
The UUID of the alarm

detail
Type:	unicode
JSON fragment describing change

event_id
Type:	unicode
The UUID of the change event

on_behalf_of
Type:	unicode
The tenant on behalf of which the change is being made

project_id
Type:	unicode
The project ID of the initiating identity

timestamp
Type:	datetime
The time/date of the alarm change

type
Type:	Enum(creation, rule change, state transition, deletion)
The type of change

user_id
Type:	unicode
The user ID of the initiating identity
 */