package org.jboss.seam.rest.test.compat.ejb.deployment;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DeploymentOrderTest {
    @Deployment
    public static WebArchive getDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(Filter.class, Foxtrot.class, Echo.class)
                .addWebResource(EmptyAsset.INSTANCE, "beans.xml").addWebResource("WEB-INF/web.xml", "web.xml");
    }

    @Test
    public void test() {
        // noop
        // we are interested in whether the app deploys
    }
}
