package org.openstack.api.restful.elements

import java.net.URL
import java.util.Date

import myUtils.DateUtils
import org.openstack.api.restful.MalformedJsonException
import spray.json._
/**
 * Created by tmnd on 11/10/14.
 */
object JsonConversions extends DefaultJsonProtocol{

    implicit object URLJsonFormat extends JsonFormat[URL] {
      def write(u: URL) =
        JsString(u.toString)

      def read(value: JsValue) = value match {
        case JsString(url) =>
          new URL(url)
        case _ => throw new MalformedJsonException
      }
    }

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
