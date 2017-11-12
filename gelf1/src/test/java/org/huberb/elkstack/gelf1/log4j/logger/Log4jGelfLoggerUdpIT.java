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

public class Log4jGelfLoggerUdpIT
{
    private Logger instance;
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }
    
    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().host(new Configuration().getUdpHost()).port(new Configuration().getUdpPort()).category(Log4jGelfLoggerUdpIT.class.getName()).build();
    }
    
    @After
    public void tearDown() {
        this.instance.removeAllAppenders();
    }
    
    @Test
    public void testHelloWorld() {
        final String message = new StringTemplates().getHelloWorldTemplate();
        this.instance.info((Object)message);
    }
}
