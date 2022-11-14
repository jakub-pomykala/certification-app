package io.openliberty.exam.system;

import java.util.Properties;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.annotation.Counted;

@RequestScoped
@Path("properties")
public class SystemResource {

    @Counted(name = "getPropertiesCount",
            absolute = true,
            description = "Number of times the GET properties method is requested")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Properties getProperties() {
        return System.getProperties();
    }
}
