/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author berni3
 */
public class CamelLoggingAdapterTest extends CamelTestSupport {

    public CamelLoggingAdapterTest() {
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    RouteBuilder createMyRouteBuilder() {
        RouteBuilder rb
                = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").to("mock:result");

            }
        };
        return rb;
    }

    /**
     * Test of getCamelContext method, of class CamelLoggingAdapter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetCamelContext() throws Exception {
        CamelContext camelContext = this.context();
        RouteBuilder rb = createMyRouteBuilder();
        CamelLoggingAdapter instance = new CamelLoggingAdapter.F().create(camelContext, rb);
        CamelContext result = instance.getCamelContext();
        assertNotNull(result);
    }

    /**
     * Test of getTemplate method, of class CamelLoggingAdapter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTemplate() throws Exception {
        CamelContext camelContext = this.context();
        RouteBuilder rb = createMyRouteBuilder();
        CamelLoggingAdapter instance = new CamelLoggingAdapter.F().create(camelContext, rb);
        ProducerTemplate result = instance.getTemplate();
        assertNotNull(result);
    }

    /**
     * Test of close method, of class CamelLoggingAdapter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testClose() throws Exception {
        CamelContext camelContext = this.context();
        RouteBuilder rb = createMyRouteBuilder();
        CamelLoggingAdapter instance = new CamelLoggingAdapter.F().create(camelContext, rb);
        instance.close();
        assertEquals(true, instance.getCamelContext().isStopped());
    }

}
