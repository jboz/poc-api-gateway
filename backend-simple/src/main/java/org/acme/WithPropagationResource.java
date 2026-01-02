package org.acme;

import java.security.Principal;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/propagate")
@RequestScoped
public class WithPropagationResource {

    @Inject
    @RestClient
    DownstreamRestClient dataRestClient;

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        var username = Optional.ofNullable(ctx.getUserPrincipal()).map(Principal::getName).orElse("anonymous");

        return "Hello '" + username + "' from Quarkus REST with data: " + dataRestClient.getData();
    }
}