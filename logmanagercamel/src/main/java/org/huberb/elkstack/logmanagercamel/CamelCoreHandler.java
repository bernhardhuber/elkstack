/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author berni3
 */
public class CamelCoreHandler extends Handler {

    private static final Logger LOG = Logger.getLogger(CamelCoreHandler.class.getName());
    private AtomicStampedReference<CamelLoggingAdapter> arh = new AtomicStampedReference(null, 0);
    private Object lock = new Object();
    //private CamelLoggingAdapter h;

    // Set on WildFly
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    private CamelLoggingAdapter setupCamelLoggingAdapter() {
        CamelLoggingAdapter result;
        final CamelContext camelContext = new DefaultCamelContext();
        final RouteBuilder routeBuilder = new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:logger").to(destination);
            }
        };
        try {
            result = new CamelLoggingAdapter.F().create(camelContext, routeBuilder);
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "init", ex);
            result = null;
        }
        return result;
    }

    @Override
    public void publish(LogRecord record) {
        final int initTries = 5;
        if (arh.getReference() == null) {
            synchronized (lock) {
                int stamp = arh.getStamp();
                if (stamp < initTries) {
                    final CamelLoggingAdapter cla = setupCamelLoggingAdapter();
                    stamp += 1;
                    arh.set(cla, stamp);
                } else {
                    return;
                }
            }
        }

        try {
            final CamelLoggingAdapter cla = arh.getReference();
            final Map<String, Object> headers = new HeadersFromLogRecordBuilder().logRecord(record).build();
            cla.getTemplate().sendBodyAndHeaders("direct:logger", record.getMessage(), headers);
        } catch (CamelExecutionException caxex) {
            LOG.log(Level.WARNING, "publish " + record, caxex);
        }
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    @Override
    public void close() throws SecurityException {
        try {
            CamelLoggingAdapter cla = arh.getReference();
            if (cla != null) {
                cla.close();
            }
        } catch (IOException e) {
            LOG.log(Level.WARNING, "close", e);
        } finally {
            arh.set(null, 0);
        }
    }

}
