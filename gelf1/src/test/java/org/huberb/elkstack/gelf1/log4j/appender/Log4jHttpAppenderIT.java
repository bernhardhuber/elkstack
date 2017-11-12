// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.log4j.appender;

import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import java.net.MalformedURLException;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import biz.paluch.logging.gelf.log4j.GelfLayout;
import org.huberb.elkstack.gelf1.Configuration;
import org.apache.log4j.Logger;

public class Log4jHttpAppenderIT
{
    private Logger logger;
    private Log4jHttpAppender instance;
    
    @Before
    public void setUp() throws MalformedURLException {
        (this.instance = new Log4jHttpAppender()).setUrl(new Configuration().getHttpHostPort() + "/log4jhttpappenderit");
        this.instance.setLayout((Layout)new GelfLayout());
        this.instance.activateOptions();
        (this.logger = Logger.getLogger((Class)this.getClass())).addAppender((Appender)this.instance);
    }
    
    @After
    public void tearDown() {
        this.logger.removeAllAppenders();
    }
    
    @Test
    public void testHelloWorld() {
        final String message = new StringTemplates().getHelloWorldTemplate();
        this.logger.info((Object)message);
    }
}
