package org.openstack.api.restful.elements

import java.util.Date

import spray.json._
import it.unibo.ing.utils.DateUtils
import spray.json.URLConversion._
import org.openstack.api.restful.MalformedJsonException
/**
 *
 * spray json conversion for all the classes of this package (not subpackages)<br/>
 * the list is:
 *<ul>
 * <li>[[org.openstack.api.restful.elements.Link Link]]</li>
 * <li>[[org.openstack.api.restful.elements.Version Version]]</li>
 * <li>java.util.Date</li>
 *</ul>
 * @author Antonio Murgia
 * @version 11/10/14
 */
object JsonConversions extends DefaultJsonProtocol{

  implicit object DateJsonFormat extends JsonFormat[Date] {
    def write(d: Date) =
      JsString(DateUtils.format(d))
    def read(value: JsValue) = value match {
      case JsString(date) =>
        DateUtils.parse(date)
      case _ => throw new MalformedJsonException
    }
  }

  implicit val linkJsonFormat = jsonFormat3(Link)
  implicit val versionJsonFormat = jsonFormat4(Version)
}
