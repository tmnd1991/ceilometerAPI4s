package myUtils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Wrappers of java functions to manipulate Dates
 * @author Antonio Murgia
 * @version 04/12/2014
 */
object DateUtils {
  /**
   *
   * @param stringDate the String to be converted
   * @param format the format in SimpleDateFormat format, the default is "yyyy-MM-dd'T'HH:mm:ss'Z'"
   * @return Some Date or None if an error occurs
   */
  def parseOption(stringDate : String, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : Option[Date] = {
    try{
      Some(parse(stringDate,format))
    }
    catch{
      case e : Throwable => None
    }
  }

  /**
   * Simply a wrapper around java.text.SimpleDateFormat
   * @param stringDate the String to be converted
   * @param format the format in SimpleDateFormat format, default is "yyyy-MM-dd'T'HH:mm:ss'Z'"
   * @return the parsed Date
   */
  def parse(stringDate : String, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : Date = {
    new SimpleDateFormat(format).parse(stringDate)
  }

  /**
   * Formats a Date into the specified format
   * @param date the date to be formatted
   * @param format the format to be applied, default is "yyyy-MM-dd'T'HH:mm:ss'Z'"
   * @return the formatted string
   */
  def format(date : Date, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : String = {
    new SimpleDateFormat(format).format(date)
  }

  /**
   *
   * @param date the date to be formatted
   * @param format the format in SimpleDateFormat format, the default is "yyyy-MM-dd'T'HH:mm:ss'Z'"
   * @return Some String or None if an error occurs
   */
  def formatOption(date : Date, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : Option[String] = {
    try{
      Some(this.format(date,format))
    }
    catch{
      case e : Throwable => None
    }
  }
}
