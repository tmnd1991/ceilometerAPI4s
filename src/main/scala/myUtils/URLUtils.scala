package myUtils

import java.net.URL

/**
 * Wrappers of java functions to manipulate URLS
 * @author Antonio Murgia
 * @version 04/12/2014
 */
object URLUtils {
  def apply(s : String) = try{
    Some(new URL(s))
  }
  catch{
    case e : Throwable => None
  }
}
