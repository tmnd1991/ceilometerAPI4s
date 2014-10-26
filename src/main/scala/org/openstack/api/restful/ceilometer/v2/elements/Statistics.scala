package org.openstack.api.restful.ceilometer.v2.elements

import java.util.Date

/**
 * Created by tmnd on 21/10/14.
 */
case class Statistics(aggregate : Option[Map[String,Float]],
                      avg : Float,
                      count : Int,
                      duration : Float,
                      duration_end : Date,
                      duration_start : Date,
                      groupby : Option[Map[String, String]],
                      max : Float,
                      min : Float,
                      period : Int,
                      period_end : Date,
                      period_start : Date,
                      sum : Float, unit : String) {

}

/*
aggregate
Type:	dict(unicode: float)
The selectable aggregate value(s)

avg
Type:	float
The average of all of the volume values seen in the data

count
Type:	int
The number of samples seen

duration
Type:	float
The difference, in seconds, between the oldest and newest timestamp

duration_end
Type:	datetime
UTC date and time of the oldest timestamp, or the query end time

duration_start
Type:	datetime
UTC date and time of the earliest timestamp, or the query start time

groupby
Type:	dict(unicode: unicode)
Dictionary of field names for group, if groupby statistics are requested

max
Type:	float
The maximum volume seen in the data

min
Type:	float
The minimum volume seen in the data

period
Type:	int
The difference, in seconds, between the period start and end

period_end
Type:	datetime
UTC date and time of the period end

period_start
Type:	datetime
UTC date and time of the period start

sum
Type:	float
The total of all of the volume values seen in the data

unit
Type:	unicode
The unit type of the data set
*/