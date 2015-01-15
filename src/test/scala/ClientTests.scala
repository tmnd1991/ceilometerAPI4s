//import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.{Operator, SimpleQuery}

import java.util.Date

import it.unibo.ing.utils._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue._
import org.openstack.api.restful.ceilometer.v2.elements.{Resource, Meter}
import org.scalatest._

import scala.collection.IterableLike
import scala.collection.generic.CanBuildFrom

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

  "there " should " be some statistics about meters in the last 1 hour" in {
    val stats = client.tryGetStatistics(meters.get.head.name, new Date(new Date().getTime - 3600000), new Date())
    stats should not be None
    stats.get.isEmpty should be (false)
  }

  "there " should " be some resources " in {
    resources should not be None
    resources.get.isEmpty should be (false)
    println(s"there are ${resources.get.size} resources")
  }

  "there " should " be some samples about resources in the last 1 hour" in {
    val start = new Date((new Date().getTime - 3600000))
    val end = new Date(start.getTime + 3600000)
    println("start " + start)
    println("end " + end)
    val samples1 = client.tryGetSamplesOfResource(resources.get.head.resource_id, start, end)
    val samples2 = client.tryGetSamplesOfMeter(meters.get.head.name, start, end)
    samples1 should not be None
    samples1.get.isEmpty should be (false)
    samples2 should not be None
    samples2.get.isEmpty should be (false)
    for (s <- samples1.get.distinctBy(_.meter))
      println(s.meter)
    /*
    println(s"there are ${samples1.get.size} samples")
    println(s"there are ${samples2.get.size} samples")
    println("avaiable resources :")
    println("-----------------------------------")
    for(r <- resources.get)
      println(r.resource_id + " " + r.links)
    println("-----------------------------------")
    println("avaiable meters :")
    println("-----------------------------------")
    for(r <- meters.get.distinctBy(_.name))
      println(r.name + " " + r.`type`)
    println("-----------------------------------")
    */
  }

  implicit class RichCollection[A, Repr](xs: IterableLike[A, Repr]){
    def distinctBy[B, That](f: A => B)(implicit cbf: CanBuildFrom[Repr, A, That]) = {
      val builder = cbf(xs.repr)
      val i = xs.iterator
      var set = Set[B]()
      while (i.hasNext) {
        val o = i.next
        val b = f(o)
        if (!set(b)) {
          set += b
          builder += o
        }
      }
      builder.result
    }
  }
}
