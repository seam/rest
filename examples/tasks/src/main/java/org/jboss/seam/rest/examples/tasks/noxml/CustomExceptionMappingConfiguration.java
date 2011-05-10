package org.jboss.seam.rest.examples.tasks.noxml;

import javax.enterprise.inject.Alternative;
import javax.persistence.NoResultException;

import org.jboss.seam.rest.SeamRestConfiguration;
import org.jboss.seam.rest.exceptions.ExceptionMapping;

/**
 * This is a configuration for Seam REST exception mapping. Activate this alternative if the XML configuration cannot be used.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Alternative
@ExceptionMapping.List({
        @ExceptionMapping(exceptionType = NoResultException.class, status = 404, message = "Requested resource does not exist."),
        @ExceptionMapping(exceptionType = IllegalArgumentException.class, status = 400, message = "Illegal parameter value.")})
public class CustomExceptionMappingConfiguration extends SeamRestConfiguration {
}
