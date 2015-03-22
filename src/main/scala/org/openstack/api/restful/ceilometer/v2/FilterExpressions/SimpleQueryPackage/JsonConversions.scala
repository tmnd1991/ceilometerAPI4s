package org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage


import org.openstack.api.restful.ceilometer.v2.FilterExpressions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
import org.openstack.api.restful.MalformedJsonException
import java.util.Date
import spray.json._

/**
 * spray json conversion for all the classes of this package (not subpackages)<br/>
 * the list is:
 *<ul>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.Operator Operator]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.SimpleQuery SimpleQuery]]</li>
 *</ul>
 * @author Antonio Murgia
 * @version 20/10/14
 */
object JsonConversions extends DefaultJsonProtocol{

  implicit object OperatorJsonFormat extends JsonFormat[Operator] {
    override def write(obj: Operator) = JsString(obj.s)

    override def read(value: JsValue) = value match {
      case x: JsString => Operator.values(x.value)
      case _ => throw new MalformedJsonException()
    }
  }

  implicit object SimpleQueryJsonFormat extends JsonFormat[SimpleQuery] {
    override def read(json: JsValue): SimpleQuery = {
      json match {
        case obj: JsObject => try {
          val value: FieldValue = FieldValueJsonFormat.read(obj.fields("type"))
          val tipo = value.getType
          val op = obj.fields("op").convertTo[Operator]
          val field = obj.fields("field").convertTo[String]
          SimpleQuery(field, op, value, Some(tipo))
        }
        catch{
          case t : Throwable => throw new MalformedJsonException
        }
        case _ => throw new MalformedJsonException
      }
    }

    override def write(obj: SimpleQuery) =
      if (obj.`type` != None)
        JsObject(
          "field" -> JsString(obj.field),
          "op" -> OperatorJsonFormat.write(obj.op),
          "value" -> FieldValueJsonFormat.write(obj.value),
          "type" -> JsString(obj.`type`.get))
      else
        JsObject(
          "field" -> JsString(obj.field),
          "op" -> OperatorJsonFormat.write(obj.op),
          "value" -> FieldValueJsonFormat.write(obj.value))
  }


}
