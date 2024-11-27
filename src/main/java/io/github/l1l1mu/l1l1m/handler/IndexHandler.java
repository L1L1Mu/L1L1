package io.github.l1l1mu.l1l1m.handler;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class IndexHandler {

    @GET
    public String index() {
        return "Helo World!";
    }
}
