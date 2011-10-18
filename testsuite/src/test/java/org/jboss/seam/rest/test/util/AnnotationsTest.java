package org.jboss.seam.rest.test.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.util.Annotations;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AnnotationsTest {
    @Test
    public void testAnnotationSetSearch() throws SecurityException, NoSuchFieldException {
        Field field1 = Resource.class.getDeclaredField("field1");
        Field field2 = Resource.class.getDeclaredField("field2");
        Field field3 = Resource.class.getDeclaredField("field3");
        Set<Annotation> annotations1 = new HashSet<Annotation>((List<? extends Annotation>) Arrays.asList(field1
                .getAnnotations()));
        Set<Annotation> annotations2 = new HashSet<Annotation>((List<? extends Annotation>) Arrays.asList(field2
                .getAnnotations()));
        Set<Annotation> annotations3 = new HashSet<Annotation>((List<? extends Annotation>) Arrays.asList(field3
                .getAnnotations()));

        assertEquals("http://example.com", Annotations.getAnnotation(annotations1, RestClient.class).value());
        assertEquals("http://foo.bar", Annotations.getAnnotation(annotations2, RestClient.class).value());
        assertNull(Annotations.getAnnotation(annotations3, RestClient.class));
    }
}
