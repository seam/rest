Seam Tasks Example
==============================================================

Deploying to JBoss AS 6
======================
mvn clean package -Pjbossas6
cp target/rest-tasks.war $JBOSS_HOME/server/default/deploy
$JBOSS_HOME/bin/run.sh -Djboss.i18n.generate-proxies=true

Deploying to JBoss AS 7
======================
mvn clean package
$JBOSS_HOME/bin/jboss-admin.sh --connect
deploy target/rest-tasks.war

Deploying to Glassfish
======================
mvn clean package -Pglassfish
$GF_HOME/bin/asadmin start-database
$GF_HOME/bin/asadmin start-domain
$GF_HOME/bin/asadmin deploy target/rest-tasks.war

Deploying to Resin (not currently supported)
======================
mvn clean package -Presin
cp target/rest-tasks.war $RESIN_HOME/webapps
$RESIN_HOME/bin/resin.sh start

Build options
======================
To turn on Seam Catch integration, use -Pcatch (e.g. mvn clean package -Pcatch)
To use Apache Velocity instead of FreeMarker, use -Pvelocity (e.g. mvn clean package -Pvelocity)
Build options can be combined and used during functional testsuite execution.

Running functional tests
======================
The following configurations are supported:
mvn clean verify -Darquillian=jbossas-managed-6
mvn clean verify -Darquillian=jbossas-managed-7
mvn clean verify -Darquillian=glassfish-remote-3.1

mvn clean verify -Pjbossas6 -Darquillian=jbossas-remote-6
mvn clean verify -Pjbossas7 -Darquillian=jbossas-remote-7

Note that you need to set the JBOSS_HOME environment variable properly for the managed configurations.
Make sure that the application is not deployed before running the functional test.