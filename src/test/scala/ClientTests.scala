//import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.{Operator, SimpleQuery}

import java.util.Date

import it.unibo.ing.utils._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue._
import org.openstack.api.restful.ceilometer.v2.elements.{Resource, Meter}
import org.openstack.clients.ceilometer.v2.ICeilometerClient2
import org.scalatest._

import scala.collection.IterableLike
import scala.collection.generic.CanBuildFrom
import scala.concurrent.Await
import scala.concurrent.duration._
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

  lazy val client: ICeilometerClient2 = org.openstack.clients.ceilometer.v2.CeilometerClient.getInstance(ceilometerURL, keystoneURL, tenantName, username, password,30000, 360000)
  lazy val meters = Await.result(client.listMeters(Seq()),3000000.millis)
  lazy val resources = Await.result(client.listResources(Seq()),300000.millis)
  "there " should " be some meters " in {
    meters.isEmpty should be (false)
    println(s"there are ${meters.size} meters")
  }

  "there " should " be some statistics about meters in the last 1 hour" in {
    val theMeter = meters.head.name
    val startDate = new Date(new Date().getTime - 3600000)
    val endDate = new Date()
    val stats = Await.result(client.getStatistics(theMeter, startDate, endDate),3000000.millis)
    stats.isEmpty should be (false)
  }

  "there " should " be some resources " in {
    resources should not be None
    resources.isEmpty should be (false)
    println(s"there are ${resources.size} resources")
  }

  "there " should " be some samples about resources in the last 1 hour" in {
    val start = new Date((new Date().getTime - 3600000))
    val end = new Date(start.getTime + 3600000)
    println("start " + start)
    println("end " + end)
    val samples1 = Await.result(client.getSamplesOfResource(resources.head.resource_id, start, end),300000.millis)
    val samples2 = Await.result(client.getSamplesOfMeter(meters.head.name, start, end),300000.millis)
    samples1 should not be None
    samples1.isEmpty should be (false)
    samples2 should not be None
    samples2.isEmpty should be (false)
    for (s <- samples1.distinctBy(_.meter))
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
