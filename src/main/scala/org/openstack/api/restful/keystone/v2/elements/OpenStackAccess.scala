package org.openstack.api.restful.keystone.v2.elements

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
case class OpenStackAccess(token : OpenStackToken,
                           serviceCatalog : Seq[OpenStackService],
                           user : OpenStackUser,
                           metadata : OpenStackTokenMetadata)