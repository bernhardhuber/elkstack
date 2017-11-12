// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1.standalone;

import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import biz.paluch.logging.gelf.standalone.Datenpumpe;

public class DatenpumpeHttpMessagesSubmitterIT
{
    private Datenpumpe datenpumpe;
    
    @Before
    public void setUp() {
        this.datenpumpe = new DatenpumpeBuilder().host(new Configuration().getHttpHostPort() + "/datenpumpehttpmessagessubmitterit").specificConfiguration("version", (Object)"1.1").build();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testSubmitMessage() {
        final DatenpumpeMessagesSubmitter instance = new DatenpumpeMessagesSubmitter();
        final String message = new StringTemplates().getHelloWorldTemplate();
        instance.submitMessage(this.datenpumpe, message);
    }
    
    @Test
    public void testSubmitMessage_10() {
        final DatenpumpeMessagesSubmitter instance = new DatenpumpeMessagesSubmitter();
        final String message = new StringTemplates().getTheQuickBrownFoxTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 1028);
            instance.submitMessage(this.datenpumpe, m);
        }
    }
}
