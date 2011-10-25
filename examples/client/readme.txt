Seam REST Client Example
==============================================================

The Seam REST Client Example demonstrates the use of Seam REST together
with RESTEasy Client Framework for writing JAX-RS clients. The application
is split into two parts. 

Firstly, a client for the postal code search service is implemented.
(http://www.geonames.org/export/web-services.html)

Secondly, the application is able to gather usage statistics from a running
instance of the Seam REST Tasks example. To use this part, deploy the Tasks
application to an application server to the default location (http://localhost:8080/rest-tasks)

Running the example
===========================================
Run the example by executing "mvn package -Drun"
