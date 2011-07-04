package org.jboss.seam.rest.test;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public interface Dependencies {
    public static final Archive<?>[] SEAM_SOLDER = DependencyResolvers.use(MavenDependencyResolver.class)
            .loadReposFromPom("pom.xml").artifact("org.jboss.seam.solder:seam-solder").exclusion("*")
            .resolveAs(GenericArchive.class).toArray(new Archive<?>[0]);

    public static final Archive<?>[] SEAM_CATCH = DependencyResolvers.use(MavenDependencyResolver.class)
            .loadReposFromPom("pom.xml").artifact("org.jboss.seam.catch:seam-catch").exclusion("*")
            .resolveAs(GenericArchive.class).toArray(new Archive<?>[0]);

    public static final Archive<?>[] FREEMARKER = DependencyResolvers.use(MavenDependencyResolver.class)
            .loadReposFromPom("pom.xml").artifact("org.freemarker:freemarker").exclusion("*").resolveAs(GenericArchive.class)
            .toArray(new Archive<?>[0]);

    public static final Archive<?>[] VELOCITY = DependencyResolvers.use(MavenDependencyResolver.class)
            .loadReposFromPom("pom.xml").artifact("org.apache.velocity:velocity").resolveAs(GenericArchive.class)
            .toArray(new Archive<?>[0]);

    public static final Archive<?>[] VELOCITY_TOOLS = DependencyResolvers.use(MavenDependencyResolver.class)
            .loadReposFromPom("pom.xml").artifact("org.apache.velocity:velocity-tools").exclusion("*").resolveAs(GenericArchive.class)
            .toArray(new Archive<?>[0]);
}
