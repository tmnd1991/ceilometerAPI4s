package org.openstack.api.restful.ceilometer.v2.elements

/**
 * ceilometer Alarm representation
 * @author Antonio Murgia
 * @version 22/10/14
 */
import java.util.Date

case class Alarm(alarm_actions : Seq[String],
                 alarm_id : Option[String],
                 combination_rule : AlarmCombinationRule,
                 description : String,
                 enabled : Boolean,
                 insufficient_data_actions : Seq[String],
                 name : String,
                 ok_actions : Seq[String],
                 project_id : String,
                 repeat_actions : Boolean,
                 state : AlarmState,
                 state_timestamp : java.sql.Timestamp,
                 threshold_rule : Option[AlarmThresholdRule],
                 time_constraints : Seq[AlarmTimeConstraint],
                 timestamp : java.sql.Timestamp,
                 `type` : AlarmType,
                 user_id : String)
/*
alarm_actions
Type:	list(unicode)
The actions to do when alarm state change to alarm

alarm_id
Type:	unicode
The UUID of the alarm

combination_rule
Type:	AlarmCombinationRule
Describe when to trigger the alarm based on combining the state of other alarms

description
Type:	unicode
The description of the alarm

enabled
Type:	bool
This alarm is enabled?

insufficient_data_actions
Type:	list(unicode)
The actions to do when alarm state change to insufficient data

name
Type:	unicode
The name for the alarm

ok_actions
Type:	list(unicode)
The actions to do when alarm state change to ok

project_id
Type:	unicode
The ID of the project or tenant that owns the alarm

repeat_actions
Type:	bool
The actions should be re-triggered on each evaluation cycle

state
Type:	Enum(ok, alarm, insufficient data)
The state offset the alarm

state_timestamp
Type:	datetime
The date of the last alarm state changed

threshold_rule
Type:	AlarmThresholdRule
Describe when to trigger the alarm based on computed statistics

time_constraints
Type:	list(AlarmTimeConstraint)
Describe time constraints for the alarm

timestamp
Type:	datetime
The date of the last alarm definition update

type
Type:	Enum(threshold, combination)
Explicit type specifier to select which rule to follow below.

user_id
Type:	unicode
The ID of the user who created the alarm
*/