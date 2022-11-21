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

@ApplicationScoped
public class ArtistsManager {

    private List<SystemData> systems = Collections.synchronizedList(new ArrayList<>());
        @Timed(name = "ArtistsProcessingTime",
                                    tags = {"method=get"},
                                    absolute = true,
                                    description = "Time needed to process the Artists")
                public Properties get(String hostname) {
        return null;
    }
    
            @SimplyTimed(name = "ArtistsAddingTime",
                                    absolute = true,
                                    description = "Time needed to add system properties to the Artists")
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
