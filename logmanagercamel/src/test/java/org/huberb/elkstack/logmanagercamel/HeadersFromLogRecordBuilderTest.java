/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author berni3
 */
public class HeadersFromLogRecordBuilderTest {

    private HeadersFromLogRecordBuilder instance;

    public HeadersFromLogRecordBuilderTest() {
    }

    @Before
    public void setUp() {
        instance = new HeadersFromLogRecordBuilder();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLogRecordBuild_edge_null() {
        LogRecord logRecord = null;
        Map<String, Object> m = instance.logRecord(logRecord).build();
        assertNotNull(m);
        assertEquals(0, m.size());
    }

    @Test
    public void testLogRecordBuild_edge_minimalLogRecord() {
        LogRecord logRecord = new LogRecord(Level.INFO, "someMessage");
        Map<String, Object> m = instance.logRecord(logRecord).build();
        assertNotNull(m);
        assertTrue(m.size() > 0);
    }

    @Test
    public void testLogRecordBuild() {
        LogRecord logRecord = new LogRecord(Level.INFO, "someMessage");
        logRecord.setLoggerName("loggerName");
        logRecord.setSequenceNumber(7L);
        logRecord.setMillis(13L);
        logRecord.setThreadID(23);

        Map<String, Object> m = instance.logRecord(logRecord).build();

        assertEquals("INFO", m.get(HeadersFromLogRecordBuilder.LEVEL));
        assertEquals("loggerName", m.get(HeadersFromLogRecordBuilder.LOGGER_NAME));
        assertEquals("13", m.get(HeadersFromLogRecordBuilder.MILLIS));
        assertEquals("someMessage", m.get(HeadersFromLogRecordBuilder.MESSAGE));
        assertEquals("7", m.get(HeadersFromLogRecordBuilder.SEQUENCE_NUMBER));
        assertEquals("", m.get(HeadersFromLogRecordBuilder.SOURCE_CLASS_NAME));
        assertEquals("", m.get(HeadersFromLogRecordBuilder.SOURCE_METHOD_NAME));
        assertEquals("23", m.get(HeadersFromLogRecordBuilder.THREAD_ID));
        assertEquals("", m.get(HeadersFromLogRecordBuilder.THROWN));
    }

}
