/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.gelf1.standalone;

import biz.paluch.logging.gelf.intern.GelfSenderConfiguration;
import biz.paluch.logging.gelf.standalone.DatenpumpeImpl;
import biz.paluch.logging.gelf.standalone.DefaultGelfSenderConfiguration;
import biz.paluch.logging.gelf.standalone.Datenpumpe;
import java.util.HashMap;
import org.huberb.elkstack.gelf1.Configuration;
import java.util.Map;

/**
 * A very simple Datenpumpe builder.
 * 
 * @author berni3
 */
public class DatenpumpeBuilder {

    private String host;
    private int port;
    private Map<String, Object> specificConfiguration;

    public DatenpumpeBuilder() {
        this.host = new Configuration().getUdpHost();
        this.port = new Configuration().getUdpPort();
        this.specificConfiguration = new HashMap<String, Object>();
    }

    public DatenpumpeBuilder host(final String host) {
        this.host = host;
        return this;
    }

    public DatenpumpeBuilder port(final int port) {
        this.port = port;
        return this;
    }

    public DatenpumpeBuilder specificConfiguration(final String key, final Object value) {
        this.specificConfiguration.put(key, value);
        return this;
    }

    public DatenpumpeBuilder specificConfigurationMap(final Map<String, Object> specificConfiguration) {
        this.specificConfiguration.putAll(specificConfiguration);
        return this;
    }

    public Datenpumpe build() {
        final DefaultGelfSenderConfiguration gelfSenderConfiguration = new DefaultGelfSenderConfiguration();
        gelfSenderConfiguration.setHost(this.host);
        gelfSenderConfiguration.setPort(this.port);
        gelfSenderConfiguration.setSpecificConfigurations((Map) this.specificConfiguration);
        final Datenpumpe datenpumpe = (Datenpumpe) new DatenpumpeImpl((GelfSenderConfiguration) gelfSenderConfiguration);
        return datenpumpe;
    }
}
