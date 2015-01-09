package spray.json

import java.net.URL

import org.openstack.api.restful.MalformedJsonException

/**
 * Created by tmnd91 on 09/01/15.
 */
object URLConversion extends DefaultJsonProtocol{
  implicit object urljsonformat extends RootJsonFormat[java.net.URL]{
    override def read(json: JsValue): URL = json match{
      case jsstring : JsString => new URL(jsstring.value)
      case _ => throw new MalformedJsonException
    }
    override def write(obj: URL): JsValue = new JsString(obj.toString)
  }

}
