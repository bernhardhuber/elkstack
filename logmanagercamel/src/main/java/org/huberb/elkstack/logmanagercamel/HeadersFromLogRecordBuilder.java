/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.LogRecord;

/**
 *
 * @author berni3
 */
class HeadersFromLogRecordBuilder {

    private LogRecord logRecord;

    HeadersFromLogRecordBuilder logRecord(LogRecord logRecord) {
        this.logRecord = logRecord;
        return this;
    }

    Map<String, Object> build() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("level", Objects.toString(logRecord.getLevel(), ""));
        headers.put("loggerName", Objects.toString(logRecord.getLoggerName(), ""));
        headers.put("millis", Objects.toString(logRecord.getMillis(), ""));
        headers.put("message", Objects.toString(logRecord.getMessage(), ""));
        headers.put("sequenceNumber", Objects.toString(logRecord.getSequenceNumber(), ""));
        headers.put("sourceClassName", Objects.toString(logRecord.getSourceClassName(), ""));
        headers.put("sourceMethodName", Objects.toString(logRecord.getSourceMethodName(), ""));
        headers.put("threadID", Objects.toString(logRecord.getThreadID(), ""));
        headers.put("thrown", Objects.toString(logRecord.getThrown(), ""));
        return headers;
    }

}
