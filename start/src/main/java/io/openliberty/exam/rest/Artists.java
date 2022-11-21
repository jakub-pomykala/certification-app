package io.openliberty.exam.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class Artists {

    private List<JsonArray> listOfArtists = Collections.synchronizedList(new ArrayList<>());

    int counter = 0;

    public List<JsonArray> getListOfArtists() {
        if (listOfArtists.isEmpty() || counter != 0) {
            for (JsonArray s : Reader.getArtists()) {
                listOfArtists.add(s);
            }
        }
        return listOfArtists;
    }

    public JsonArray addArtist(JsonArray artist) {
        listOfArtists.add(artist);
        counter++;
        return artist;
    }
}
