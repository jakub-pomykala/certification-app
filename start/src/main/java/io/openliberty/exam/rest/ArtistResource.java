package io.openliberty.exam.rest;

import io.openliberty.exam.rest.model.SystemData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import java.util.List;


@ApplicationScoped
@Path("systems")
public class ArtistResource {

    @Inject
    Artists artists;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(value = SystemData.class,
            responseDescription = "A list of all the artists and their information in the artists.json.",
            responseCode = "200")
    @Operation(
            summary = "Display artists.",
            description = "Returns the currently stored artists in the artists.json.",
            operationId = "displayArtists")
    public List<JsonObject> getArtists() {
        return artists.getListOfArtists();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addArtist(JsonObject artist) {
        artists.addArtist(artist);
        System.out.println(artist);
        return success(" was added.");
    }

    private Response success(String message) {
        return Response.ok("{ \"ok\" : \"" + message + "\" }").build();
    }
}
