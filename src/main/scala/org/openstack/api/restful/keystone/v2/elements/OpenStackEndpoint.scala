package org.openstack.api.restful.keystone.v2.elements

import java.net.URL

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
case class OpenStackEndpoint(adminURL : URL,
                             region : String,
                             internalURL : URL,
                             id : String,
                             publicURL : URL)