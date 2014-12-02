package org.openstack.api.restful.keystone.v2.elements

/**
 * @author Antonio Murgia
 * @version 09/11/14
 */
abstract class OpenStackEndpointType(val value : String) {

}
object OpenStackEndpointType {
  val values = Map(COMPUTE.value -> COMPUTE,
                   S3.value -> S3,
                   IMAGE.value -> IMAGE,
                   NETWORK.value -> NETWORK,
                   VOLUMEV2.value -> VOLUMEV2,
                   COMPUTEV3.value -> COMPUTEV3,
                   METERING.value -> METERING,
                   CLOUDFORMATION.value -> CLOUDFORMATION,
                   VOLUME.value -> VOLUME,
                   EC2.value -> EC2,
                   ORCHESTRATION.value -> ORCHESTRATION,
                   IDENTITY.value -> IDENTITY)
  object COMPUTE extends OpenStackEndpointType("compute")
  object S3 extends OpenStackEndpointType("s3")
  object IMAGE extends OpenStackEndpointType("image")
  object NETWORK extends OpenStackEndpointType("network")
  object VOLUMEV2 extends OpenStackEndpointType("volumev2")
  object COMPUTEV3 extends OpenStackEndpointType("computev3")
  object METERING extends OpenStackEndpointType("metering")
  object CLOUDFORMATION extends OpenStackEndpointType("cloudformation")
  object VOLUME extends OpenStackEndpointType("volume")
  object EC2 extends OpenStackEndpointType("ec2")
  object ORCHESTRATION extends OpenStackEndpointType("orchestration")
  object IDENTITY extends OpenStackEndpointType("identity")
}