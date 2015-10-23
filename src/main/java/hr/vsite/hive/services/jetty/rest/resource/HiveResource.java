package hr.vsite.hive.services.jetty.rest.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveConfiguration;

@Path("/hive")
public class HiveResource {

	@Inject
	public HiveResource(HiveConfiguration conf) {
		log.info("From REST: {}", conf.getString("hive.DataPath"));
	}
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return "pong";
	}
   
    private static final Logger log = LoggerFactory.getLogger(HiveResource.class);

}