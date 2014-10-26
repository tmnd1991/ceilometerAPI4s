package org.openstack.api.restful.ceilometer.v2.elements

/**
 * Created by tmnd on 21/10/14.
 */
class StatisticType(val s : String) {

}
object StatisticType{
  val values = Map("max" -> MAX,
                    "min" -> MIN,
                    "avg" -> AVG,
                    "sum" -> SUM,
                    "count" -> COUNT)
  object MAX extends StatisticType("max")
  object MIN extends StatisticType("min")
  object AVG extends StatisticType("avg")
  object SUM extends StatisticType("sum")
  object COUNT extends StatisticType("count")
}
