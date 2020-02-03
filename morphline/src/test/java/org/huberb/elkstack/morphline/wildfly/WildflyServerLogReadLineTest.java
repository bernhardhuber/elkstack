/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.morphline.wildfly;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.huberb.elkstack.morphline.JunitCommand;
import org.huberb.elkstack.morphline.Morphline;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

/**
 *
 * @author berni3
 */
public class WildflyServerLogReadLineTest {

    private JunitCommand junitCommand;

    public WildflyServerLogReadLineTest() {
    }

    @Before
    public void setUp() {
        junitCommand = new JunitCommand(null);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testServerLog_ServerLogReadLine_lines() throws IOException {
        String config = "morphline/wildfly/server_log_readLine.conf";
        final Morphline.MorphlineCommandBuilder morphlineCommandBuilder = new Morphline.MorphlineCommandBuilder()
                .finalCommand(junitCommand)
                .configAsResource(config);
        final Command command = morphlineCommandBuilder.build();
        final Morphline.MorphlineSimpleProcessor morphlineSimpleProcessor = new Morphline.MorphlineSimpleProcessor(command);

        final String[][] lines = {
            new String[]{
                "2019-06-23 05:49:28,158 INFO  [org.jboss.as.connector.deployers.jdbc] (MSC service thread 1-3) WFLYJCA0019: Stopped Driver service with driver-name = csvcompare.war_org.h2.Driver_1_4\n",
                "2019-06-23 05:49:28,158",
                "INFO",
                "org.jboss.as.connector.deployers.jdbc",
                "MSC service thread 1-3",
                "WFLYJCA0019: Stopped Driver service with driver-name = csvcompare.war_org.h2.Driver_1_4",},
            new String[]{
                "2019-06-23 05:49:28,158 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 69) WFLYUT0022: Unregistered web context: /csvcompare\n",
                "2019-06-23 05:49:28,158",
                "INFO",
                "org.wildfly.extension.undertow",
                "ServerService Thread Pool -- 69",
                "WFLYUT0022: Unregistered web context: /csvcompare"
            },
            new String[]{
                "2019-06-23 05:49:28,252 INFO  [org.jboss.as.clustering.infinispan] (ServerService Thread Pool -- 75) WFLYCLINF0003: Stopped client-mappings cache from ejb container",
                "2019-06-23 05:49:28,252",
                "INFO",
                "org.jboss.as.clustering.infinispan",
                "ServerService Thread Pool -- 75",
                "WFLYCLINF0003: Stopped client-mappings cache from ejb container"
            },
            new String[]{
                "2019-06-23 05:49:28,252 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-3) WFLYSRV0028: Stopped deployment csvcompare.war (runtime-name: csvcompare.war) in 103ms",
                "2019-06-23 05:49:28,252",
                "INFO",
                "org.jboss.as.server.deployment",
                "MSC service thread 1-3",
                "WFLYSRV0028: Stopped deployment csvcompare.war (runtime-name: csvcompare.war) in 103ms",},
            new String[]{
                "2019-06-23 05:49:28,520 WARN  [org.jboss.as.controller] (DeploymentScanner-threads - 2) WFLYCTL0357: Notification of type deployment-undeployed is not described for the resource at the address []",
                "2019-06-23 05:49:28,520",
                "WARN",
                "org.jboss.as.controller",
                "DeploymentScanner-threads - 2",
                "WFLYCTL0357: Notification of type deployment-undeployed is not described for the resource at the address []",},
            new String[]{
                "2019-06-23 05:49:28,520 INFO  [org.jboss.as.server] (DeploymentScanner-threads - 2) WFLYSRV0009: Undeployed \"csvcompare.war\" (runtime-name: \"csvcompare.war\")",
                "2019-06-23 05:49:28,520",
                "INFO",
                "org.jboss.as.server",
                "DeploymentScanner-threads - 2",
                "WFLYSRV0009: Undeployed \"csvcompare.war\" (runtime-name: \"csvcompare.war\")",},
            new String[]{
                "2019-06-23 05:49:33,536 INFO  [org.jboss.as.server.deployment.scanner] (DeploymentScanner-threads - 1) WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",
                "2019-06-23 05:49:33,536",
                "INFO",
                "org.jboss.as.server.deployment.scanner",
                "DeploymentScanner-threads - 1",
                "WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",},
            new String[]{
                "2019-06-23 06:10:07,464 INFO  [org.jboss.as.server.deployment.scanner] (DeploymentScanner-threads - 1) WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",
                "2019-06-23 06:10:07,464",
                "INFO",
                "org.jboss.as.server.deployment.scanner",
                "DeploymentScanner-threads - 1",
                "WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",},
            new String[]{
                "2019-06-23 06:16:13,546 INFO  [org.jboss.as.server.deployment.scanner] (DeploymentScanner-threads - 1) WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",
                "2019-06-23 06:16:13,546",
                "INFO",
                "org.jboss.as.server.deployment.scanner",
                "DeploymentScanner-threads - 1",
                "WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",},
            new String[]{
                "2019-06-23 06:32:41,549 INFO  [org.jboss.as.server.deployment.scanner] (DeploymentScanner-threads - 2) WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",
                "2019-06-23 06:32:41,549",
                "INFO",
                "org.jboss.as.server.deployment.scanner",
                "DeploymentScanner-threads - 2",
                "WFLYDS0004: Found csvcompare.war in deployment directory. To trigger deployment create a file called csvcompare.war.dodeploy",},
            new String[]{
                "2019-06-23 06:32:46,586 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-1) WFLYSRV0027: Starting deployment of \"csvcompare.war\" (runtime-name: \"csvcompare.war\")",
                "2019-06-23 06:32:46,586",
                "INFO",
                "org.jboss.as.server.deployment",
                "MSC service thread 1-1",
                "WFLYSRV0027: Starting deployment of \"csvcompare.war\" (runtime-name: \"csvcompare.war\")"
            },
            new String[]{
                "2019-06-23 06:32:47,052 INFO  [org.jboss.weld.deployer] (MSC service thread 1-8) WFLYWELD0003: Processing weld deployment csvcompare.war",
                "2019-06-23 06:32:47,052",
                "INFO",
                "org.jboss.weld.deployer",
                "MSC service thread 1-8",
                "WFLYWELD0003: Processing weld deployment csvcompare.war",}

        };
        for (int i = 0; i < lines.length; i++) {
            final String m = "" + i;
            final String line = lines[i][0];
            final String expectedTimestamp = lines[i][1];
            final String expectedSeverity = lines[i][2];
            final String expectedCategory = lines[i][3];
            final String expectedThread = lines[i][4];
            final String expectedLogmessage = lines[i][5];
            try (InputStream is = new ByteArrayInputStream(line.getBytes())) {
                morphlineSimpleProcessor.process(is);
            }

            final Record junitCommandRecord = junitCommand.peekRecordFromIndex(i);

            assertNotNull(m, junitCommandRecord);

            assertEquals(m, line.trim(), junitCommandRecord.getFirstValue("message"));
            assertEquals(m, expectedTimestamp, junitCommandRecord.getFirstValue("timestamp"));
            assertEquals(m, expectedSeverity, junitCommandRecord.getFirstValue("severity"));
            assertEquals(m, expectedCategory, junitCommandRecord.getFirstValue("category"));
            assertEquals(m, expectedThread, junitCommandRecord.getFirstValue("thread"));
            assertEquals(m, expectedLogmessage, junitCommandRecord.getFirstValue("logmessage"));
        }
        assertEquals(12, junitCommand.getRecordList().size());
    }

}
