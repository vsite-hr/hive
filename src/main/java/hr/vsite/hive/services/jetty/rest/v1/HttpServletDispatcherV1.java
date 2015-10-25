package hr.vsite.hive.services.jetty.rest.v1;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

public class HttpServletDispatcherV1 extends HttpServlet30Dispatcher {

	@Override
	public void init(ServletConfig config) throws ServletException {

		config.getServletContext().setInitParameter("resteasy.servlet.mapping.prefix", "/api/1");
		config.getServletContext().setInitParameter("javax.ws.rs.Application", RestApplicationV1.class.getName());
		config.getServletContext().setInitParameter("resteasy.logger.type", "SLF4J");

		super.init(config);
		
	}

	private static final long serialVersionUID = 1L;

}
