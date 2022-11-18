package io.openliberty.exam.openapi;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class OpenApiIT {

    private static final Jsonb JSONB = JsonbBuilder.create();

    @Test
    public void testOpenAPI() throws FileNotFoundException {
        // tag::systemProperties[]
        String port = System.getProperty("http.port");
        String context = System.getProperty("context.root");
        // end::systemProperties[]
        String url = "";
        if (context.equals("/") || context.isEmpty()) {
            url = "http://localhost:" + port + "/";
        } else {
            url = "http://localhost:" + port + "/" + context + "/";
        }

        // tag::clientSetup[]
        Client client = ClientBuilder.newClient();
        // end::clientSetup[]

        // tag::target[]
        WebTarget target = client.target(url + "openapi");
        // end::target[]
        // tag::requestget[]
        Response response;
        response = target.request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                "Incorrect response code from " + url);
        // end::assertequals[]

//        String output = response.readEntity(String.class);
//
//        final String file = "/Users/jakub/Desktop/certification-app/start/src/test/java/io/openliberty/exam/openapi/openAPIResponse.json";
//
//        InputStream fis;
//        fis = new FileInputStream(file);
//        JsonArray expected = Json.createReader(fis).readArray();
//
        response.close();
        client.close();
    }

    @Test
    public void testOpenAPIResponse() {

        RestAssured.registerParser("text/plain", Parser.HTML);

        get("http://localhost:9080/openapi").then().statusCode(200).assertThat()
                .body(startsWith("---\n" +
                        "openapi: 3.0.3\n" +
                        "info:\n" +
                        "  title: Generated API\n" +
                        "  version: \"1.0\"\n" +
                        "servers:\n" +
                        "- url: http://localhost:9080\n" +
                        "- url: https://localhost:9443\n" +
                        "paths:\n" +
                        "  /artists/properties:\n" +
                        "    get:\n" +
                        "      responses:\n" +
                        "        \"200\":\n" +
                        "          description: OK\n" +
                        "          content:\n" +
                        "            application/json:\n" +
                        "              schema:\n" +
                        "                type: object\n" +
                        "  /artists/systems:\n" +
                        "    get:\n" +
                        "      summary: Display artists.\n" +
                        "      description: Returns the currently stored artists in the artists.json.\n" +
                        "      operationId: getArtists\n" +
                        "      responses:\n" +
                        "        \"200\":\n" +
                        "          description: A list of all the artists and their information in the artists.json.\n" +
                        "          content:\n" +
                        "            application/json:\n" +
                        "              schema:\n" +
                        "                $ref: '#/components/schemas/SystemData'\n" +
                        "  /artists/systems/add:\n" +
                        "    post:\n" +
                        "      summary: Add a new artist.\n" +
                        "      description: Adds a new artist to the list of artists\n" +
                        "      operationId: addArtist\n" +
                        "      requestBody:\n" +
                        "        content:\n" +
                        "          application/json:\n" +
                        "            schema:\n" +
                        "              type: array\n" +
                        "      responses:\n" +
                        "        \"200\":\n" +
                        "          description: Add a new artists to the list of artists\n" +
                        "          content:\n" +
                        "            application/json:\n" +
                        "              schema:\n" +
                        "                $ref: '#/components/schemas/SystemData'\n" +
                        "components:\n" +
                        "  schemas:\n" +
                        "    SystemData:\n" +
                        "      type: object\n" +
                        "      properties:\n" +
                        "        id:\n" +
                        "          format: int32\n" +
                        "          type: integer"));
    }
}
