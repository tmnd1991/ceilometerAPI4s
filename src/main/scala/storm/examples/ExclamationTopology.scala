package storm.scala.examples


import java.util.concurrent.TimeoutException

import org.openstack.api.restful.elements.JsonConversions
import storm.scala.StormConfig
import storm.scala.dsl.StormBolt
import storm.scala.dsl.StormSpout

class ExclamationBolt extends StormBolt(outputFields = List("status","updated")) {
  import backtype.storm.tuple.Tuple
  import org.openstack.api.restful.compute.v2.responses.Root
  def execute(t: Tuple) {
    val x : Root = t.getValue(0).asInstanceOf[Root]
    t.emit(x.versions(0).status + "!!",x.versions(0).updated)
    t.ack
  }
}

class computeJsonRootSpout extends StormSpout(outputFields = List("RootObject")){
  import dispatch._,Defaults._
  import spray.json._
  import org.openstack.api.restful.compute.v2.responses.Root
  import org.openstack.api.restful.compute.v2.responses.JsonConversions._
  override def nextTuple() = {
    Thread.sleep(1000)
    val host = "http://192.168.1.11:8774/"
    val svc = url(host)
    val rootString = Http.configure(_.setAllowPoolingConnection(true).setConnectionTimeoutInMs(100))(svc OK as.String)
    //val rootString =
    rootString.onFailure{
      case e: TimeoutException =>println("Timeout")
    }
    rootString.onSuccess {
      case s: String => {
        try{
          val r : Root = s.parseJson.convertTo[Root]
          emit(r)
        }
        catch{
          case _ : Throwable => println("Cannot convert to Root")
        }
      }
    }
  }
}

object ExclamationTopology {
  import backtype.storm.topology.TopologyBuilder
  import backtype.storm.LocalCluster

  def main(args: Array[String]) = {
    val builder = new TopologyBuilder()
    builder.setSpout("roots", new computeJsonRootSpout, 10)
    builder.setBolt("extract", new ExclamationBolt, 3)
      .shuffleGrouping("roots")
    val conf = new StormConfig(debug = true)
    val cluster = new LocalCluster()
    cluster.submitTopology("test", conf, builder.createTopology())
    Thread.sleep(10000)
    cluster.killTopology("test")
    cluster.shutdown()
  }
}
