package org.openstack.api.restful.ceilometer.v2.elements

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Operator

/**
 * @author Antonio Murgia
 * @version 22/10/14
 * ceilometer AlarmThresholdRule representation
 */
case class AlarmThresholdRule(comparison_operator : Operator,
                              evaluation_periods : Int,
                              exclude_outliers : Boolean,
                              meter_name : String,
                              period : Int,
                              query : Seq[Query],
                              statistic : StatisticType,
                              threshold : Float)

/*
comparison_operator
Type:	Enum(lt, le, eq, ne, ge, gt)
The comparison against the alarm threshold

evaluation_periods
Type:	integer
The number of historical periods to evaluate the threshold

exclude_outliers
Type:	bool
Whether datapoints with anomalously low sample counts are excluded

meter_name
Type:	unicode
The name of the meter

period
Type:	integer
The time range in seconds over which query

query
Type:	list(Query)
The query to find the data for computing statistics. Ownership settings are automatically included based on the Alarm owner.

statistic
Type:	Enum(max, min, avg, sum, count)
The statistic to compare to the threshold

threshold
Type:	float
The threshold of the alarm
*/