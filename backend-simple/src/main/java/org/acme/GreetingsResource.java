package org.acme;

import java.security.Principal;
import java.util.Optional;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/greets")
@RequestScoped
public class GreetingsResource {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        var username = Optional.ofNullable(ctx.getUserPrincipal()).map(Principal::getName).orElse("anonymous");
        return "Hello '" + username + "' from Quarkus REST";
    }
}