package org.jboss.seam.resteasy.example.tasks.noxml;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Specializes;
import javax.persistence.NoResultException;

import org.jboss.seam.resteasy.configuration.ExceptionMapping;
import org.jboss.seam.resteasy.configuration.SeamResteasyConfiguration;

@Specializes
public class CustomSeamResteasyConfiguration extends SeamResteasyConfiguration {

	@PostConstruct
	public void setup()
	{
		addExceptionMapping(new ExceptionMapping(NoResultException.class, 404, "Requested resource with id #{pathParameters['id']} does not exist."));
		addExceptionMapping(new ExceptionMapping(IllegalArgumentException.class, 400, "Illegal parameter value."));
		addMediaTypeMapping("xml", "application/xml");
		addMediaTypeMapping("json", "application/json");
	}
}
