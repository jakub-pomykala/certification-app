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
package io.openliberty.exam.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class MetricsIT {

    private static final String KEYSTORE_PATH = System.getProperty("user.dir")
            + "/target/liberty/wlp/usr/servers/"
            + "defaultServer/resources/security/key.p12";
    private static final String SYSTEM_ENV_PATH =  System.getProperty("user.dir")
            + "/target/liberty/wlp/usr/servers/"
            + "defaultServer/server.env";

    private static String httpPort;
    private static String httpsPort;
    private static String baseHttpUrl;
    private static String baseHttpsUrl;
    private static KeyStore keystore;

    private List<String> metrics;
    private Client client;

    private final String INVENTORY_HOSTS = "artists/systems";
    private final String METRICS_APPLICATION = "metrics";

        @BeforeAll
            public static void oneTimeSetup() throws Exception {
        httpPort = System.getProperty("http.port");
        httpsPort = System.getProperty("https.port");
        baseHttpUrl = "http://localhost:9080/";
        baseHttpsUrl = "https://localhost:9443/";
        loadKeystore();
    }
    
    private static void loadKeystore() throws Exception {
        Properties sysEnv = new Properties();
        sysEnv.load(new FileInputStream(SYSTEM_ENV_PATH));
        char[] password = sysEnv.getProperty("keystore_password").toCharArray();
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream(KEYSTORE_PATH), password);
    }

        @BeforeEach
            public void setup() {
        client = ClientBuilder.newBuilder().trustStore(keystore).build();
    }
    
        @AfterEach
            public void teardown() {
        client.close();
    }
    
        @Test
            @Order(1)
            public void testPropertiesRequestTimeMetric() {
        connectToEndpoint(baseHttpUrl + INVENTORY_HOSTS);
        metrics = getMetrics();
        for (String metric : metrics) {
            if (metric.startsWith(
                    "application_inventoryProcessingTime_rate_per_second")) {
                float seconds = Float.parseFloat(metric.split(" ")[1]);
                assertTrue(4 > seconds);
            }
        }
    }
    
        @Test
            @Order(2)
            public void testInventoryAccessCountMetric() {
        metrics = getMetrics();
        Map<String, Integer> accessCountsBefore = getIntMetrics(metrics,
                "application_inventoryAccessCount_total");
        connectToEndpoint(baseHttpUrl + INVENTORY_HOSTS);
        metrics = getMetrics();
        Map<String, Integer> accessCountsAfter = getIntMetrics(metrics,
                "application_inventoryAccessCount_total");
        for (String key : accessCountsBefore.keySet()) {
            Integer accessCountBefore = accessCountsBefore.get(key);
            Integer accessCountAfter = accessCountsAfter.get(key);
            assertTrue(accessCountAfter > accessCountBefore);
        }
    }
    
        @Test
            @Order(3)
            public void testInventorySizeGaugeMetric() {
        metrics = getMetrics();
        Map<String, Integer> inventorySizeGauges = getIntMetrics(metrics,
                "application_inventorySizeGauge");
        for (Integer value : inventorySizeGauges.values()) {
            assertTrue(1 <= value);
        }
    }
        
    public void connectToEndpoint(String url) {
        Response response = this.getResponse(url);
        this.assertResponse(url, response);
        response.close();
    }

    private List<String> getMetrics() {
        String usernameAndPassword = "admin" + ":" + "adminpwd";
        String authorizationHeaderValue = "Basic "
                + java.util.Base64.getEncoder()
                .encodeToString(usernameAndPassword.getBytes());
        Response metricsResponse = client.target(baseHttpsUrl + METRICS_APPLICATION)
                .request(MediaType.TEXT_PLAIN)
                .header("Authorization",
                        authorizationHeaderValue)
                .get();

        BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)
                metricsResponse.getEntity()));
        List<String> result = new ArrayList<String>();
        try {
            String input;
            while ((input = br.readLine()) != null) {
                result.add(input);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        metricsResponse.close();
        return result;
    }

    private Response getResponse(String url) {
        return client.target(url).request().get();
    }

    private void assertResponse(String url, Response response) {
        assertEquals(200, response.getStatus(), "Incorrect response code from " + url);
    }

    private Map<String, Integer> getIntMetrics(List<String> metrics, String metricName) {
        Map<String, Integer> output = new HashMap<String, Integer>();
        for (String metric : metrics) {
            if (metric.startsWith(metricName)) {
                String[] mSplit = metric.split(" ");
                String key = mSplit[0];
                Integer value = Integer.parseInt(mSplit[mSplit.length - 1]);
                output.put(key, value);
            }
        }
        return output;
    }
}
