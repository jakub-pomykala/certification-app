// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.exam.rest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Reader {

    private static List<JsonObject> listOfArtists = Collections.synchronizedList(new ArrayList<>());

    public static List<JsonObject> getArtists() {
        final String jsonFile = "/Users/jakub/Desktop/certification-app/start/src/main/resources/artists.json";
        try {
            InputStream fis;
            fis = new FileInputStream(jsonFile);
            JsonObject artist = Json.createReader(fis).readObject();
            listOfArtists.add(artist);
            return listOfArtists;
        } catch (FileNotFoundException e) {
            System.err.println("File does not exist: " + jsonFile);
            return null;
        }
    }
}
