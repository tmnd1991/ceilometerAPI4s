package uk.co.bigbeeconsultants.http

import java.net.URL

import uk.co.bigbeeconsultants.http.header.{CookieJar, Headers, MediaType}
import uk.co.bigbeeconsultants.http.request.{Request, RequestBody}
import uk.co.bigbeeconsultants.http.response.Response

/**
 * Created by tmnd on 11/11/14.
 */
class ModdedHttpClient(commonConfig: Config = Config()) extends HttpClient(commonConfig){
  def myGet(url: URL, mediaType : MediaType): Response = get(url, Headers(List()), Some(RequestBody("",mediaType)))
  def get(url: URL,
          requestHeaders: Headers,
          body : Option[RequestBody]): Response = makeRequest(Request(Request.GET,
                                                                      url,
                                                                      body,
                                                                      requestHeaders,
                                                                      None))
}
