package org.jboss.seam.rest.example.tasks;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.jboss.seam.rest.example.tasks.resource.CategoryCollectionResource;
import org.jboss.seam.rest.example.tasks.resource.CategoryResource;
import org.jboss.seam.rest.example.tasks.resource.TaskCollectionResource;
import org.jboss.seam.rest.exceptions.SeamExceptionMapper;
import org.jboss.seam.rest.templating.TemplatingMessageBodyWriter;

@ApplicationPath("/api/*")
public class TasksApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(CategoryCollectionResource.class);
        classes.add(CategoryResource.class);
        classes.add(TaskCollectionResource.class);
        classes.add(SeamExceptionMapper.class);
        classes.add(TemplatingMessageBodyWriter.class);
        classes.add(UriInfoProducer.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.<Object> singleton(new JacksonJaxbJsonProvider());
    }
}
