package hr.vsite.hive.services.jetty.faces.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

import com.google.inject.Injector;

public class HiveGuiceELResolverWrapper extends ELResolver implements FacesWrapper<ELResolver> {

	public HiveGuiceELResolverWrapper(ELResolver wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ELResolver getWrapped() {
		return wrapped;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
        Object obj = getWrapped().getValue(context, base, property);

        if (null != obj) {
            FacesContext fctx = (FacesContext) context.getContext(FacesContext.class);

            if (null != fctx) {

                Map<String, Object> map = fctx.getExternalContext().getApplicationMap();
                Injector injector = (Injector) map.get(Injector.class.getName());
                if (injector == null) {
                    throw new NullPointerException("Could not locate "
                            + "Guice Injector in application scope using"
                            + " key '" + Injector.class.getName() + "'");
                }
                injector.injectMembers(obj);
            }
        }

        return obj;
    }

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		return getWrapped().getType(context, base, property);
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		getWrapped().setValue(context, base, property, value);
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		return getWrapped().isReadOnly(context, base, property);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return getWrapped().getFeatureDescriptors(context, base);
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return getWrapped().getCommonPropertyType(context, base);
	}

	private ELResolver wrapped;
}
