package org.openstack.api.restful.ceilometer.v2.requests

import org.openstack.api.restful.ceilometer.v2.elements.Alarm

/**
 * @author Antonio Murgia
 * @version 21/10/14
 */
case class AlarmPOSTRequest(data : Alarm)