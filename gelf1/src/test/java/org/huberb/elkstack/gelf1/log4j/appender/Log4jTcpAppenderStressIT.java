// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.log4j.appender;

import org.junit.Test;
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import java.net.MalformedURLException;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import biz.paluch.logging.gelf.log4j.GelfLayout;
import org.huberb.elkstack.gelf1.Configuration;
import org.apache.log4j.Logger;

public class Log4jTcpAppenderStressIT
{
    private Logger logger;
    private Log4jTcpAppender instance;
    
    @Before
    public void setUp() throws MalformedURLException {
        (this.instance = new Log4jTcpAppender()).setHost(new Configuration().getTcpHost());
        this.instance.setPort(new Configuration().getTcpPort());
        this.instance.setLayout((Layout)new GelfLayout());
        this.instance.activateOptions();
        (this.logger = Logger.getLogger((Class)this.getClass())).addAppender((Appender)this.instance);
    }
    
    @After
    public void tearDown() {
        this.logger.removeAllAppenders();
    }
    
    @Test
    public void testSubmitMessage_10_1028() {
        final String message = new StringTemplates().getTheQuickBrownFoxTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 1028);
            this.logger.info((Object)m);
        }
    }
    
    @Test
    public void testSubmitMessage_10_8192() {
        final String message = new StringTemplates().getAtVeroTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 8192);
            this.logger.info((Object)m);
        }
    }
}
