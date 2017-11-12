// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.log4j.logger;

import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerUdpVaryStressIT
{
    private Logger instance;
    private int maxSize;
    
    public Log4jGelfLoggerUdpVaryStressIT() {
        this.maxSize = 16384;
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }
    
    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().host(new Configuration().getUdpHost()).port(new Configuration().getUdpPort()).category(Log4jGelfLoggerUdpVaryStressIT.class.getName()).build();
    }
    
    @After
    public void tearDown() {
        this.instance.removeAllAppenders();
    }
    
    @Test
    public void testSubmitMessage_10_TheQuickBrownFox() {
        for (int i = 0; i < 10; ++i) {
            final String message = new GeneratorVaryingString().generateVaryWords(new StringTemplates().getTheQuickBrownFoxTemplate());
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, this.maxSize);
            this.instance.info((Object)m);
        }
    }
    
    @Test
    public void testSubmitMessage_10_LoremIpsum() {
        for (int i = 0; i < 10; ++i) {
            final String message = new GeneratorVaryingString().generateVaryWords(new StringTemplates().getLoremIpsumTemplate());
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, this.maxSize);
            this.instance.info((Object)m);
        }
    }
    
    @Test
    public void testSubmitMessage_10_AtVero() {
        for (int i = 0; i < 10; ++i) {
            final String message = new GeneratorVaryingString().generateVaryWords(new StringTemplates().getAtVeroTemplate());
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, this.maxSize);
            this.instance.info((Object)m);
        }
    }
}
