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

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

/**
 *
 * @author berni3
 */
public class CsvMorphlineRecordReporterCommand implements Command {

    private final Command parent;
    private final CsvMorphlineRecordReporter csvMorphlineRecordReporter;

    public CsvMorphlineRecordReporterCommand(Command parent) {
        this.parent = parent;
        List<String> keywords = Arrays.asList(
                "timestamp",
                "severity",
                "category",
                "thread",
                "logmessage"
        );
        this.csvMorphlineRecordReporter = new CsvMorphlineRecordReporter(keywords);
    }

    @Override
    public void notify(Record notification) {

    }

    @Override
    public boolean process(Record record) {
        PrintStream ps = System.out;
        try {
            csvMorphlineRecordReporter.reportRecordToPrintStream(ps, record);
            return true;
        } catch (IOException ioex) {
            return false;
        }
    }

    @Override
    public Command getParent() {
        return this.parent;
    }

}
