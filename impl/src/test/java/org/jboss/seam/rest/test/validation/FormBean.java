package org.jboss.seam.rest.test.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class FormBean {
    @FormParam("foo")
    @NotNull
    private String foo;
    @FormParam("bar")
    @Size(min = 2)
    private String bar;

    public FormBean(String foo, String bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
