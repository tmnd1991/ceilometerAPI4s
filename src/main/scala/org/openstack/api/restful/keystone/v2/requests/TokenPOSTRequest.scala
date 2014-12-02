package org.openstack.api.restful.keystone.v2.requests

import org.openstack.api.restful.keystone.v2.elements.{OpenStackCredential}

/**
 * @author Antonio Murgia
 * @version 09/11/14.
 */
case class TokenPOSTRequest(auth : OpenStackCredential){
  def relativeURL = "/v2.0/tokens"
}
