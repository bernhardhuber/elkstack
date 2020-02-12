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
package org.huberb.elkstack.morphline;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.huberb.elkstack.morphline.Morphline.MorphlineCommandBuilder;
import org.huberb.elkstack.morphline.Morphline.MorphlineSimpleProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kitesdk.morphline.api.Command;

/**
 *
 * @author berni3
 */
public class MorphlineSyslogTest {

    public MorphlineSyslogTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSyslogInline() throws IOException {

        String config = ""
                + "morphlines : [\n"
                + "  {\n"
                + "    id : morphline1\n "
                + "    commands : [\n"
                + "      {\n"
                + "        # Parse input attachment and emit a record for each input line                \n"
                + "        readLine {\n"
                + "          charset : UTF-8\n"
                + "        }\n"
                + "      }\n"
                + "\n"
                + "      {\n"
                + "        grok {\n"
                + "          # Consume the output record of the previous command and pipe another\n"
                + "          # record downstream.\n"
                + "          #\n"
                + "          # A grok-dictionary is a config file that contains prefabricated\n"
                + "          # regular expressions that can be referred to by name. grok patterns\n"
                + "          # specify such a regex name, plus an optional output field name.\n"
                + "          # The syntax is %{REGEX_NAME:OUTPUT_FIELD_NAME}\n"
                + "          # The input line is expected in the \"message\" input field.\n"
                + "          dictionaryFiles : [src/test/resources/grok-dictionaries]\n"
                + "          expressions : {\n"
                + "            message : \"\"\"<%{POSINT:priority}>%{SYSLOGTIMESTAMP:timestamp} %{SYSLOGHOST:hostname} %{DATA:program}(?:\\[%{POSINT:pid}\\])?: %{GREEDYDATA:msg}\"\"\"\n"
                + "          }\n"
                + "        }\n"
                + "      }\n"
                + "\n"
                + "      # log the record at INFO level to SLF4J\n"
                + "      { logInfo { format : \"output record: {}\", args : [\"@{}\"] } }\n"
                + "    ]\n"
                + "  }\n"
                + "]";
        final MorphlineCommandBuilder b = new MorphlineCommandBuilder().configAsString(config);
        final Command command = b.build();

        final MorphlineSimpleProcessor processor = new MorphlineSimpleProcessor(command);

        String lines = "<164>Feb  4 10:46:14 syslog sshd[607]: listening on 0.0.0.0 port 22.";
        try (InputStream is = new ByteArrayInputStream(lines.getBytes())) {
            processor.process(is);
        }
    }
}
