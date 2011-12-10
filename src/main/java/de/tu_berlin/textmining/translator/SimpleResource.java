package de.tu_berlin.textmining.translator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// The Java class will be hosted at the URI path "/greeting"
@Path("greeting")
public class SimpleResource {

	// and implement the following GET method
	@GET
	@Produces("text/plain")
	public String getGreeting() {
		return "Hi there";
	}

}
