package org.openstack.api.restful.elements

import java.net.URL

/**
 * @author Antonio Murgia
 * @version 10/10/14
 */
case class Link(href : URL, rel : String, contentType : Option[String] = None) {
  require(href != null)
  require(contentType != null)
}