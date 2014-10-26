package org.openstack.api.restful.FilterExpressions

/**
 * Created by tmnd on 19/10/14.
 */
abstract class FieldValue(val getType : String){
  val value : Any
  override def toString = value.toString
}
object FieldValue{
  import scala.language.implicitConversions
  def apply(b : Boolean) = new BooleanField(b)
  def apply(i : Int) = new IntField(i)
  def apply(f : Float) = new FloatField(f)
  def apply(s : String) = new StringField(s)
  def apply(d : java.util.Date) = new DateField(d)
  implicit def b2bf(b : Boolean) : BooleanField = new BooleanField(b)
  implicit def i2if(i : Int) : IntField = new IntField(i)
  implicit def f2ff(f : Float) : FloatField = new FloatField(f)
  implicit def s2sf(s : String) : StringField = new StringField(s)
  implicit def d2df(d : java.util.Date) : DateField = new DateField(d)
}
case class BooleanField(value : Boolean) extends FieldValue("bool")
case class IntField(value : Int) extends FieldValue("int")
case class FloatField(value : Float) extends FieldValue("float")
case class StringField(value : String) extends FieldValue("string")
case class DateField(value : java.util.Date) extends FieldValue("date")