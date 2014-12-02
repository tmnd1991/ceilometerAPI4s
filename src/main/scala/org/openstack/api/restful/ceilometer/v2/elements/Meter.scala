package org.openstack.api.restful.ceilometer.v2.elements

/**
 * @author Antonio Murgia
 * @version 22/10/14
 * ceilometer Meter representation
 */
case class Meter(meter_id : String,
                 name : String,
                 project_id : String,
                 resource_id : String,
                 source : String,
                 `type` : MeterType,
                 unit : String,
                 user_id : String) {

}
/*
meter_id
Type:	unicode
The unique identifier for the meter

name
Type:	unicode
The unique name for the meter

project_id
Type:	unicode
The ID of the project or tenant that owns the resource

resource_id
Type:	unicode
The ID of the Resource for which the measurements are taken

source
Type:	unicode
The ID of the source that identifies where the meter comes from

type
Type:	Enum(gauge, delta, cumulative)
The meter type (see Measurements)

unit
Type:	unicode
The unit of measure

user_id
Type:	unicode
The ID of the user who last triggered an update to the resource
*/