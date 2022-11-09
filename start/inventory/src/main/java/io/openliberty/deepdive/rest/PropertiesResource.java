package io.openliberty.deepdive.rest;

import java.util.Properties;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// tag::path[]
@Path("properties")
// end::path[]
public class PropertiesResource {

    // tag::get[]
    @GET
    // end::get[]
    // tag::produces[]
    @Produces(MediaType.APPLICATION_JSON)
    // end::produces[]
    public Properties getProperties() {
        return System.getProperties();
    }
}
