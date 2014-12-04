package myUtils

import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * Wrappers of java functions to manipulate Timestamps
 * @author Antonio Murgia
 * @version 04/12/2014
 */
object TimestampUtils {
  def parse(stringTimestamp : String) = Timestamp.valueOf(stringTimestamp.replaceAll("T", " "))
  def parseOption(stringTimestamp : String) = try{
    Some(parse(stringTimestamp))
  }
  catch{
    case e : Throwable => None
  }
  def format(t : Timestamp) = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.").format(t)+t.getNanos/1000
}
