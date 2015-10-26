package hr.vsite.hive.services.jetty.rest.v1.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.inject.Injector;

import hr.vsite.hive.HiveStatus;
import hr.vsite.hive.services.jetty.rest.v1.jaxb.JAXBHiveStatus;

@Path("")
public class RootResource {

	@Inject
	public RootResource(Injector injector) {
		this.injector = injector;
	}
	
	@GET
	@Path("status")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public JAXBHiveStatus status() {
		return new JAXBHiveStatus(injector.getInstance(HiveStatus.class));
	}
   
	@GET
	@Path("ip")
	@Produces(MediaType.TEXT_PLAIN)
	public String ip(@Context HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	private final Injector injector;
    
}