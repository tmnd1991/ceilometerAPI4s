package org.openstack.api.restful.elements

import java.net.URL
import java.util.Date

import spray.json._
import myUtils.DateUtils
import myUtils.URLJsonConversion._
import org.openstack.api.restful.MalformedJsonException
/**
 * Created by tmnd on 11/10/14.
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
