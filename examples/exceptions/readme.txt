Seam REST Exceptions Example
==============================================================

Deploying to JBoss AS 7
======================
mvn clean package arquillian:run -Darquillian=jbossas-managed-7

Deploying to JBoss AS 6
======================
mvn clean package arquillian:run -Darquillian=jbossas-managed-6

Deploying to Glassfish
======================
$GF_HOME/bin/asadmin start-domain
mvn clean package arquillian:run -Darquillian=glassfish-remote-3.1

Deploying to Tomcat 7
======================
mvn clean package -Ptomcat
deploy the generated archive

Running tests
======================
The following configurations are supported:
mvn clean verify -Darquillian=jbossas-managed-6
mvn clean verify -Darquillian=jbossas-managed-7
mvn clean verify -Darquillian=glassfish-remote-3.1

mvn clean verify -Pjbossas6 -Darquillian=jbossas-remote-6
mvn clean verify -Pjbossas7 -Darquillian=jbossas-remote-7

Note that you need to set the JBOSS_HOME environment variable properly for the managed configurations.
Make sure that the application is not deployed before running the functional test.
