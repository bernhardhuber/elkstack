// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.log4j2;

import org.junit.Test;
import org.apache.logging.log4j.Logger;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class Log4j2Log4j2XmlIT
{
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testHelloWorld() {
        final Logger logger = LogManager.getLogger((Class)this.getClass());
        final String message = new StringTemplates().getHelloWorldTemplate();
        logger.info(message);
    }
}
