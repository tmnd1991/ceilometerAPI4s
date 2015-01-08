package org.openstack.clients.ceilometer.v2

import java.util.Date

import org.openstack.api.restful.ceilometer.v2.FilterExpressions.Query
import org.openstack.api.restful.ceilometer.v2.elements._

/**
   * High level object that offers services to query a ceilometer endpoint
   * @author Antonio Murgia
   * @version 26/11/14
   */
trait ICeilometerClient {

  /**
   *
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if an error occurs return None
   */
  def tryListMeters(q: Seq[Query]): Option[Seq[Meter]]

  /**
   * @return a collection of resources filtered by the query params if an error occurs return None
   */
  def tryListResources(q: Seq[Query]) : Option[Seq[Resource]]

  /**
   *
   * @param meterName the name of the meter
   * @return a collection of statistics, if an error occurs return None
   */
  def tryGetStatistics(meterName : String) : Option[Seq[Statistics]]

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Statistics is relative to.
   * @return Some statistics about that meter from a Date to another or None if an error occurs
   */
  def tryGetStatistics(meterName : String, from : Date, to : Date) : Option[Seq[Statistics]]

  /**
   * @param from
   * @param to
   * @param meterName the name of the meter the Samples are relative to.
   * @return Some Samples about that meter from a Date to another or None if an error occurs
   */
  def tryGetSamplesOfMeter(meterName : String, from : Date, to : Date) : Option[Seq[OldSample]]


  /**
   * @param from
   * @param to
   * @param resource_id the resource_id of the meter the Samples are relative to.
   * @return Some Samples about that resource from a Date to another or None if an error occurs
   */
  def tryGetSamplesOfResource(resource_id : String, from : Date, to :Date) : Option[Seq[Sample]]

  /**
   * called to shutdown the httpClient
   */
  def shutdown()

  //region syntactic sugar
  /**
   * @return Some collection of meters avaiable if an error occurs returns None
   */
  def tryListAllMeters : Option[Seq[Meter]] = tryListMeters(Seq.empty)
  def tryListAllResources : Option[Seq[Resource]] = tryListResources(Seq.empty)
  /**
   * @param q any number of queries to filter the meters
   * @return a collection of meters filtered by the query params if any error occurs an empty Seq is returned
   */
  def listMeters(q: Query*): Seq[Meter] = tryListMeters(q).getOrElse(List.empty)

  /**
   * @return a collection of meters avaiable if an error occurs an empty Seq is returned
   */
  def listAllMeters: Seq[Meter] = tryListAllMeters.getOrElse(List.empty)

  def listAllResources : Seq[Resource] = tryListAllResources.getOrElse(Seq.empty)

  def getStatistics(meterName : String) : Seq[Statistics] = tryGetStatistics(meterName).getOrElse(List.empty)

  /**
   * @param from
   * @param to
   * @param meterName the name of the Meter the Statistics is relative to.
   * @return statistics about that meter from a Date to another or an empty Seq if an error occurs
   */
  def getStatistics(meterName : String, from : Date, to : Date) : Seq[Statistics] = tryGetStatistics(meterName, from, to).getOrElse(List.empty)

  def getSamplesOfMeter(meterName : String, from : Date, to : Date) : Seq[OldSample] = tryGetSamplesOfMeter(meterName, from , to).getOrElse(Seq.empty)

  def getSampleOfResource(resource_id : String, from : Date, to :Date) : Seq[Sample] = tryGetSamplesOfResource(resource_id, from, to).getOrElse(Seq.empty)

  //endregion
}
