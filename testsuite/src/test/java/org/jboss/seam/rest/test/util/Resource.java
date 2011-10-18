package org.jboss.seam.rest.test.util;

import org.jboss.seam.rest.client.RestClient;

@SuppressWarnings("unused")
class Resource {
    @Example
    private Void field1;
    @RestClient("http://foo.bar")
    private Void field2;
    private Void field3;
}
