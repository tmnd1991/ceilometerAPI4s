package spray.json

/**
 * Some modding to spray-json
 * @author Antonio Murgia
 * @version 27/11/14
 */
object MyMod {
  implicit class pimpedString(val s : String) extends AnyVal{
    import spray.json._

    /**
     * @return Some JsValue if the parsing is successful or None if an error occurs
     */
    def tryParseJson = {
      try{
        Some(s.parseJson)
      }
      catch{
        case t : Throwable => None
      }
    }
  }
  implicit class pimpedJson(val json : spray.json.JsValue) extends AnyVal{
    /**
     * @tparam T the class to the json has to be converted to
     * @return Some instance of class T or None if an error occurs
     */
    def tryConvertTo[T : JsonReader] = {
      try{
        Some(json.convertTo[T])
      }
      catch{
        case t : Throwable => None
      }
    }
  }
}
