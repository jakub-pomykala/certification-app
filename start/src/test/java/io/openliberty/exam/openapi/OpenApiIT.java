/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
package io.openliberty.exam.openapi;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class OpenApiIT {

    private static final Jsonb JSONB = JsonbBuilder.create();

    String expected;
    String port = System.getProperty("http.port");
    String context = System.getProperty("context.root");
    String url = "";

    @Test
    public void testOpenAPI(){
        if (context.equals("/") || context.isEmpty()) {
            url = "http://localhost:" + port + "/";
        } else {
            url = "http://localhost:" + port + context + "/";
        }

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(url + "openapi");
        Response response;
        response = target.request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                "Incorrect response code from " + url);

        response.close();
        client.close();
    }

    @Test
    public void testOpenAPIResponse() throws FileNotFoundException {

        if (context.equals("/") || context.isEmpty()) {
            url = "http://localhost:" + port + "/";
        } else {
            url = "http://localhost:" + port + context + "/";
        }

        RestAssured.baseURI = url;
        RequestSpecification httpRequest = RestAssured.given();
        io.restassured.response.Response response = httpRequest.get("/openapi");

        ResponseBody body = response.getBody();

        File APIExpected = new File("/Users/jakub/Desktop/certification-app/start/src/test/java/io/openliberty/exam/openapi/OpenApiTest.txt");

        BufferedReader br = new BufferedReader(new FileReader(APIExpected));

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

           expected = sb.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        assertEquals(expected, body.asString(),
                "Incorrect response from " + url);
    }
    }
