package org.openstack.api.restful.keystone.v2.elements

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
case class OpenStackTokenMetadata(is_admin : Boolean,
                                  roles : Seq[String])
