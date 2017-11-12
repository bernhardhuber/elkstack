// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.log4j.logger;

import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import biz.paluch.logging.gelf.log4j.GelfLayout;

public class Log4jGelfLayoutIT
{
    private GelfLayout instance;
    private Logger logger;
    
    @Before
    public void setUp() {
        this.instance = new GelfLog4jLayoutBuilder().build();
        final ConsoleAppender consoleAppender = new ConsoleAppender((Layout)this.instance);
        (this.logger = Logger.getLogger((Class)this.getClass())).addAppender((Appender)consoleAppender);
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
