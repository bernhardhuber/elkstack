/*
 * Copyright 2020 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class WildflyServerLogReadMultiLineTest {

    private JunitCommand junitCommand;

    public WildflyServerLogReadMultiLineTest() {
    }

    @Before
    public void setUp() {
        junitCommand = new JunitCommand(null);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testServerLog_ServerLogReadMultiLine_lines() throws IOException {
        String config = "morphline/wildfly/server_log_readMultiLine.conf";
        final Morphline.MorphlineCommandBuilder morphlineCommandBuilder = new Morphline.MorphlineCommandBuilder()
                .finalCommand(junitCommand)
                .configAsResource(config);
        final Command command = morphlineCommandBuilder.build();
        final Morphline.MorphlineSimpleProcessor morphlineSimpleProcessor = new Morphline.MorphlineSimpleProcessor(command);

        final String[][] lines = {
            new String[]{
                "2019-06-23 06:32:47,077 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-8) WFLYEJB0473: JNDI bindings for session bean named 'CsvQuery' in deployment unit 'deployment \"csvcompare.war\"' are as follows:\n"
                + "\n"
                + "	java:global/csvcompare/CsvQuery!org.huberb.csvcompare.war.service.CsvQuery\n"
                + "	java:app/csvcompare/CsvQuery!org.huberb.csvcompare.war.service.CsvQuery\n"
                + "	java:module/CsvQuery!org.huberb.csvcompare.war.service.CsvQuery\n"
                + "	java:global/csvcompare/CsvQuery\n"
                + "	java:app/csvcompare/CsvQuery\n"
                + "	java:module/CsvQuery\n"
                + "\n",
                //
                "2019-06-23 06:32:47,077",
                "INFO",
                "org.jboss.as.ejb3.deployment",
                "MSC service thread 1-8",
                "WFLYEJB0473: JNDI bindings for session bean named 'CsvQuery' in deployment unit 'deployment \"csvcompare.war\"' are as follows:\n"
                + "\n"
                + "	java:global/csvcompare/CsvQuery!org.huberb.csvcompare.war.service.CsvQuery\n"
                + "	java:app/csvcompare/CsvQuery!org.huberb.csvcompare.war.service.CsvQuery\n"
                + "	java:module/CsvQuery!org.huberb.csvcompare.war.service.CsvQuery\n"
                + "	java:global/csvcompare/CsvQuery\n"
                + "	java:app/csvcompare/CsvQuery\n"
                + "	java:module/CsvQuery\n"

            }
        };
        final int i = 0;
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

        assertEquals(m, line.trim(), String.valueOf(junitCommandRecord.getFirstValue("message")).trim());
        assertEquals(m, expectedTimestamp, junitCommandRecord.getFirstValue("timestamp"));
        assertEquals(m, expectedSeverity, junitCommandRecord.getFirstValue("severity"));
        assertEquals(m, expectedCategory, junitCommandRecord.getFirstValue("category"));
        assertEquals(m, expectedThread, junitCommandRecord.getFirstValue("thread"));
        assertEquals(m, expectedLogmessage, junitCommandRecord.getFirstValue("logmessage"));

        assertEquals(1, junitCommand.getRecordList().size());
    }

}
