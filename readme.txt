Seam REST
=============

Seam REST is a lightweight module that aims to provide additional integration 
with technologies within the Java EE platform as well as third party technologies.

Seam REST is independent of CDI and JAX-RS implementations and thus fully portable 
between Java EE 6 environments.

Release Notes
=============

3.0.0.CR1
** Bug
    * [SEAMREST-13] - Removing of a task fails with an exception on JBoss AS
    * [SEAMREST-24] - Manifest.mf files in jars generated from the module contain unreachable Implementation-URL
** Feature Request
    * [SEAMREST-20] - Provide better boot message
    * [SEAMREST-21] - seam-tasks-statistics overhaul
    * [SEAMREST-27] - Register ClientErrorInterceptors automatically
** Task
    * [SEAMREST-25] - Use Solder's @Requires for optional beans
    * [SEAMREST-31] - Rewrite templating providers so that they do not depend on Solder's Expressions
    * [SEAMREST-32] - Move tasks statistics to package org.jboss.seam.rest.example.tasks.statistics

3.0.0.Alpha3
** Feature Request
    * [SEAMREST-9] - Add support for @Valid
    * [SEAMREST-12] - Upgrade to Seam Catch Alpha3
** Task
    * [SEAMREST-15] - Adapt to WELD-796
    * [SEAMREST-16] - Move jaxrs example from Catch to REST
    * [SEAMREST-17] - Switch to beans.xml deployment

3.0.0.Alpha2
** Feature Request
    * [SEAMREST-4] - Support templating using FreeMarker
    * [SEAMREST-5] - Provide integration with RESTEasy Client Framework
    * [SEAMREST-7] - Provide infrastructure for integrating with templating engines
    * [SEAMREST-8] - Create a JAX-RS bridge
    * [SEAMREST-10] - Migrate to Seam Solder
    * [SEAMREST-11] - Provide combined artifact (api+impl)
    * [SEAMREST-14] - Upgrade to Seam Servlet Alpha
