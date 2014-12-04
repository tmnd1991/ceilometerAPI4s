package myUtils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Wrappers of java functions to manipulate Dates
 * @author Antonio Murgia
 * @version 04/12/2014
 */
object DateUtils {
  def parseOption(stringDate : String, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : Option[Date] = {
    try{
      Some(parse(stringDate,format))
    }
    catch{
      case e : Throwable => None
    }
  }

  def parse(stringDate : String, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : Date = {
    new SimpleDateFormat(format).parse(stringDate)
  }

  def format(date : Date, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : String = {
    new SimpleDateFormat(format).format(date)
  }

  def formatOption(date : Date, format : String = "yyyy-MM-dd'T'HH:mm:ss'Z'") : Option[String] = {
    try{
      Some(this.format(date,format))
    }
    catch{
      case e : Throwable => None
    }
  }
}
