package spray.json

/**
 * @author Antonio Murgia
 * @version 27/11/14
 * Some modding to spray-json
 */
object MyMod {
  implicit class pimpedString(s : String){
    import spray.json._
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
