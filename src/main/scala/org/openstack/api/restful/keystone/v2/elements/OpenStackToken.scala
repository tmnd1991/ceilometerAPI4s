package org.openstack.api.restful.keystone.v2.elements

import java.sql.Timestamp
import java.util.Date

/**
 * Created by tmnd on 09/11/14.
 */
case class OpenStackToken(issued_at : Timestamp,
                          expires : Date,
                          id : String,
                          tenant : OpenStackTenant)