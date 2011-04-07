Seam REST Exceptions Example
==============================================================

Deploying to JBoss AS 6
======================
mvn clean package
cp target/rest-exceptions.war $JBOSS_HOME/server/default/deploy
$JBOSS_HOME/bin/run.sh -Djboss.i18n.generate-proxies=true

Deploying to Glassfish
======================
mvn clean package -Pglassfish
$GF_HOME/bin/asadmin start-domain
$GF_HOME/bin/asadmin deploy target/rest-exceptions.war

Running tests
======================
Deploy the application
Run mvn clean verify (DO NOT run the ftest profile!)
