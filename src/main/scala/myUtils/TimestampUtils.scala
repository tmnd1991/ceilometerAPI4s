package myUtils

import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * Wrappers of java functions to manipulate Timestamps
 * @author Antonio Murgia
 * @version 04/12/2014
 */
object TimestampUtils {
  /**
   * @param stringTimestamp the String to be converted
   * @return the Timestamp
   */
  def parse(stringTimestamp : String) = Timestamp.valueOf(stringTimestamp.replaceAll("T", " "))

  /**
   * @param stringTimestamp the String to be converted
   * @return Some Timestamp or None if an error occurs
   */
  def parseOption(stringTimestamp : String) = try{
    Some(parse(stringTimestamp))
  }
  catch{
    case e : Throwable => None
  }

  /**
   * @param t the Timestamp to be formatted
   * @return the formatted String
   */
  def format(t : Timestamp) = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.").format(t)+t.getNanos/1000

  /**
   * @param t the Timestamp to be formatted
   * @return Some formatted String or None if an error occurs
   */
  def formatOption(t : Timestamp) = try{
    Some(format(t))
  }
  catch{
    case t : Throwable => None
  }
}
