//import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.{Operator, SimpleQuery}

import java.util.Date

import myUtils.DateUtils
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Goodies._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue._
import org.scalatest._

/**
 * Created by tmnd on 26/11/14.
 */
class ClientTests extends FlatSpec with Matchers{
  "the connectivity " should "work " in {
    import java.net.URL
    val keystoneURL = new URL("http://137.204.57.150:5000")
    val ceilometerURL = new URL("http://137.204.57.150:8777")
    val tenantName = "ceilometer_project"
    val username = "amurgia"
    val password = "PUs3dAs?"

    val client = org.openstack.clients.ceilometer.v2.CeilometerClient.getInstance(ceilometerURL, keystoneURL, tenantName, username, password)

    val meters = client.listMeters

    val statistics = client.getStatistics(meters.head)

  }
}
