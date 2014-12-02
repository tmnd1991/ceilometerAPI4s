package org.openstack.api.restful.ceilometer.v2.elements

/**
 * @author Antonio Murgia
 * @version 22/10/14
 * ceilometer Sample representation
 */
import java.util.Date

case class Sample(id : String,
                  metadata : Map[String,String],
                  meter : String,
                  project_id : String,
                  recorded_at : java.sql.Timestamp,
                  resource_id: String,
                  source : String,
                  timestamp : java.sql.Timestamp,
                  `type` : MeterType,
                  unit : String,
                  user_id : String,
                  volume : Float)
/*
id
Type:	unicode
The unique identifier for the sample.

metadata
Type:	dict(unicode: unicode)
Arbitrary metadata associated with the sample.

meter
Type:	unicode
The meter name this sample is for.

project_id
Type:	unicode
The project this sample was taken for.

recorded_at
Type:	datetime
When the sample has been recorded.

resource_id
Type:	unicode
The Resource this sample was taken for.

source
Type:	unicode
The source that identifies where the sample comes from.

timestamp
Type:	datetime
When the sample has been generated.

type
Type:	Enum(gauge, delta, cumulative)
The meter type (see Measurements)

unit
Type:	unicode
The unit of measure.

user_id
Type:	unicode
The user this sample was taken for.

volume
Type:	float
The metered value.

 */
