package org.openstack.api.restful.keystone.v2

import java.net.URL

/**
 * Interface of a tokenProvider, an object (better to be managed as a Flyweight)
 * known implementations are KeystoneProvider
 * @author Antonio Murgia
 * @version 09/11/14
 */
trait TokenProvider extends Serializable{
  /**
   * @return the Token in the form of a String
   */
  def token : String

  /**
   * @return Some Token or None if an error occurs
   */
  def tokenOption : Option[String] = {
    try{
      Some(token)
    }
    catch{
      case t : Throwable => {
        None
      }
    }
  }
}
