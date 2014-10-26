package org.openstack.api.restful.FilterExpressions.SimpleQueryPackage

import org.openstack.api.restful.FilterExpressions._

/**
 * Created by tmnd on 21/10/14.
 */
object Goodies {
  implicit class FieldName(val s : String) extends AnyVal {
    /*
    def ====(v: Boolean) = SimpleQuery[BooleanField](s, SimpleQueryPackage.eq, v)
    def !!==(v: Boolean) = SimpleQuery[BooleanField](s, le, v)

    def ====(v:Int) = SimpleQuery[IntField](s,SimpleQueryPackage.eq,v)
    def >>>>(v:Int) = SimpleQuery[IntField](s,gt,v)
    def >>==(v:Int) = SimpleQuery[IntField](s,ge,v)
    def <<<<(v:Int) = SimpleQuery[IntField](s,lt,v)
    def <<==(v:Int) = SimpleQuery[IntField](s,le,v)
    def !!==(v:Int) = SimpleQuery[IntField](s,le,v)

    def ====(v:Float) = SimpleQuery[FloatField](s,SimpleQueryPackage.eq,v)
    def >>>>(v:Float) = SimpleQuery[FloatField](s,gt,v)
    def >>==(v:Float) = SimpleQuery[FloatField](s,ge,v)
    def <<<<(v:Float) = SimpleQuery[FloatField](s,lt,v)
    def <<==(v:Float) = SimpleQuery[FloatField](s,le,v)
    def !!==(v:Float) = SimpleQuery[FloatField](s,le,v)

    def ====(v:String) = SimpleQuery[StringField](s,SimpleQueryPackage.eq,v)
    def >>>>(v:String) = SimpleQuery[StringField](s,gt,v)
    def >>==(v:String) = SimpleQuery[StringField](s,ge,v)
    def <<<<(v:String) = SimpleQuery[StringField](s,lt,v)
    def <<==(v:String) = SimpleQuery[StringField](s,le,v)
    def !!==(v:String) = SimpleQuery[StringField](s,le,v)

    def ====(v:java.util.Date) = SimpleQuery[DateField](s,SimpleQueryPackage.eq,v)
    def >>>>(v:java.util.Date) = SimpleQuery[DateField](s,gt,v)
    def >>==(v:java.util.Date) = SimpleQuery[DateField](s,ge,v)
    def <<<<(v:java.util.Date) = SimpleQuery[DateField](s,lt,v)
    def <<==(v:java.util.Date) = SimpleQuery[DateField](s,le,v)
    def !!==(v:java.util.Date) = SimpleQuery[DateField](s,le,v)
    */
  }

}
