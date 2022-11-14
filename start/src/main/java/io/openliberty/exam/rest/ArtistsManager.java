package io.openliberty.exam.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import io.openliberty.exam.rest.model.ArtistsList;
import io.openliberty.exam.rest.model.SystemData;

// tag::ApplicationScoped[]
@ApplicationScoped
// end::ApplicationScoped[]
public class ArtistsManager {

    private List<SystemData> systems = Collections.synchronizedList(new ArrayList<>());
    // tag::timedForGet[]
    // tag::nameForGet[]
    @Timed(name = "ArtistsProcessingTime",
            // end::nameForGet[]
            // tag::tagForGet[]
            tags = {"method=get"},
            // end::tagForGet[]
            // tag::absoluteForGet[]
            absolute = true,
            // end::absoluteForGet[]
            // tag::desForGet[]
            description = "Time needed to process the Artists")
    // end::desForGet[]
    // end::timedForGet[]
    // tag::get[]
    public Properties get(String hostname) {
        return null;
    }
    // end::get[]

    // tag::timedForAdd[]
    // tag::nameForAdd[]
    @SimplyTimed(name = "ArtistsAddingTime",
            // end::nameForAdd[]
            // tag::absoluteForAdd[]
            absolute = true,
            // end::absoluteForAdd[]
            // tag::desForAdd[]
            description = "Time needed to add system properties to the Artists")
    // end::desForAdd[]
    // end::timedForAdd[]
    // tag::add[]
    public void add(String hostname, Properties systemProps) {
        Properties props = new Properties();
        props.setProperty("os.name", systemProps.getProperty("os.name"));
        props.setProperty("user.name", systemProps.getProperty("user.name"));
    }

    @Timed(name = "ArtistsProcessingTime",
            tags = {"method=list"},
            absolute = true,
            description = "Time needed to process the Artists")
    @Counted(name = "ArtistsAccessCount",
            absolute = true,
            description = "Number of times the list of systems method is requested")

    public ArtistsList list() {
        return new ArtistsList(systems);
    }


    @Gauge(unit = MetricUnits.NONE,
            name = "artistsSizeGauge",
            absolute = true,
            description = "Number of systems in the Artists")
    public int getTotal() {
        return systems.size();
    }
}
