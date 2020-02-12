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
 * A simple csv report for reporting {@link Record}s.
 * 
 * @author berni3
 */
public class CsvMorphlineRecordReporter {

    private final List<String> keywords;
    private String fieldDelim = ";";
    private String valueDelim = "\"";
    private String lineDelim = System.lineSeparator();

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
            final StringBuilder sb = createCsvHeader();
            w.append(sb);
        }
        final StringBuilder sb = createCsvLineFromRecord(r);
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
        final StringBuilder sb = createCsvLineFromRecord(r);
        ps.print(sb.toString());
    }

    protected StringBuilder createCsvHeader() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            String key = keywords.get(i);
            String v = key;
            v = sanitizeValue(v);
            if (i > 0) {
                sb.append(fieldDelim);
            }
            appendField(sb, v);
        }
        sb.append(lineDelim);
        return sb;
    }

    protected StringBuilder createCsvLineFromRecord(Record r) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            final String key = keywords.get(i);
            final String v = (String) r.getFirstValue(key);
            if (i > 0) {
                sb.append(fieldDelim);
            }
            appendField(sb, v);
        }
        sb.append(lineDelim);
        return sb;
    }

    StringBuilder appendField(StringBuilder sb, String v) {
        v = sanitizeValue(v);
        if (valueDelim != null) {
            sb.append(valueDelim);
            sb.append(v);
            sb.append(valueDelim);
        } else {
            sb.append(v);
        }
        return sb;
    }

    String sanitizeValue(String v) {
        if (v == null) {
            v = "";
        }
        if (valueDelim != null) {
            if (v.contains(valueDelim)) {
                v = v.replace(valueDelim, valueDelim + valueDelim);
            }
        } else if (lineDelim != null) {
            if (v.contains(lineDelim)) {
                v = v.replace(lineDelim, lineDelim + lineDelim);
            }
        }
        return v;
    }
}
