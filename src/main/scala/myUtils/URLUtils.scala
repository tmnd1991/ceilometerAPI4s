package myUtils

import java.net.URL

/**
 * Wrappers of java functions to manipulate URLS
 * @author Antonio Murgia
 * @version 04/12/2014
 */
object URLUtils {
  /**
   * A simple wrapper around URL constructor
   * @param s the string to be converted in URL
   * @return Some URL or None if an error occurs
   */
  def apply(s : String) = try{
    Some(new URL(s))
  }
  catch{
    case e : Throwable => None
  }
}
