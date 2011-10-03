package org.jboss.seam.rest.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.solder.bean.AbstractImmutableBean;
import org.jboss.solder.bean.Beans;

/**
 * We need to create a producer method with the type closure discovered at boot time. Therefore, the producer method has to be
 * registered by extension.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class RestClientProducerBean extends AbstractImmutableBean<Object> {
    private Bean<RestClientProducer> beanDefiningProducerMethod;
    private BeanManager manager;
    private List<InjectionPoint> injectionPoints;

    public RestClientProducerBean(Bean<RestClientProducer> producerBean, Set<Type> types, BeanManager manager) {
        super(RestClientProducer.class, null, Collections.<Annotation>singleton(new RestClient.RestClientLiteral()),
                Dependent.class, null, types, false, false, null, null);

        this.beanDefiningProducerMethod = producerBean;
        this.manager = manager;

        AnnotatedMethod<? super RestClientProducer> annotatedMethod = null;

        for (AnnotatedMethod<? super RestClientProducer> method : manager.createAnnotatedType(RestClientProducer.class)
                .getMethods()) {
            if (method.getAnnotations().isEmpty()) {
                annotatedMethod = method;
            }
        }

        if (annotatedMethod == null) {
            throw new RuntimeException("Unable to find @RestClient producer method.");
        }

        injectionPoints = Beans.createInjectionPoints(annotatedMethod, this, manager);
    }

    public Object create(CreationalContext<Object> creationalContext) {
        // get an instance of the bean declaring the producer method
        RestClientProducer producer = (RestClientProducer) manager.getReference(beanDefiningProducerMethod,
                RestClientProducer.class, manager.createCreationalContext(beanDefiningProducerMethod));

        InjectionPoint ip = (InjectionPoint) manager.getInjectableReference(injectionPoints.get(0), creationalContext);
        ClientExecutor executor = (ClientExecutor) manager.getInjectableReference(injectionPoints.get(1), creationalContext);

        return producer.produceRestClient(ip, executor);
    }

    public void destroy(Object instance, CreationalContext<Object> creationalContext) {
        creationalContext.release();
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return new HashSet<InjectionPoint>(injectionPoints);
    }
}
