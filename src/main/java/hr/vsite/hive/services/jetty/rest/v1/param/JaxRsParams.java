package hr.vsite.hive.services.jetty.rest.v1.param;

public class JaxRsParams {

	private JaxRsParams() {}
	
	public static JaxRsParam forClass(Class<?> clazz) {
		return new JaxRsParamImpl(clazz);
	}

}
