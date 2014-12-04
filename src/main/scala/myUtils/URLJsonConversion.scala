package myUtils

import org.openstack.api.restful.MalformedJsonException
import spray.json._
import java.net.URL
/**
 * URL spray Json conversions
 * @author Antonio Murgia
 * @version 04/12/2014
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
