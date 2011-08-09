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
To use Apache Velocity instead of FreeMarker, use -Pvelociry (e.g. mvn clean package -Pvelocity)
Build options can be combined and used during functional testsuite execution.

Running functional test from command line
======================
build & deploy the application (using steps above)
mvn verify -Pftest

Running functional tests from Eclipse
======================
You can run the functional tests directly from Eclipse. 
Firstly,start the selenium server.

java -jar ~/.m2/repository/org/seleniumhq/selenium/server/selenium-server/1.0.3/selenium-server-1.0.3-standalone.jar -port 14444

Then, run the test using Eclipse TestNG plugin (e.g. TaskPageTest).
It will fail at the first run. Modify the run configuration of the test
TaskPageTest -> Run As -> Run Configurations and add the following VM 
arguments in the arguments tab:

-Dmethod=* -Dbrowser=*firefoxproxy -Dcontext.root=http://localhost:8080/ -Dcontext.path=/rest-tasks/ -Dselenium.host=localhost -Dselenium.port=14444 -Dselenium.debug=false -Dselenium.maximize=false -Dselenium.timeout.default=30000 -Dselenium.timeout.gui=5000 -Dselenium.timeout.ajax=15000 -Dselenium.timeout.model=30000 -Dselenium.speed=0 -Dselenium.timeout=3000 -Dbasedir=.

Note that you need to add the arguments for every test class.
