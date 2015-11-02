package hr.vsite.hive.services.jetty.rest.v1.param;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class JaxRsParamImpl implements JaxRsParam, Serializable {

	JaxRsParamImpl(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> clazz() {
		return this.clazz;
	}

	public int hashCode() {
		// This is specified in java.lang.Annotation.
		return (127 * "clazz".hashCode()) ^ clazz.hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof JaxRsParam)) {
			return false;
		}
		JaxRsParam other = (JaxRsParam) o;
		return clazz.equals(other.clazz());
	}

	public String toString() {
		return "@" + JaxRsParam.class.getName() + "(clazz=" + clazz + ")";
	}

	public Class<? extends Annotation> annotationType() {
		return JaxRsParam.class;
	}

	private static final long serialVersionUID = 0;

	private final Class<?> clazz;
	
}
