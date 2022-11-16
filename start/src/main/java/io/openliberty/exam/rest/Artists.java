package io.openliberty.exam.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class Artists {

    private List<JsonObject> listOfArtists = Collections.synchronizedList(new ArrayList<>());

    public List<JsonObject> getListOfArtists() {
        if (listOfArtists.isEmpty()) {
            for (JsonValue s : Reader.getArtists()) {
                listOfArtists.add((JsonObject) s);
            }
        }
        return listOfArtists;
    }

    public JsonObject addArtist(JsonObject artist) {
        listOfArtists.add(artist);

        return artist;
    }
}
