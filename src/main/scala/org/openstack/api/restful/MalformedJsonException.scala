package org.openstack.api.restful


/**
 * Exception that's thrown when the json syntax is malformed
 * @author Antonio Murgia
 * @version 09/11/14
 */
class MalformedJsonException(s: String = "Malformed Json") extends java.text.ParseException(s, 0)
