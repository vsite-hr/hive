package hr.vsite.hive.services.jetty.rest.v1;

import java.time.Instant;

import javax.ws.rs.ext.ParamConverter;

import com.google.inject.AbstractModule;

import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.services.jetty.rest.v1.param.InstantParamConverter;
import hr.vsite.hive.services.jetty.rest.v1.param.JaxRsParams;
import hr.vsite.hive.services.jetty.rest.v1.param.SensorParamConverter;
import hr.vsite.hive.services.jetty.rest.v1.providers.GuiceParamConverterProvider;
import hr.vsite.hive.services.jetty.rest.v1.providers.ObjectMapperProvider;
import hr.vsite.hive.services.jetty.rest.v1.resources.RootResource;
import hr.vsite.hive.services.jetty.rest.v1.resources.SensorResource;
import hr.vsite.hive.services.jetty.rest.v1.resources.TickResource;

/**
 * Place to register all JAX-RS resources for API v1
 */
public class ResourceModuleV1 extends AbstractModule {

	@Override 
	protected void configure() {

		// providers
		bind(ObjectMapperProvider.class);
		bind(GuiceParamConverterProvider.class);
		
		// params
		bind(ParamConverter.class).annotatedWith(JaxRsParams.forClass(Sensor.class)).to(SensorParamConverter.class);
		bind(ParamConverter.class).annotatedWith(JaxRsParams.forClass(Instant.class)).to(InstantParamConverter.class);
		
		// resources
		bind(RootResource.class);
		bind(SensorResource.class);
		bind(TickResource.class);
		
	}

}
