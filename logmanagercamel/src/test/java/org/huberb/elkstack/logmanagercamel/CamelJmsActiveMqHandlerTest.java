/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author berni3
 */
public class CamelJmsActiveMqHandlerTest extends CamelTestSupport {

    CamelJmsActiveMqHandler instance;

    public CamelJmsActiveMqHandlerTest() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        instance = new CamelJmsActiveMqHandler();
        instance.setDestination("mock:result");
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of publish method, of class CamelCoreHandler.
     */
    @Test
    public void testPublish() {
        LogRecord record = new LogRecord(Level.INFO, "someMessage");
        instance.publish(record);
    }

    /**
     * Test of close method, of class CamelCoreHandler.
     */
    @Test
    public void testClose() {
        instance.close();
    }

}
