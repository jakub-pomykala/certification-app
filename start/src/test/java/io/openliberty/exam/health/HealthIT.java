package io.openliberty.exam.health;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import jakarta.json.JsonArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HealthIT {

    private JsonArray servicesStates;
    private static HashMap<String, String> endpointData;

    private String HEALTH_ENDPOINT = "health";
    private String READINESS_ENDPOINT = "health/ready";
    private String LIVENES_ENDPOINT = "health/live";
    private String STARTUP_ENDPOINT = "health/started";

    @BeforeEach
    public void setup() {
        endpointData = new HashMap<String, String>();
    }

    @Test
    public void testStartup() {
        endpointData.put("Startup Check", "UP");

        servicesStates = HealthITUtil.connectToHealthEnpoint(200, STARTUP_ENDPOINT);
        System.out.println(servicesStates);
        checkStates(endpointData, servicesStates);
    }

    @Test
    public void testLiveness() {
        endpointData.put("Liveness Check", "UP");

        servicesStates = HealthITUtil.connectToHealthEnpoint(200, LIVENES_ENDPOINT);
        checkStates(endpointData, servicesStates);
    }

    @Test
    public void testReadiness() {
        endpointData.put("Readiness Check", "UP");
        servicesStates = HealthITUtil.connectToHealthEnpoint(200, READINESS_ENDPOINT);
        checkStates(endpointData, servicesStates);
    }

    @Test
    public void testHealth() {
        endpointData.put("Startup Check", "UP");
        endpointData.put("Liveness Check", "UP");
        endpointData.put("Readiness Check", "UP");
        endpointData.put("Startup Check", "UP");
        endpointData.put("Liveness Check", "UP");
        endpointData.put("Readiness Check", "UP");

        servicesStates = HealthITUtil.connectToHealthEnpoint(200, HEALTH_ENDPOINT);
        checkStates(endpointData, servicesStates);
    }

    private void checkStates(HashMap<String, String> testData, JsonArray servStates) {
        testData.forEach((service, expectedState) -> {
            assertEquals(expectedState, HealthITUtil.getActualState(service, servStates),
                    "The state of " + service + " service is not matching.");
        });
    }

}
// end::HealthIT[]


