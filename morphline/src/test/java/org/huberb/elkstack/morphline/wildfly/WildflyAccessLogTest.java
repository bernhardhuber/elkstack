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
import java.util.Arrays;
import java.util.List;
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
public class WildflyAccessLogTest {

    private static final String MORPHLINE_WILDFLY_ACCESSLOG_LOG_TXT = "morphline/wildfly/access_log.log.txt";

    private JunitCommand junitCommand;

    public WildflyAccessLogTest() {
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
        String config = "morphline/wildfly/access_log_readLine.conf";
        final Morphline.MorphlineCommandBuilder morphlineCommandBuilder = new Morphline.MorphlineCommandBuilder()
                .finalCommand(junitCommand)
                .configAsResource(config);
        final Command command = morphlineCommandBuilder.build();
        final Morphline.MorphlineSimpleProcessor morphlineSimpleProcessor = new Morphline.MorphlineSimpleProcessor(command);

        final String[][] lines = {
            new String[]{
                "127.0.0.1 - - [23/Jun/2019:06:32:50 +0200] \"GET /csvcompare/csvQuery HTTP/1.1\" 200 1275\n",
                "127.0.0.1",
                "-",
                "-",
                "23/Jun/2019:06:32:50 +0200",
                "GET",
                "/csvcompare/csvQuery",
                "1.1",
                "200",
                "1275",},
            new String[]{
                "127.0.0.1 - - [23/Jun/2019:06:32:50 +0200] \"GET /csvcompare/csvQuery HTTP/1.1\" 200 1275\n",
                "127.0.0.1",
                "-",
                "-",
                "23/Jun/2019:06:32:50 +0200",
                "GET",
                "/csvcompare/csvQuery",
                "1.1",
                "200",
                "1275",}
        };
        for (int i = 0; i < lines.length; i++) {
            String m = "" + i;
            String line = lines[i][0];

            String clientip = lines[i][1];
            String ident = lines[i][2];
            String auth = lines[i][3];
            String timestamp = lines[i][4];
            String verb = lines[i][5];
            String request = lines[i][6];
            String rawrequest = null;
            String httpversion = lines[i][7];
            String response = lines[i][8];
            String bytes = lines[i][9];
            String referrer = null;
            String agent = null;

            try (InputStream is = new ByteArrayInputStream(line.getBytes())) {
                boolean success = morphlineSimpleProcessor.process(is);
                assertEquals(m, true, success);
            }

            final Record junitCommandRecord = junitCommand.peekRecordFromIndex(i);

            assertNotNull(m, junitCommandRecord);

            assertEquals(m, line.trim(), junitCommandRecord.getFirstValue("message"));

            assertEquals(m, clientip, junitCommandRecord.getFirstValue("clientip"));
            assertEquals(m, ident, junitCommandRecord.getFirstValue("ident"));
            assertEquals(m, auth, junitCommandRecord.getFirstValue("auth"));
            assertEquals(m, timestamp, junitCommandRecord.getFirstValue("timestamp"));
            assertEquals(m, verb, junitCommandRecord.getFirstValue("verb"));
            assertEquals(m, request, junitCommandRecord.getFirstValue("request"));
            assertEquals(m, httpversion, junitCommandRecord.getFirstValue("httpversion"));
            assertEquals(m, rawrequest, junitCommandRecord.getFirstValue("rawrequest"));
            assertEquals(m, response, junitCommandRecord.getFirstValue("response"));
            assertEquals(m, bytes, junitCommandRecord.getFirstValue("bytes"));
            assertEquals(m, referrer, junitCommandRecord.getFirstValue("referrer"));
            assertEquals(m, agent, junitCommandRecord.getFirstValue("agent"));
        }
        assertEquals(2, junitCommand.getRecordList().size());
    }

    @Test
    public void testServerLog_ServerLogReadLine_accessLog() throws IOException {
        String config = "morphline/wildfly/access_log_readLine.conf";
        final Morphline.MorphlineCommandBuilder morphlineCommandBuilder = new Morphline.MorphlineCommandBuilder()
                .finalCommand(junitCommand)
                .configAsResource(config);
        final Command command = morphlineCommandBuilder.build();
        final Morphline.MorphlineSimpleProcessor morphlineSimpleProcessor = new Morphline.MorphlineSimpleProcessor(command);

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(MORPHLINE_WILDFLY_ACCESSLOG_LOG_TXT)) {
            boolean success = morphlineSimpleProcessor.process(is);
            assertEquals(true, success);
        }

        assertEquals(183, junitCommand.getRecordList().size());

        for (int i = 0; i < junitCommand.getRecordList().size(); i++) {
            final Record junitCommandRecord = junitCommand.peekRecordFromIndex(i);
            final String m = "" + i + ": " + junitCommandRecord.toString();
            assertNotNull(m, junitCommandRecord.toString());
            final Integer size = junitCommandRecord.getFields().size();
            assertEquals(m + ", size=" + size, true, size == 9 || size == 10);
            assertEquals(m + ", size=" + size, true, Arrays.asList(9, 10).stream().anyMatch((n) -> size.equals(n)));
            //---
            final List<String> keyList = Arrays.asList(
                    "message",
                    "clientip",
                    "ident",
                    "auth",
                    "timestamp",
                    "verb",
                    "request",
                    "httpversion",
                    "response"
            );
            for (final String key : keyList) {
                assertNotNull(m + ", key=" + key, junitCommandRecord.getFirstValue(key));
            }
        }
    }

}
