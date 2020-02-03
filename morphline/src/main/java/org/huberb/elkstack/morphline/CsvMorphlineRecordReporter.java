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
import java.io.Writer;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.kitesdk.morphline.api.Record;

/**
 *
 * @author berni3
 */
public class CsvMorphlineRecordReporter {

    private final List<String> keywords;
    private String FIELD_DELIM = ";";
    private String VALUE_DELIM = "\"";
    private String LINE_DELIM = System.lineSeparator();

    private final AtomicBoolean createdCsvHeader = new AtomicBoolean(false);

    public CsvMorphlineRecordReporter(List<String> keywords) {
        this.keywords = keywords;
    }

    //--
    public void reportRecordToWriter(Writer w, List<Record> rList) throws IOException {
        for (Record r : rList) {
            reportRecordToWriter(w, r);
        }
    }

    public void reportRecordToWriter(Writer w, Record r) throws IOException {
        if (createdCsvHeader.compareAndSet(false, true)) {
            StringBuilder sb = createCsvHeader();
            w.append(sb);
        }
        StringBuilder sb = createCsvLineFromRecord(r);
        w.append(sb);
    }

    //--
    public void reportRecordToPrintStream(PrintStream ps, List<Record> rList) throws IOException {
        for (Record r : rList) {
            reportRecordToPrintStream(ps, r);
        }
    }

    public void reportRecordToPrintStream(PrintStream ps, Record r) throws IOException {
        if (createdCsvHeader.compareAndSet(false, true)) {
            final StringBuilder sb = createCsvHeader();
            ps.print(sb);
        }
        StringBuilder sb = createCsvLineFromRecord(r);
        ps.print(sb.toString());
    }

    protected StringBuilder createCsvHeader() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            String key = keywords.get(i);
            String v = key;
            v = sanitizeValue(v);
            if (i > 0) {
                sb.append(FIELD_DELIM);
            }
            appendField(sb, v);
        }
        sb.append(LINE_DELIM);
        return sb;
    }

    protected StringBuilder createCsvLineFromRecord(Record r) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            String key = keywords.get(i);
            String v = (String) r.getFirstValue(key);
            if (i > 0) {
                sb.append(FIELD_DELIM);
            }
            appendField(sb, v);
        }
        sb.append(LINE_DELIM);
        return sb;
    }

    StringBuilder appendField(StringBuilder sb, String v) {
        v = sanitizeValue(v);
        if (VALUE_DELIM != null) {
            sb.append(VALUE_DELIM);
            sb.append(v);
            sb.append(VALUE_DELIM);
        } else {
            sb.append(v);
        }
        return sb;
    }

    String sanitizeValue(String v) {
        if (v == null) {
            v = "";
        }
        if (VALUE_DELIM != null) {
            if (v.contains(VALUE_DELIM)) {
                v = v.replace(VALUE_DELIM, VALUE_DELIM + VALUE_DELIM);
            }
        } else if (LINE_DELIM != null) {
            if (v.contains(LINE_DELIM)) {
                v = v.replace(LINE_DELIM, LINE_DELIM + LINE_DELIM);
            }
        }
        return v;
    }
}
