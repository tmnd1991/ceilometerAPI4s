package myUtils

import org.openstack.api.restful.MalformedJsonException
import spray.json._
import java.net.URL
/**
 * Created by tmnd on 09/11/14.
 */
object URLJsonConversion extends DefaultJsonProtocol{
  implicit object UrlJsonFormat extends JsonFormat[URL]{
    override def write(obj: URL) = JsString(obj.toString)

    override def read(json: JsValue) = json match{
      case s: JsString => URLUtils(s.value).getOrElse(throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }
  }
}
