package org.acme.logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;

@Provider // Registers the filter with JAX-RS
public class ServerRequestLoggingFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        // Log basic request info
        Log.debugf("Incoming Request: %s %s",
                context.getMethod(),
                context.getUriInfo().getRequestUri());

        // Log headers
        Log.debug("Headers: " + context.getHeaders());

        // Log request body (if present and not binary)
        if (isTextualMediaType(context.getMediaType())) {
            String body = readBody(context.getEntityStream());
            // Reset the input stream so the resource can read it
            context.setEntityStream(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            Log.debug("Request Body: " + body);
        }
    }

    // Check if media type is text-based (e.g., JSON, XML)
    private boolean isTextualMediaType(MediaType mediaType) {
        return mediaType != null && mediaType.getType() != null
                && (mediaType.getType().equals("text")
                        || mediaType.getSubtype().endsWith("json")
                        || mediaType.getSubtype().endsWith("xml"));
    }

    // Read the request body from the input stream
    private String readBody(InputStream inputStream) throws IOException {
        try (InputStream is = inputStream) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}