/**
 * Created by tmnd on 27/11/14.
 */
import java.io._
import java.net._
import org.openstack.api.restful.keystone.v2.KeystoneTokenProvider
import org.scalatest._

class javaTest extends FlatSpec with Matchers{
  "the connectivity " should "work " in {
    //SETUP

    val token = KeystoneTokenProvider.getInstance(new URL("http://137.204.57.150:5000"),"ceilometer_project","amurgia","PUs3dAs?").token
    val url = new URL("http://137.204.57.150:8777/v2/meters/")
    val body = "{\"q\": [{\"field\": \"resource\", \"op\": \"eq\", \"value\": \"gdfsf\"}]}"
    val bodyLenght = body.length.toString
    val host = (url.getHost+":"+url.getPort)
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")
    connection.setRequestProperty("Content-Type", "application/json")
    connection.setRequestProperty("Content-Length", bodyLenght)
    connection.setRequestProperty("Accept", "*/*")
    connection.setRequestProperty("Host", host)
    connection.setRequestProperty("X-Auth-Token", token)
    connection.setDoInput(true)
    connection.setDoOutput(true)
    //SEND REQUEST
    val wr = new DataOutputStream(connection.getOutputStream)
    wr.write(body.getBytes)
    wr.flush
    wr.close
    connection.getResponseCode should be (200)

    //GET RESPONSE
    /*
    //Get Response
  InputStream is = connection.getInputStream();
  BufferedReader rd = new BufferedReader(new InputStreamReader(is));
  String line;
  StringBuffer response = new StringBuffer();
  while((line = rd.readLine()) != null) {
    response.append(line);
    response.append('\r');
  }
  rd.close();
  return response.toString();
  connection.disconnect()
     */
  }
}