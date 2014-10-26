package org.openstack.api.restful.ceilometer.v2.elements

/**
 * Created by tmnd on 21/10/14.
 */

import java.sql.Timestamp
import java.util.Date

case class OldSample(counter_name : String,
                      counter_type : MeterType,
                      counter_unit : String,
                      counter_volume : Float,
                      message_id : String,
                      project_id : String,
                      recorded_at : Timestamp,
                      resource_id : String,
                      resource_metadata : Map[String, String],
                      source : String,
                      timestamp : Timestamp,
                      user_id : String
                     )
/*

counter_name
Type:	unicode
The name of the meter

counter_type
Type:	unicode
The type of the meter (see Measurements)

counter_unit
Type:	unicode
The unit of measure for the value in counter_volume

counter_volume
Type:	float
The actual measured value

message_id
Type:	unicode
A unique identifier for the sample

project_id
Type:	unicode
The ID of the project or tenant that owns the resource

recorded_at
Type:	datetime
When the sample has been recorded.

resource_id
Type:	unicode
The ID of the Resource for which the measurements are taken

resource_metadata
Type:	dict(unicode: unicode)
Arbitrary metadata associated with the resource

source
Type:	unicode
The ID of the source that identifies where the sample comes from

timestamp
Type:	datetime
UTC date and time when the measurement was made

user_id
Type:	unicode
The ID of the user who last triggered an update to the resource

*/