package org.jboss.seam.rest.templating;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

/**
 * Wraps TemplatingModel to allow objects to be referenced via EL in templates.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class ModelWrapper extends HashMap<String, Object> {
    private static final long serialVersionUID = -4511289530418970162L;
    private BeanManager manager;

    public ModelWrapper(Map<String, Object> model, BeanManager manager) {
        super(model);
        this.manager = manager;
    }

    @Override
    public Object get(Object key) {
        if (containsKey(key)) {
            return super.get(key);
        }
        if (key instanceof String) {
            String name = (String) key;
            Set<Bean<?>> beans = manager.getBeans(name);
            if (!beans.isEmpty()) {
                Bean<?> bean = manager.resolve(beans);
                if (bean != null) {
                    CreationalContext<?> ctx = manager.createCreationalContext(bean);
                    return manager.getReference(bean, bean.getBeanClass(), ctx);
                }
            }
        }
        return null;
    }
}
