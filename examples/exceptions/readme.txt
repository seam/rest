Seam REST Exceptions Example
==============================================================

Deploying to JBoss AS 6
======================
mvn clean package
cp target/rest-exceptions.war $JBOSS_HOME/server/default/deploy
$JBOSS_HOME/bin/run.sh -Djboss.i18n.generate-proxies=true

Deploying to JBoss AS 7
======================
mvn clean package
$JBOSS_HOME/bin/jboss-admin.sh --connect
deploy target/rest-exceptions.war

Deploying to Glassfish
======================
mvn clean package
$GF_HOME/bin/asadmin start-domain
$GF_HOME/bin/asadmin deploy target/rest-exceptions.war

Running tests
======================
Deploy the application
Run mvn clean verify (DO NOT run the ftest profile!)
