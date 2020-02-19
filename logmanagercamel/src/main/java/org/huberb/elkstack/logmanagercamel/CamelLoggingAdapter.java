/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.logmanagercamel;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;

/**
 * Wrapper for holding and building camel-specific objects.
 *
 * @author berni3
 */
public class CamelLoggingAdapter implements Closeable {

    private static final Logger LOG = Logger.getLogger(CamelLoggingAdapter.class.getName());
    private CamelContext camelContext;
    private ProducerTemplate template;

    public CamelContext getCamelContext() {
        return camelContext;
    }

    public ProducerTemplate getTemplate() {
        return template;
    }

    @Override
    public void close() throws IOException {
        try {
            if (template != null) {
                template.stop();
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "producerTemplate close", e);
        } finally {
            try {
                if (camelContext != null) {
                    camelContext.stop();
                }
            } catch (Exception e) {
                LOG.log(Level.WARNING, "camelContext close", e);
            }

        }
    }

    static class F {

        /**
         * short hand creating camel state.
         *
         * @param camelContext
         * @param routeBuilder
         * @return
         * @throws Exception
         */
        CamelLoggingAdapter create(CamelContext camelContext, RouteBuilder routeBuilder) throws Exception {
            final CamelLoggingAdapter camelLoggingAdapter = new CamelLoggingAdapter();
            camelLoggingAdapter.camelContext = camelContext;
            camelLoggingAdapter.camelContext.addRoutes(routeBuilder);
            camelLoggingAdapter.template = camelLoggingAdapter.camelContext.createProducerTemplate();
            return camelLoggingAdapter;
        }
    }

}
