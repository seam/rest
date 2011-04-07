package org.jboss.seam.rest.examples.exceptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@ApplicationScoped
public class DateProducer {
    private DateFormat format = SimpleDateFormat.getDateTimeInstance();

    @Produces
    @Named
    public String getDate() {
        return format.format(new Date());
    }
}
