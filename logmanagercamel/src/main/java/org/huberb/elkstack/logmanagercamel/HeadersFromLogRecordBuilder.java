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
 * Builder for createing header hashmap from a {@link LogRecord}.
 *
 * @author berni3
 */
class HeadersFromLogRecordBuilder {

    static final String THROWN = "thrown";
    static final String THREAD_ID = "threadID";
    static final String SOURCE_METHOD_NAME = "sourceMethodName";
    static final String SOURCE_CLASS_NAME = "sourceClassName";
    static final String SEQUENCE_NUMBER = "sequenceNumber";
    static final String MESSAGE = "message";
    static final String MILLIS = "millis";
    static final String LOGGER_NAME = "loggerName";
    static final String LEVEL = "level";

    private LogRecord logRecord;

    HeadersFromLogRecordBuilder logRecord(LogRecord logRecord) {
        this.logRecord = logRecord;
        return this;
    }

    Map<String, Object> build() {
        Map<String, Object> headers = new HashMap<>();
        if (logRecord != null) {
            headers.put(LEVEL, Objects.toString(logRecord.getLevel(), ""));
            headers.put(LOGGER_NAME, Objects.toString(logRecord.getLoggerName(), ""));
            headers.put(MILLIS, Objects.toString(logRecord.getMillis(), ""));
            headers.put(MESSAGE, Objects.toString(logRecord.getMessage(), ""));
            headers.put(SEQUENCE_NUMBER, Objects.toString(logRecord.getSequenceNumber(), ""));
            headers.put(SOURCE_CLASS_NAME, Objects.toString(logRecord.getSourceClassName(), ""));
            headers.put(SOURCE_METHOD_NAME, Objects.toString(logRecord.getSourceMethodName(), ""));
            headers.put(THREAD_ID, Objects.toString(logRecord.getThreadID(), ""));
            headers.put(THROWN, Objects.toString(logRecord.getThrown(), ""));
        }
        return headers;
    }

}
