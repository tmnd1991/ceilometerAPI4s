package org.openstack.api.restful.ceilometer.v2.elements

import java.sql.Timestamp
import java.util.Date

import org.openstack.api.restful.elements.Link
/**
 * Created by tmnd on 21/10/14.
 */
case class Resource(first_sample_timestamp : Option[Timestamp],
                    last_sample_timestamp : Option[Timestamp],
                    links : Seq[Link],
                    metadata : Map[String,String],
                    project_id : String,
                    resource_id : String,
                    source : String,
                    user_id : String) {
  require(project_id != null)
  require(project_id.nonEmpty)
  require(resource_id != null)
  require(resource_id.nonEmpty)
  require(source != null)
  require(source.nonEmpty)
  require(user_id != null)
  require(user_id.nonEmpty)
}
/**
Type:	datetime
UTC date & time not later than the first sample known for this resource

last_sample_timestamp
Type:	datetime
UTC date & time not earlier than the last sample known for this resource

links
Type:	list(Link)
A list containing a self link and associated meter links

metadata
Type:	dict(unicode: unicode)
Arbitrary metadata associated with the resource

project_id
Type:	unicode
The ID of the owning project or tenant

resource_id
Type:	unicode
The unique identifier for the resource

source
Type:	unicode
The source where the resource come from

user_id
Type:	unicode
The ID of the user who created the resource or updated it last
  **/