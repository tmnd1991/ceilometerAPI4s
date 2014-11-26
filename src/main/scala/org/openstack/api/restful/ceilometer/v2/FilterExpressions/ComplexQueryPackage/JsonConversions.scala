package org.openstack.api.restful.ceilometer.v2.FilterExpressions.ComplexQueryPackage

import myUtils.{TypeExtractor, DateUtils}
import org.openstack.api.restful.ceilometer.v2.FilterExpressions._
import org.openstack.api.restful.ceilometer.v2.FilterExpressions.JsonConversions._
import org.openstack.api.restful.MalformedJsonException
import spray.json.{DefaultJsonProtocol, JsObject, JsonWriter, _}

/**
 * Created by tmnd on 19/10/14.
 */
object JsonConversions extends DefaultJsonProtocol{

  implicit object OrderJsonFormat extends JsonFormat[Order]{
    override def read(json: JsValue) = json match{
      case s : JsString => Order.values.getOrElse(s.value, throw new MalformedJsonException)
      case _ => throw new MalformedJsonException
    }
    override def write(obj: Order) = JsString(obj.s)
  }

  implicit object OrderByJsonFormat extends JsonFormat[OrderBy]{
    override def read(json: JsValue) = json match{
      case obj : JsObject =>{
        val kv = obj.fields.headOption.getOrElse(throw new MalformedJsonException)
        val field = kv._1
        kv._2 match{
          case s : JsString => OrderBy(field, Order.values.getOrElse(s.value, throw new MalformedJsonException))
          case _ => throw new MalformedJsonException
        }
      }
      case _ => throw new MalformedJsonException
    }
    override def write(obj: OrderBy) = JsObject(obj.field -> JsString(obj.order.s))
  }

  implicit object FilterJsonFormat extends JsonFormat[Filter] {
    override def write(f : Filter) =  ExpressionJsonFormat.write(f.e)

    override def read(json: JsValue) = {
      val s = json.asInstanceOf[JsString].value
      val x = s.parseJson
      Filter(ExpressionJsonFormat.read(x))
    }
  }

  implicit object ComplexQueryJsonFormat extends JsonFormat[ComplexQuery]{
    override def write(obj: ComplexQuery) =
    {
      (obj.orderBy, obj.limit) match{
        case (None, None) => JsObject("filter" -> JsString(obj.filter.toJson.toString()))
        case (None, Some(_)) =>  JsObject(
                                "filter" -> JsString(obj.filter.toJson.toString()),
                                "limit" -> JsNumber(obj.limit.get)
                              )
        case (Some(_), None) => JsObject(
                                "filter" -> JsString(obj.filter.toJson.toString()),
                                "orderby" -> JsString(
                                    JsArray((for(x <- obj.orderBy.get) yield {
                                      JsObject(x.field -> JsString(x.order.s))
                                    }).toVector).toString())
                             )
        case (Some(_), Some(_)) => JsObject(
                                "filter" -> JsString(obj.filter.toJson.toString()),
                                "limit" -> JsNumber(obj.limit.get),
                                "orderby" -> JsString(
                                    JsArray((for(x <- obj.orderBy.get) yield {
                                      JsObject(x.field -> JsString(x.order.s))
                                    }).toVector).toString()
                                )
                             )
      }
    }

    override def read(json: JsValue) = json match{
      case obj : JsObject =>{
        obj.fields.size match{
          case 1 => ComplexQuery(FilterJsonFormat.read(obj.fields.getOrElse("filter",throw new MalformedJsonException)))
          case 2 => if(obj.fields.contains("limit")){
                      ComplexQuery(FilterJsonFormat.read(obj.fields.getOrElse("filter",throw new MalformedJsonException)),
                      Some(obj.fields("limit").convertTo[Int]))
                    }
                    else {
                      val orderbyJs = obj.fields("orderby").asInstanceOf[JsString].value
                      val orderby = orderbyJs.parseJson.convertTo[Seq[OrderBy]]
                      ComplexQuery(FilterJsonFormat.read(obj.fields.getOrElse("filter",throw new MalformedJsonException)),
                      None, Some(orderby))
                    }
          case 3 => {
            val orderbyJs = obj.fields("orderby").asInstanceOf[JsString].value
            val orderby = orderbyJs.parseJson.convertTo[Seq[OrderBy]]
            ComplexQuery(FilterJsonFormat.read(obj.fields.getOrElse("filter",throw new MalformedJsonException)),
            Some(obj.fields("limit").convertTo[Int]), Some(orderby))
          }
          /*
          case 1 => ComplexQuery((obj.fields.getOrElse("filter",throw new MalformedJsonException)).convertTo[String],None, None)
          case 2 => if(obj.fields.contains("limit")){
            ComplexQuery((obj.fields.getOrElse("filter",throw new MalformedJsonException)).convertTo[String],
              Some(obj.fields("limit").convertTo[Int]),None)
          }
          else {
            val orderby = obj.fields("orderby").convertTo[Seq[OrderBy]]
            ComplexQuery((obj.fields.getOrElse("filter",throw new MalformedJsonException)).convertTo[String],
              None, Some(orderby))
          }
          case 3 => {
            val orderby = obj.fields("orderby").convertTo[Seq[OrderBy]]
            ComplexQuery(FilterJsonFormat.read(obj.fields.getOrElse("filter",throw new MalformedJsonException)),
              Some(obj.fields("limit").convertTo[Int]), Some(orderby))
          }*/
        }
      }
      case _ => throw new MalformedJsonException
    }
  }

