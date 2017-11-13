# gelf1

This module generates various gelf-log-messages
using the project 
`biz.paluch.logging::logstash-gelf` (http://logging.paluch.biz/index.html).

The log-messages test are named `*IT.java`.

Following constellation are tested

* Send GELF-message via UDP
* Send GELF-message via TCP
* Send JSON-message via HTTP-POST

The test-configuration (Configuration.java) assumes that
a logstash configuration listens at

* udp at localhost:12201 
* http at http://localhost:12202/*
* tcp at localhost:12203

The integrations tests uses following logging-frameworks

* Datenpumpe 
* Log4j
* Log4j2
* JavaUtilLogging
* Wildfly

The integrations tests send following log-messages

* Simple "Hello world!"
* Text "The quick brown fox..", "At vero...", "Lorem ipsum"
* Level FATAL, ERROR, WARNING, DEBUG, OFF, ALL
* Exception generating a `RuntimeException` and sending via log-message

Run the integration test via maven goal `failsafe:integration-test`, 
alternativly run the test-suites, or the single IT-Tests.
