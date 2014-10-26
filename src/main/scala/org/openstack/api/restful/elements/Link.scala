package org.openstack.api.restful.elements

import java.net.URL

import spray.json.DefaultJsonProtocol


/**
 * Created by tmnd on 10/10/14.
 */
case class Link(href : URL, rel : String, contentType : Option[String] = None) {
  require(href != null)
  require(contentType != null)
}