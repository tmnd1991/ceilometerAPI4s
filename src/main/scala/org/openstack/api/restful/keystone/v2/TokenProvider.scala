package org.openstack.api.restful.keystone.v2

import java.net.URL

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
abstract class TokenProvider(val host : URL, val tenantName : String,  val username : String, val password : String) extends Serializable{
  def token : String
  def tokenOption : Option[String] = {
    try{
      Some(token)
    }
    catch{
      case _ : Throwable => None
    }
  }
}
