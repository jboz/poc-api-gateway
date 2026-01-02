package org.acme.other;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/other")
public class OtherResource {

    @GET
    @RolesAllowed("user")
    public String getData() {
        return "Downstream service response";
    }
}
