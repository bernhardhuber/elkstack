// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.log4j.logger;

import org.junit.Test;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Priority;
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import java.util.Arrays;
import org.apache.log4j.Level;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerHttpLevelIT
{
    private Logger instance;
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }
    
    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().host(new Configuration().getHttpHostPort() + "/log4jgelfloggerhttplevelit").category(Log4jGelfLoggerHttpLevelIT.class.getName()).build();
    }
    
    @After
    public void tearDown() {
        this.instance.removeAllAppenders();
    }
    
    @Test
    public void testSubmitMessage_Level() {
        final String template = new StringTemplates().getLoremIpsumTemplate();
        final List<Level> allLevels = Arrays.asList(Level.OFF, Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.ALL);
        for (final Level level : allLevels) {
            for (int i = 0; i < 10; ++i) {
                final String m = new GeneratorVaryingString().generateVaryMaxSize(template, 2048);
                this.instance.log((Priority)level, (Object)m);
            }
        }
    }
}
