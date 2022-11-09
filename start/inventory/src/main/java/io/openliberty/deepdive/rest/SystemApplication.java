package io.openliberty.deepdive.rest;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

// tag::applicationPath[]
@ApplicationPath("system")
// end::applicationPath[]
// tag::systemApplication[]
public class SystemApplication extends Application {

}
// end::systemApplication[]

