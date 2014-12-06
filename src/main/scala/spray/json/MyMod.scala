package spray.json

/**
 * Some modding to spray-json
 * @author Antonio Murgia
 * @version 27/11/14
 */
object MyMod {
  implicit class pimpedString(s : String){
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
  implicit class pimpedJson(json : spray.json.JsValue){
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
