package org.jboss.seam.rest.test.validation;

import static org.junit.Assert.assertEquals;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.ExceptionStack;
import org.jboss.seam.rest.exceptions.ResponseBuilderProducer;
import org.jboss.seam.rest.exceptions.RestRequest;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.seam.rest.exceptions.integration.CatchValidationExceptionHandler;
import org.jboss.seam.rest.test.Dependencies;
import org.jboss.seam.rest.util.Annotations;
import org.jboss.seam.rest.util.Utils;
import org.jboss.seam.rest.validation.ValidateRequest;
import org.jboss.seam.rest.validation.ValidationException;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ValidationTest {
    @Inject
    @Valid
    private PersonResource validResource;
    @Inject
    @Invalid
    private PersonResource invalidResource;
    @Inject
    private ResourceChild resourceChild;
    @Inject
    @RestResource
    private Instance<ResponseBuilder> builder;
    @Inject
    @RestResource
    private Instance<Response> response;
    @Inject
    private Instance<CatchValidationExceptionHandler> handler;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addAsWebInfResource("org/jboss/seam/rest/test/validation/beans.xml", ArchivePaths.create("beans.xml"));
        war.setWebXML("WEB-INF/web.xml");
        war.addPackages(false, ValidateRequest.class.getPackage(), ValidationTest.class.getPackage());
        war.addClasses(CatchValidationExceptionHandler.class, RestResource.class, RestRequest.class,
                ResponseBuilderProducer.class, Annotations.class, Utils.class);
        war.addClass(Dependencies.class);
        war.addAsLibraries(Dependencies.SEAM_SOLDER);
        return war;
    }

    @Test
    public void testCorrectMessageBody() {
        Person tester = new Person("Jozef", "Hartinger", 22, false);
        validResource.partiallyValidatedOperation(tester);
    }

    @Test
    public void testIncorrectMessageBody() {
        Person tester = new Person("foo", "b", 5, true);
        try {
            validResource.partiallyValidatedOperation(tester);
            throw new RuntimeException("Expected exception not thrown.");
        } catch (ValidationException e) {
            assertEquals(3, e.getViolations().size());
        }
    }

    @Test
    public void testDisabledValidation() {
        Person tester = new Person("foo", "b", 5, true);
        validResource.notValidatedOperation(tester);
    }

    @Test
    public void testIncorrectFormBean() {
        FormBean form1 = new FormBean(null, "a");
        FormBean form2 = new FormBean(null, "bb");
        try {
            validResource.formOperation(form1, form2);
            throw new RuntimeException("Expected exception not thrown.");
        } catch (ValidationException e) {
            assertEquals(3, e.getViolations().size());
        }
    }

    @Test
    public void testValidationExceptionHandler() {
        Person tester = new Person("foo", "bar", 100, true);

        try {
            // prepare exception
            validResource.partiallyValidatedOperation(tester);
            throw new RuntimeException("Expected exception not thrown.");
        } catch (final ValidationException e) {
            // pass the exception to the handler
            ExceptionStack stack = new ExceptionStack(e);
            this.handler.get().handleValidationException(new CaughtException<ValidationException>(stack, false, false) {
                @Override
                public ValidationException getException() {
                    return e;
                }

                @Override
                public void handled() {
                }

            }, builder.get());

            assertEquals(400, response.get().getStatus());
            assertEquals("must be false", response.get().getEntity().toString().trim());
        }
    }

    @Test
    public void testGroups() {
        Person partiallyValidPerson = new Person("foo", "bar", 100, false);
        Person completelyValidPerson = new Person("foo", "bar", 100, false, "foobar");

        validResource.partiallyValidatedOperation(partiallyValidPerson);
        validResource.completelyValidatedOperation(completelyValidPerson);

        try {
            validResource.completelyValidatedOperation(partiallyValidPerson);
            throw new RuntimeException("Expected exception not thrown.");
        } catch (ValidationException e) {
            // expected
        }
    }

    @Test
    public void testResourceHierarchy() {
        try {
            resourceChild.ping(new Person());
            throw new RuntimeException("Expected exception not thrown.");
        } catch (ValidationException e) {
            // expected
        }
    }

    @Test
    public void testResourceValidation() {
        Person validPerson = new Person("foo", "bar", 100, false, "foobar");
        try {
            invalidResource.completelyValidatedOperation(validPerson);
            throw new RuntimeException("Expected exception not thrown.");
        } catch (ValidationException e) {
            assertEquals(3, e.getViolations().size());
        }
    }
}
