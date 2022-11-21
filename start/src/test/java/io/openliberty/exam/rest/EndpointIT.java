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
package io.openliberty.exam.rest;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndpointIT {
    private static final Jsonb JSONB = JsonbBuilder.create();
        @Test
        public void testGetProperties() {
                String port = System.getProperty("http.port");
        String context = System.getProperty("context.root");
                String url = "";
        if (context.equals("/") || context.isEmpty()) {
            url = "http://localhost:" + port + "/";
        } else {
            url = "http://localhost:" + port + context + "/";
        }

                Client client = ClientBuilder.newClient();
        
                WebTarget target = client.target(url + "artists/properties");
                        Response response;
        response = target.request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                "Incorrect response code from " + url);
        
                String json = response.readEntity(String.class);
        Properties sysProps = JSONB.fromJson(json, Properties.class);

                assertEquals(System.getProperty("os.name"), sysProps.getProperty("os.name"),
                "The system property for the local and remote JVM should match");
                        response.close();
        client.close();
    }

    @Test
        public void testPost() throws FileNotFoundException {
                String port = System.getProperty("http.port");
        String context = System.getProperty("context.root");
                String url = "";
        if (context.equals("/") || context.isEmpty()) {
            url = "http://localhost:" + port + "/";
        } else {
            url = "http://localhost:" + port  + context + "/";
        }

                Client client = ClientBuilder.newClient();
        
                WebTarget target = client.target(url + "artists/systems/add");
                
        final String jsonFile = "/Users/jakub/Desktop/certification-app/start/src/test/java/io/openliberty/exam/rest/jsonTest.json";

        InputStream fis;
        fis = new FileInputStream(jsonFile);
        JsonArray artist = Json.createReader(fis).readArray();


        Response response = target.request().post(Entity.entity(artist,MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(),
                "Incorrect response code from " + url);

        response.close();
        client.close();
    }
}
