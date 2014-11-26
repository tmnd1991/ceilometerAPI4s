import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.{Operator, SimpleQuery}
import org.scalatest.{Matchers, FlatSpec}

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
    val meter = client.listMeters.head
    client.listMeters(List(
      SimpleQuery.apply("project",Operator.eq,meter.project_id)
    )).foreach(println)
  }

}
