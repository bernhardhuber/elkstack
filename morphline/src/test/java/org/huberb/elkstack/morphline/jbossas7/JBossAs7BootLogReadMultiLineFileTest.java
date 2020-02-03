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
package org.huberb.elkstack.morphline.jbossas7;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import org.huberb.elkstack.morphline.CsvMorphlineRecordReporter;
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
public class JBossAs7BootLogReadMultiLineFileTest {

    private JunitCommand junitCommand;

    public JBossAs7BootLogReadMultiLineFileTest() {
    }

    @Before
    public void setUp() {
        junitCommand = new JunitCommand(null);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBootLog_ServerLogReadMultiLine_bootLog() throws IOException {
        String config = "morphline/jbossas7/boot_log_readMultiLine.conf";
        final Morphline.MorphlineCommandBuilder morphlineCommandBuilder = new Morphline.MorphlineCommandBuilder()
                .finalCommand(junitCommand)
                .configAsResource(config);
        final Command command = morphlineCommandBuilder.build();
        final Morphline.MorphlineSimpleProcessor morphlineSimpleProcessor = new Morphline.MorphlineSimpleProcessor(command);

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("morphline/jbossas7/boot.log")) {
            boolean success = morphlineSimpleProcessor.process(is);
            assertTrue(success == true);
        }

        assertEquals(27, junitCommand.getRecordList().size());

        final List<String> keywordList = Arrays.asList("timestamp",
                "severity",
                "category",
                "logmessage"
        );
        CsvMorphlineRecordReporter csvMorphlineRecordReporter = new CsvMorphlineRecordReporter(keywordList);
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        csvMorphlineRecordReporter.reportRecordToWriter(osw, junitCommand.getRecordList());
        osw.flush();

        for (int i = 0; i < junitCommand.getRecordList().size(); i++) {
            final Record junitCommandRecord = junitCommand.peekRecordFromIndex(i);
            final String m = "" + i + ": " + junitCommandRecord.toString();
            assertNotNull(m, junitCommandRecord.toString());
            final Integer size = junitCommandRecord.getFields().size();
            assertEquals(m + ", size=" + size, true, size == 5);
            assertEquals(m + ", size=" + size, true, Arrays.asList(5).stream().anyMatch((n) -> size.equals(n)));
            //---     
            for (final String key : keywordList) {
                assertNotNull(m + ", key=" + key, junitCommandRecord.getFirstValue(key));
            }
        }
    }

}
