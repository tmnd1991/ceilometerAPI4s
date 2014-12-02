package org.openstack.api.restful.keystone.v2.elements

import java.net.URL

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
case class OpenStackService(endpoints : Seq[OpenStackEndpoint],
                       endpoints_links : Seq[URL],
                       `type` : OpenStackEndpointType,
                       name : String)
