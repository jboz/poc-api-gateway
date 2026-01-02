package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;

@RegisterRestClient(configKey = "downstream-api")
@AccessToken
public interface DownstreamRestClient {

    @GET
    String getData();
}
