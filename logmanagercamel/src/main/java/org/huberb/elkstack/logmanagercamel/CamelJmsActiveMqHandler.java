/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.component.jms.JmsComponent;

/**
 *
 * @author berni3
 */
public class CamelJmsActiveMqHandler extends Handler {

    private static final Logger LOG = Logger.getLogger(CamelJmsActiveMqHandler.class.getName());
    private CamelLoggingAdapter h;

    // Set on WildFly
    private String destination;
    private String brokerUrl = "tcp://0.0.0.0:61616";

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    private void init() {
        final CamelContext camelContext = new DefaultCamelContext();

        //---
        final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerUrl);
        camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        final RouteBuilder routeBuilder = new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:logger").to(destination);
            }
        };
        try {
            this.h = new CamelLoggingAdapter.F().create(camelContext, routeBuilder);
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "init", ex);
        }
    }

    @Override
    public void publish(LogRecord record) {
        if (this.h == null) {
            init();
        }
        try {
            final Map<String, Object> headers = new HeadersFromLogRecordBuilder().logRecord(record).build();
            h.getTemplate().sendBodyAndHeader("direct:logger", record.getMessage(), headers);
        } catch (CamelExecutionException cexecex) {
            LOG.log(Level.WARNING, "publish " + record, cexecex);
        }
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    @Override
    public void close() throws SecurityException {
        try {
            h.close();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "close", e);
        }
    }
}
