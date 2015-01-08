//import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.{Operator, SimpleQuery}

import java.util.Date

import myUtils.DateUtils
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue._
import org.openstack.api.restful.ceilometer.v2.elements.{Resource, Meter}
import org.scalatest._

/**
 * Created by tmnd on 26/11/14.
 */
import java.net.URL
class ClientTests extends FlatSpec with Matchers{
  val keystoneURL = new URL("http://137.204.57.150:5000")
  val ceilometerURL = new URL("http://137.204.57.150:8777")
  val tenantName = "ceilometer_project"
  val username = "amurgia"
  val password = "PUs3dAs?"

  lazy val client = org.openstack.clients.ceilometer.v2.CeilometerClient.getInstance(ceilometerURL, keystoneURL, tenantName, username, password,30000, 360000)
  lazy val meters = client.tryListAllMeters
  lazy val resources = client.tryListAllResources
  "there " should " be some meters " in {
    meters should not be None
    meters.get.isEmpty should be (false)
    println(s"there are ${meters.get.size} meters")
  }

  "there " should " be some statistics about meters in the last 10 hours" in {
    val stats = client.tryGetStatistics(meters.get.head.name, new Date(new Date().getTime - 36000000), new Date())
    stats should not be None
    stats.get.isEmpty should be (false)
  }

  "there " should " be some resources " in {
    resources should not be None
    resources.get.isEmpty should be (false)
    println(s"there are ${resources.get.size} resources")
  }

  "there " should " be some samples about resources in the last 10 hours" in {
    val samples = client.tryGetSamplesOfResource(resources.get.head.resource_id, new Date(new Date().getTime - 36000000), new Date())
    samples should not be None
    samples.get.isEmpty should be (false)
    println(s"there are ${samples.get.size} samples")
  }
}
