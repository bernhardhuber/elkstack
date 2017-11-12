// 
// Decompiled by Procyon v0.5.30
// 
package org.huberb.elkstack.gelf1.log4j.logger;

import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerHttpExceptionIT {

    private Logger instance;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }

    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().host(new Configuration().getHttpHostPort() + "/log4jgelfloggerhttpexceptionit").category(Log4jGelfLoggerHttpExceptionIT.class.getName()).build();
    }

    @After
    public void tearDown() {
        this.instance.removeAllAppenders();
    }

    @Test
    public void testException_RuntimeException() {
        final String message = new StringTemplates().getLoremIpsumTemplate();
        for (int i = 0; i < 10; i++) {
            final RuntimeException aRuntimeException = new RuntimeException("" + i + ": RuntimeException message");
            try {
                throw aRuntimeException;
            } catch (RuntimeException rtex) {
                final String m = "" + i + ": " + message;
                this.instance.info(m, rtex);
            }
        }
    }
}
