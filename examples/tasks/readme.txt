Seam Tasks Example
==============================================================


Deploying to Glassfish
======================
mvn clean package
$GF_HOME/bin/asadmin start-database
$GF_HOME/bin/asadmin start-domain
$GF_HOME/bin/asadmin deploy target/seam-tasks.war


Deploying to JBoss AS 6
======================
mvn clean package -Pjboss-as
cp target/seam-tasks.war $JBOSS_HOME/server/default/deploy
$JBOSS_HOME/bin/run.sh -c default


Deploying to Resin
======================
mvn clean package -Presin
cp target/seam-tasks.war $RESIN_HOME/webapps
$RESIN_HOME/bin/resin.sh start