  implicit object ExpressionJsonFormat extends JsonFormat[Expression]{
    def write(e : Expression) = {
      e match
      {
        case se : SimpleExpression => SimpleExpressionJsonFormat.write(se)
        case sie: SimpleInExpression => SimpleInExpressionJsonFormat.write(sie)
        case uce: UnaryComplexExpression => UnaryComplexExpressionJsonFormat.write(uce)
        case mce: MultiComplexExpression => MultiComplexExpressionJsonFormat.write(mce)
      }
    }

    override def read(json: JsValue): Expression = json match{
      case obj : JsObject =>{
        obj.fields.keys.headOption match{
          case Some(x : String) =>{
            if(x == "in")
              SimpleInExpressionJsonFormat.read(json)
            else{
              if(SimpleOperator.values.contains(x))
                SimpleExpressionJsonFormat.read(json)
              else
                if(UnaryComplexOperator.values.contains(x))
                  UnaryComplexExpressionJsonFormat.read(json)
                else
                  if(MultiComplexOperator.values.contains(x))
                    MultiComplexExpressionJsonFormat.read(json)
                  else
                    throw new MalformedJsonException
            }
          }
          case _ => throw new MalformedJsonException
        }
      }
      case _ => throw new MalformedJsonException
    }
  }

  implicit object MultiComplexExpressionJsonFormat extends JsonFormat[MultiComplexExpression] {
    def write(mce: MultiComplexExpression) = JsObject(
      mce.o.s -> JsArray(
        mce.expressions.map(_.toJson).toVector
      )
    )
    override def read(json: JsValue) = json match{
      case obj : JsObject =>{
        val op = MultiComplexOperator.values(obj.fields.keys.headOption.getOrElse(throw new MalformedJsonException))
        obj.fields.values.headOption.getOrElse(throw new MalformedJsonException) match{
          case exps : JsArray => {
            val expressions = exps.elements.map(ExpressionJsonFormat.read(_))
            MultiComplexExpression(op,expressions)
          }
          case _ => throw new MalformedJsonException
        }
      }
      case _ => throw new MalformedJsonException
    }
  }

  implicit object SimpleExpressionJsonFormat extends JsonFormat[SimpleExpression]{
    override def read(json: JsValue): SimpleExpression = {
      json match{
        case obj : JsObject =>{
          obj.fields.headOption match{
            case Some(h : (String,JsObject)) =>{
              val op = SimpleOperator.values.getOrElse(h._1,throw new MalformedJsonException)
              val kv = h._2
              kv.fields.headOption match{
                case Some(x : (String,JsValue)) =>{
                  val field = x._1
                  val value = FieldValueJsonFormat.read(x._2)
                  SimpleExpression(op, field, value, value.getType)
                }
                case _ => throw new MalformedJsonException()
              }
            }
            case _ => throw new MalformedJsonException
          }
        }
        case _ => throw new MalformedJsonException
      }
    }

    override def write(obj: SimpleExpression): JsValue = JsObject(
      obj.o.s -> JsObject(obj.fieldName -> FieldValueJsonFormat.write(obj.value))
    )
  }

  implicit object SimpleInExpressionJsonFormat extends JsonFormat[SimpleInExpression]{
    override def read(json: JsValue): SimpleInExpression = {
      json match{
        case obj : JsObject =>{
          obj.fields.headOption match{
            case Some(h : (String,JsObject)) =>{
              val op = SimpleOperator.values.getOrElse(h._1,throw new MalformedJsonException)
              val kv = h._2
              kv.fields.headOption match{
                case Some(x : (String,JsValue)) =>{
                  val field = x._1
                  val value = x._2.convertTo[List[FieldValue]]
                  SimpleInExpression(field,value,value.head.getType)
                }
                case _ => throw new MalformedJsonException()
              }
            }
            case _ => throw new MalformedJsonException
          }
        }
        case _ => throw new MalformedJsonException
      }
    }
    override def write(obj: SimpleInExpression): JsValue = JsObject(
      "in" -> JsObject(obj.fieldName -> JsArray(obj.values.map(FieldValueJsonFormat.write(_)).toVector))
    )
  }

  implicit object UnaryComplexExpressionJsonFormat extends JsonFormat[UnaryComplexExpression] {
    def write(uce: UnaryComplexExpression) = JsObject(
      uce.o.s -> uce.e.toJson
    )

    override def read(json: JsValue): UnaryComplexExpression = json match{
      case obj : JsObject =>{
        val x = obj.fields.keys.headOption.getOrElse(throw new MalformedJsonException)
        val e = ExpressionJsonFormat.read(obj.fields.values.headOption.getOrElse(throw new MalformedJsonException))
        if (UnaryComplexOperator.values.contains(x))
          UnaryComplexExpression(UnaryComplexOperator.values(x),e)
        else
          throw new MalformedJsonException
      }
      case _ => throw new MalformedJsonException
    }
  }

}
