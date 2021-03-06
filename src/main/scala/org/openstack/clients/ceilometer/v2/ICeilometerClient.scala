package org.openstack.clients.ceilometer.v2

import java.util.Date

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.elements._
import org.slf4j.Logger

import scala.util.{Failure, Success, Try}

/**
 * Created by tmnd91 on 27/04/15.
 */
trait ICeilometerClient {
  val logger: Logger

  /**
   *
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if an error occurs return None
   */
  def listMeters(q: Seq[Query]): Seq[Meter]
  def tryListMeters(q: Seq[Query]): Try[Seq[Meter]] = try(Success(listMeters(q))) catch{case e: Throwable=> Failure(e)}
  /**
   * @return a collection of resources filtered by the query params
   */
  def listResources(q: Seq[Query]) : Seq[Resource]
  def tryListResources(q: Seq[Query]) : Try[Seq[Resource]] = try(Success(listResources(q))) catch{case e: Throwable=> Failure(e)}
  /**
   *
   * @param meterName the name of the meter
   * @return a collection of statistics, if an error occurs return None
   */
  def getStatistics(meterName : String) : Seq[Statistics]
  def tryGetStatistics(meterName : String) : Try[Seq[Statistics]] = try(Success(getStatistics(meterName))) catch{case e: Throwable=> Failure(e)}
  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Statistics is relative to.
   * @return Some statistics about that meter from a Date to another or None if an error occurs
   */
  def getStatistics(meterName : String, from : Date, to : Date) : Seq[Statistics]
  def tryGetStatistics(meterName : String, from : Date, to : Date) : Try[Seq[Statistics]] = try(Success(getStatistics(meterName,from,to))) catch{case e: Throwable=> Failure(e)}
  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  def getSamplesOfMeter(meterName : String, from : Date, to : Date) : Seq[OldSample]
  def tryGetSamplesOfMeter(meterName : String, from : Date, to : Date) : Try[Seq[OldSample]] = try(Success(getSamplesOfMeter(meterName,from,to))) catch{case e: Throwable=> Failure(e)}

  /**
   * @param from
   * @param to
   * @param resource_id the resource_id of the meter the Samples are relative to.
   * @return Some Samples about that resource from a Date to another or None if an error occurs
   */
  def getSamplesOfResource(resource_id : String, from : Date, to :Date) : Seq[Sample]
  def tryGetSamplesOfResource(resource_id : String, from : Date, to :Date) : Try[Seq[Sample]] = try(Success(getSamplesOfResource(resource_id,from,to))) catch{case e: Throwable=> Failure(e)}
  /**
   * @param queries to be issued
   * @param resource_id the id of the Resource the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  def getSamplesOfResource(resource_id : String, queries : Query*) : Seq[Sample]
  def tryGetSamplesOfResource(resource_id : String, queries : Query*) : Try[Seq[Sample]] = try(Success(getSamplesOfResource(resource_id,queries:_*))) catch{case e: Throwable=> Failure(e)}

  def listAllMeters: Seq[Meter] = listMeters(Seq.empty)
  def tryListAllMeters: Try[Seq[Meter]] = try(Success(listAllMeters)) catch{case e: Throwable=> Failure(e)}

  def listAllResources : Seq[Resource] = listResources(Seq.empty)
  def tryListAllResources: Try[Seq[Resource]] = try(Success(listAllResources)) catch{case e: Throwable=> Failure(e)}
  /**
   * called to shutdown the httpClient
   */
  def shutdown() = logger.info("Shutting down")
}
