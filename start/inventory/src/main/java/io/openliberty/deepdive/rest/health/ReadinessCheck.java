package io.openliberty.deepdive.rest.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import java.io.IOException;
import java.net.Socket;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder =
                HealthCheckResponse.named("Readiness Check");

        try {
            Socket socket = new Socket("localhost", 9080);
            socket.close();
            responseBuilder.up();
        } catch (Exception e) {
            responseBuilder.down();
        }
        return responseBuilder.build();
    }
}