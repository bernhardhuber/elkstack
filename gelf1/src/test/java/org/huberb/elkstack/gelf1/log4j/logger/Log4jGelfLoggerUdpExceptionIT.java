// 
// Decompiled by Procyon v0.5.30
// 
package org.huberb.elkstack.gelf1.log4j.logger;

import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerUdpExceptionIT {

    private Logger instance;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }

    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().host(new Configuration().getUdpHost()).port(new Configuration().getUdpPort()).category(Log4jGelfLoggerUdpExceptionIT.class.getName()).build();
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
