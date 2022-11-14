package io.openliberty.exam.health;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;


public class HealthITUtil {

    private static String port;
    private static String baseUrl;
    public static final String INV_MAINTENANCE_FALSE = "io_openliberty_guides_inventory_"
            + "inMaintenance\":false";
    public static final String INV_MAINTENANCE_TRUE = "io_openliberty_guides_inventory_"
            +  "inMaintenance\":true";

    static {
        port = System.getProperty("default.http.port");
        baseUrl = "http://localhost:" + port + "/";
    }

    public static JsonArray connectToHealthEnpoint(int expectedResponseCode,
                                                   String endpoint) {
        String healthURL = baseUrl + endpoint;
        Client client = ClientBuilder.newClient();
        Response response = client.target(healthURL).request().get();
        assertEquals(expectedResponseCode, response.getStatus(),
                "Response code is not matching " + healthURL);
        JsonArray servicesStates = response.readEntity(JsonObject.class)
                .getJsonArray("checks");
        response.close();
        client.close();
        return servicesStates;
    }

    public static String getActualState(String service, JsonArray servicesStates) {
        String state = "";
        System.out.println(servicesStates);
        for (Object obj : servicesStates) {
            if (obj instanceof JsonObject) {
                if (service.equals(((JsonObject) obj).getString("name"))) {
                    state = ((JsonObject) obj).getString("status");
                }
            }
        }
        return state;
    }

}
// end::HealthTestUtil[]
