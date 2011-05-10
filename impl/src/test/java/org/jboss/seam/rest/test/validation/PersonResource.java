package org.jboss.seam.rest.test.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jboss.seam.rest.validation.ValidateRequest;

@SuppressWarnings("unused")
@Valid
public class PersonResource {
    /*
     * These fields simulate JAX-RS @Query parameters
     */
    @NotNull
    @Size(min = 1, max = 30)
    private String query;
    @Min(0)
    private int start;
    @Min(0)
    @Max(50)
    private int limit;

    public PersonResource() {
        this("Jozef", 0, 20);
    }

    public PersonResource(String query, int start, int limit) {
        this.query = query;
        this.start = start;
        this.limit = limit;
    }

    @ValidateRequest(groups = PartialValidation.class)
    public void partiallyValidatedOperation(Person person) {
    }

    @ValidateRequest
    public void completelyValidatedOperation(Person person) {
    }

    @ValidateRequest(validateMessageBody = false)
    public void notValidatedOperation(Person person) {
    }

    @ValidateRequest
    public void formOperation(@javax.validation.Valid @FormObject FormBean form1,
                              @javax.validation.Valid @FormObject FormBean form2) {
    }
}
