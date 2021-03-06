package org.openstack.api.restful.ceilometer.v2.FilterExpressions

import it.unibo.ing.utils.DateUtils
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.ComplexQuery
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage.JsonConversions.ComplexQueryJsonFormat
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.JsonConversions.SimpleQueryJsonFormat
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.SimpleQueryPackage.SimpleQuery
import org.openstack.api.restful.MalformedJsonException
import spray.json._

/**
 * spray json conversion for all the classes of this package (not subpackages)<br/>
 * the list is:
 *<ul>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query Query]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.FieldValue FieldValue]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.BooleanField BooleanField]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.StringField StringField]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.IntField IntField]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.FloatField FloatField]]</li>
 * <li>[[org.openstack.api.restful.ceilometer.v2.FilterExpressions.DateField DateField]]</li>
 *</ul>
 * @author Antonio Murgia
 * @version 21/10/14
 */
object JsonConversions extends DefaultJsonProtocol{

  implicit object QueryJsonFormat extends JsonFormat[Query]{
    override def read(json: JsValue) = json match{
      case obj : JsObject =>{
        if(obj.fields.contains("field"))
          SimpleQueryJsonFormat.read(json)
        else
          ComplexQueryJsonFormat.read(json)
      }
      case _ => throw new MalformedJsonException
    }

    override def write(obj: Query): JsValue = obj match{
      case s : SimpleQuery => SimpleQueryJsonFormat.write(s)
      case c : ComplexQuery => ComplexQueryJsonFormat.write(c)
    }
  }

  implicit object FieldValueJsonFormat extends JsonFormat[FieldValue]{
    override def write(fv : FieldValue) = {
      fv match{
        case b : BooleanField => BooleanFieldJsonFormat.write(b)
        case i : IntField => IntFieldJsonFormat.write(i)
        case f : FloatField => FloatFieldJsonFormat.write(f)
        case s : StringField => StringFieldJsonFormat.write(s)
        case d : DateField => DateFieldJsonFormat.write(d)
      }
    }
    override def read(value : JsValue) = value match{
      case s : JsString => {
        val maybeDate = DateUtils.parseOption(s.value)
        maybeDate match{
          case Some(d : java.util.Date) => DateFieldJsonFormat.read(s)
          case _ => StringFieldJsonFormat.read(s)
        }
      }
      case n : JsNumber => if (n.value.isValidInt)
                              IntFieldJsonFormat.read(n)
                           else
                              FloatFieldJsonFormat.read(n)
      case b : JsBoolean => BooleanFieldJsonFormat.read(b)
      case _ => throw new MalformedJsonException
    }
  }

  implicit object BooleanFieldJsonFormat extends JsonFormat[BooleanField] {
    override def write(b : BooleanField) = JsBoolean(b.value)
    override def read(value : JsValue) = value match{
      case b : JsBoolean => BooleanField(b.value)
      case _ => throw new MalformedJsonException
    }
  }

  implicit object IntFieldJsonFormat extends JsonFormat[IntField] {
    override def write(i : IntField) = JsNumber(i.value)
    override def read(json: JsValue) = json match{
      case n : JsNumber => IntField(n.value.intValue)
      case _ => throw new MalformedJsonException
    }
  }
  implicit object FloatFieldJsonFormat extends JsonFormat[FloatField] {
    override def write(f : FloatField) = JsNumber(f.value)
    override def read(json: JsValue) = json match{
      case n : JsNumber => FloatField(n.value.floatValue)
      case _ => throw new MalformedJsonException
    }
  }
  implicit object StringFieldJsonFormat extends JsonFormat[StringField] {
    override def write(s : StringField) = JsString(s.value)
    override def read(json: JsValue) = json match{
      case s : JsString => StringField(s.value)
      case _ => throw new MalformedJsonException
    }
  }
  implicit object DateFieldJsonFormat extends JsonFormat[DateField] {
    override def write(d : DateField) = JsString(DateUtils.format(d.value))
    override def read(json : JsValue) = json match{
      case d : JsString => DateField(DateUtils.parse(d.value))
      case _ => throw new MalformedJsonException
    }
  }
}
