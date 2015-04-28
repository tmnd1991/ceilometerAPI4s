package org.openstack.clients.ceilometer.v2

import java.net.URL


/**
 * Implementation of the flyweight pattern to get a CeilometerClient
 */
object CeilometerClient{
  private val instances : scala.collection.mutable.Map[Int,ICeilometerClient] = scala.collection.mutable.Map()
  def getInstance(ceilometerUrl : URL, keystoneUrl : URL, tenantName : String,  username : String, password : String, connectTimeout: Int, readTimeout: Int) = {
    this.synchronized{
      val hashcode = getHashCode(ceilometerUrl,keystoneUrl,tenantName,username,password)
      if (!instances.contains(hashCode))
        instances(hashCode) = new JettyCeilometerClient(ceilometerUrl, keystoneUrl, tenantName,  username, password, connectTimeout, readTimeout)
        //instances(hashCode) = new DispatchCeilometerClient(ceilometerUrl, keystoneUrl, tenantName,  username, password, connectTimeout, readTimeout)
    }
    instances(hashCode)
  }
  private def getHashCode(vals : Any*) = vals.mkString("").hashCode
}