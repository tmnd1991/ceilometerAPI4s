package org.openstack.api.restful.keystone.v2.elements

import java.sql.Timestamp
import java.util.Date

import spray.json._
import DefaultJsonProtocol._
import myUtils.URLJsonConversion._
import myUtils.{TimestampUtils, DateUtils}
import org.openstack.api.restful.MalformedJsonException
import org.openstack.api.restful.keystone.v2.elements._


/**
 * Created by tmnd on 09/11/14.
 */
object JsonConversions extends DefaultJsonProtocol{
  //REQUEST
  implicit val PasswordCredentialJsonFormat = jsonFormat2(PasswordCredential)
  implicit val OpenstackCredentialJsonFormat = jsonFormat2(OpenStackCredential)
  //implicit val AuthorizationJsonFormat = jsonFormat1(Authorization)

  //RESPONSE
  implicit object OpenStackTokenMetadataJsonFormat extends JsonFormat[OpenStackTokenMetadata]{
    override def write(obj: OpenStackTokenMetadata) =
      JsObject("is_admin" -> {if(obj.is_admin) JsNumber(1) else JsNumber(0)},
               "roles" -> obj.roles.toJson)
    override def read(json: JsValue) = json match{
      case obj : JsObject =>{
        if (obj.fields.contains("is_admin") && obj.fields.contains("roles")){
          OpenStackTokenMetadata(obj.fields("is_admin") != 0,
                                 obj.fields("roles").convertTo[Seq[String]])
        }
        else throw new MalformedJsonException
      }
      case _ => throw new MalformedJsonException
    }
  }

  implicit val OpenStackRoleJsonFormat = jsonFormat1(OpenStackRole)

  implicit val OpenStackUserJsonFormat = jsonFormat5(OpenStackUser)

  implicit val OpenStackEndpointJsonFormat = jsonFormat5(OpenStackEndpoint)

  implicit object OpenStackEndpointTypeJsonFormat extends JsonFormat[OpenStackEndpointType] {
    override def write(obj: OpenStackEndpointType) = JsString(obj.value)
    override def read(json: JsValue) = json match{
      case s : JsString => OpenStackEndpointType.values.getOrElse(s.value,throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }
  }

  implicit val OpenStackServiceJsonFormat = jsonFormat4(OpenStackService)

  implicit object TimestampJsonFormat extends JsonFormat[java.sql.Timestamp] {
    override def read(json: JsValue) = json match{
      case s : JsString => TimestampUtils.parseOption(s.value).getOrElse(throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }
    override def write(obj: Timestamp) =  JsString(myUtils.TimestampUtils.format(obj))
  }

  implicit object DateJsonFormat extends JsonFormat[java.util.Date]{
    override def write(obj: Date) = {
      JsString(DateUtils.formatOption(obj).getOrElse(throw new MalformedJsonException))
    }
    override def read(json: JsValue) = json match{
      case s : JsString => DateUtils.parseOption(s.value).getOrElse(throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }
  }

  implicit val OpenStackTenantJsonFormat = jsonFormat4(OpenStackTenant)

  implicit val OpenStackTokenJsonFormat = jsonFormat4(OpenStackToken)

  implicit val OpenStackAccessJsonFormat = jsonFormat4(OpenStackAccess)
}
