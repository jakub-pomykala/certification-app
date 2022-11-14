package io.openliberty.exam.rest;

import io.openliberty.exam.rest.model.SystemData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

@ApplicationScoped
@Path("systems")
public class ArtistResource {

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
    public JsonArray getArtists() {
        return Reader.getArtists();
    }
}