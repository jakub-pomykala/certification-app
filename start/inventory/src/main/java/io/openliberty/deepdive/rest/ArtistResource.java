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
package io.openliberty.deepdive.rest;

import io.openliberty.deepdive.rest.model.SystemData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

@ApplicationScoped
@Path("")
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
