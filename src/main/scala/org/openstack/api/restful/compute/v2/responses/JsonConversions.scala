package org.openstack.api.restful.compute.v2.responses

/**
 * Created by tmnd on 18/10/14.
 */
import org.openstack.api.restful.MalformedJsonException
import spray.json._

object JsonConversions extends DefaultJsonProtocol {
  import org.openstack.api.restful.elements.JsonConversions._
  implicit val RootJsonFormat = jsonFormat(Root,"versions")
}