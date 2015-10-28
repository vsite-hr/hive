package hr.vsite.hive.services.jetty.rest.v1.providers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;

import hr.vsite.hive.services.jetty.rest.v1.param.JaxRsParams;

@Provider
public class GuiceParamConverterProvider implements ParamConverterProvider {

	@Inject
	GuiceParamConverterProvider(Injector injector) {
		this.injector = injector;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType, final Annotation[] annotations) {

		Binding<ParamConverter> binding = injector.getExistingBinding(Key.get(ParamConverter.class, JaxRsParams.forClass(rawType)));
		if (binding == null)
			return null;
		
		return binding.getProvider().get();

	}

	private final Injector injector;
	
}
