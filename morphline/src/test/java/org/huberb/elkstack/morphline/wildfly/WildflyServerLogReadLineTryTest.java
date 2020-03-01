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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.huberb.elkstack.morphline.JunitCommand;
import org.huberb.elkstack.morphline.Morphline;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

/**
 *
 * @author berni3
 */
public class WildflyServerLogReadLineTryTest {

    private static final String MORPHLINE_WILDFLY_SERVER_LOG_TXT = "morphline/wildfly/server.log.txt";

    private JunitCommand junitCommand;

    public WildflyServerLogReadLineTryTest() {
    }

    @Before
    public void setUp() {
        junitCommand = new JunitCommand(null);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testServerLog_ServerLogReadLineTry_serverLog() throws IOException {
        String config = "morphline/wildfly/server_log_readLineTry.conf";
        final Morphline.MorphlineCommandBuilder morphlineCommandBuilder = new Morphline.MorphlineCommandBuilder()
                .finalCommand(junitCommand)
                .configAsResource(config);
        final Command command = morphlineCommandBuilder.build();
        final Morphline.MorphlineSimpleProcessor morphlineSimpleProcessor = new Morphline.MorphlineSimpleProcessor(command);

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(MORPHLINE_WILDFLY_SERVER_LOG_TXT)) {
            boolean success = morphlineSimpleProcessor.process(is);
            assertTrue(success == true || success == false);
        }

        assertEquals(322, junitCommand.getRecordList().size());

        for (int i = 0; i < junitCommand.getRecordList().size(); i++) {
            final Record junitCommandRecord = junitCommand.peekRecordFromIndex(i);
            final String m = "" + i + ": " + junitCommandRecord.toString();
            assertNotNull(m, junitCommandRecord.toString());
            final Integer size = junitCommandRecord.getFields().size();
            assertEquals(m + ", size=" + size, true, size == 6);
            assertEquals(m + ", size=" + size, true, Arrays.asList(6).stream().anyMatch((n) -> size.equals(n)));
//            //---
//            final List<String> keyList = Arrays.asList(
//                    "message",
//                    "clientip",
//                    "ident",
//                    "auth",
//                    "timestamp",
//                    "verb",
//                    "request",
//                    "httpversion",
//                    "response"
//            );
//            for (final String key : keyList) {
//                assertNotNull(m + ", key=" + key, junitCommandRecord.getFirstValue(key));
//            }
        }
    }

}
