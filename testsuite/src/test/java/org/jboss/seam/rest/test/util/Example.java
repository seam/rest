package org.jboss.seam.rest.test.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.seam.rest.client.RestClient;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

@Target({ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@RestClient("http://example.com")
        @interface Example {

}
