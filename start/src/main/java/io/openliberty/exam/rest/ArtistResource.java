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

import io.openliberty.exam.rest.model.ArtistData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import java.util.List;


@ApplicationScoped
@Path("/")
public class ArtistResource {

    @Inject
    Artists artists;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(value = ArtistData.class,
            responseDescription = "A list of all the artists and their information in the artists.json.",
            responseCode = "200")
    @Operation(
            summary = "Display artists.",
            description = "Returns the currently stored artists in the listOfArtists.",
            operationId = "getArtists")
    @Counted(name = "getArtistsCount",
            absolute = true,
            description = "Number of times list of Artists is requested")
    public List<JsonArray> getArtists() {
        return artists.getListOfArtists();
    }


    @POST
    @Path("/artist")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(value = ArtistData.class,
            responseDescription = "Add a new artists to the list of artists",
            responseCode = "200")
    @Operation(
            summary = "Add a new artist.",
            description = "Adds a new artist to the list of artists",
            operationId = "addArtist")
    @SimplyTimed(name = "ArtistAddingTime",
            absolute = true,
            description = "Time needed to add a artist to the list of Artists")
    public Response addArtist(JsonArray artist) {
        artists.addArtist(artist);
        System.out.println("new artist added: " + artist);
        return Response.ok("{ \"ok\" : \"" + artist + " was added." + "\" }").build();
    }
}
