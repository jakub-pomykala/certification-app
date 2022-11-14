package io.openliberty.deepdive.rest.system;

import java.util.Properties;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("properties")
public class SystemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getProperties() {
        return System.getProperties();
    }
}
