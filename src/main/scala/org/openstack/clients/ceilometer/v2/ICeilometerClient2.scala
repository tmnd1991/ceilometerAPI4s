package org.openstack.clients.ceilometer.v2

import java.util.Date

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.elements._
import org.slf4j.Logger

import scala.concurrent.Future

/**
 * Created by tmnd91 on 27/04/15.
 */
trait ICeilometerClient2 {
  val logger: Logger
  /**
   *
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if an error occurs return None
   */
  def listMeters(q: Seq[Query]): Future[Seq[Meter]]

  /**
   * @return a collection of resources filtered by the query params
   */
  def listResources(q: Seq[Query]) : Future[Seq[Resource]]

  /**
   *
   * @param meterName the name of the meter
   * @return a collection of statistics, if an error occurs return None
   */
  def getStatistics(meterName : String) : Future[Seq[Statistics]]

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Statistics is relative to.
   * @return Some statistics about that meter from a Date to another or None if an error occurs
   */
  def getStatistics(meterName : String, from : Date, to : Date) : Future[Seq[Statistics]]

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  def getSamplesOfMeter(meterName : String, from : Date, to : Date) : Future[Seq[OldSample]]

  /**
   * @param from
   * @param to
   * @param resource_id the resource_id of the meter the Samples are relative to.
   * @return Some Samples about that resource from a Date to another or None if an error occurs
   */
  def getSamplesOfResource(resource_id : String, from : Date, to :Date) : Future[Seq[Sample]]

  /**
   * @param queries to be issued
   * @param resource_id the id of the Resource the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  def getSamplesOfResource(resource_id : String, queries : Query*) : Future[Seq[Sample]]

  /**
   * called to shutdown the httpClient
   */
  def shutdown() = logger.info("Shutting down")
}
