package org.openstack.api.restful.keystone.v2

import java.net.URL

/**
 * Interface of a tokenProvider, an object (better to be managed as a Flyweight)
 * known implementations are KeystoneProvider
 * @author Antonio Murgia
 * @version 09/11/14
 */
trait TokenProvider extends Serializable{
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
