// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.log4j.logger;

import org.junit.Test;
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerUdpStressIT
{
    private Logger instance;
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }
    
    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().host(new Configuration().getUdpHost()).port(new Configuration().getUdpPort()).category(Log4jGelfLoggerUdpStressIT.class.getName()).build();
    }
    
    @After
    public void tearDown() {
        this.instance.removeAllAppenders();
    }
    
    @Test
    public void testSubmitMessage_10_1028() {
        final String message = new StringTemplates().getTheQuickBrownFoxTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 1028);
            this.instance.info((Object)m);
        }
    }
    
    @Test
    public void testSubmitMessage_10_8192() {
        final String message = new StringTemplates().getAtVeroTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 8192);
            this.instance.info((Object)m);
        }
    }
}
