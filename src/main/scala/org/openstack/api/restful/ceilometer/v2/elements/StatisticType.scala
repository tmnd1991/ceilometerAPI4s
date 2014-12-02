package org.openstack.api.restful.ceilometer.v2.elements

/**
 * @author Antonio Murgia
 * @version 22/10/14
 * ceilometer StatisticType representation
 */
class StatisticType(val s : String) {

}
object StatisticType{
  val values = Map(MAX.s -> MAX,
                   MIN.s -> MIN,
                   AVG.s -> AVG,
                   SUM.s -> SUM,
                   COUNT.s -> COUNT)
  object MAX extends StatisticType("max")
  object MIN extends StatisticType("min")
  object AVG extends StatisticType("avg")
  object SUM extends StatisticType("sum")
  object COUNT extends StatisticType("count")
}
