/*
 * Copyright 2017 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.elkstack.gelf1.log4j.logger;

import biz.paluch.logging.gelf.log4j.GelfLogAppender;
import org.huberb.elkstack.gelf1.Configuration;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;

/**
 * A very simple log4j Logger builder using GelfLogAppender as it sole appender.
 *
 * @author berni3
 */
public class GelfLog4jAppenderBuilder {

    private Priority threshold;
    private String host;
    private int port;
    private String version;
    private String facility;
    private String extractStackTrace;
    private boolean filterStackTrace;
    private int maximumMessageSize;
    private boolean includeFullMdc;

    public GelfLog4jAppenderBuilder() {
        this.threshold = Level.ALL;
        this.host = new Configuration().getUdpHost();
        this.port = new Configuration().getUdpPort();
        this.version = "1.1";
        this.facility = "gelf-log4j";
        this.extractStackTrace = "true";
        this.filterStackTrace = true;
        this.maximumMessageSize = 8192;
        this.includeFullMdc = true;
    }

    public GelfLog4jAppenderBuilder threshold(Priority threshold) {
        this.threshold = threshold;
        return this;
    }

    public GelfLog4jAppenderBuilder host(final String host) {
        this.host = host;
        return this;
    }

    public GelfLog4jAppenderBuilder port(final int port) {
        this.port = port;
        return this;
    }

    public GelfLog4jAppenderBuilder version(String version) {
        this.version = version;
        return this;
    }

    public GelfLog4jAppenderBuilder facility(String facility) {
        this.facility = facility;
        return this;
    }

    public GelfLog4jAppenderBuilder extractStackTrace(String extractStackTrace) {
        this.extractStackTrace = extractStackTrace;
        return this;
    }

    public GelfLog4jAppenderBuilder filterStackTrace(boolean filterStackTrace) {
        this.filterStackTrace = filterStackTrace;
        return this;
    }

    public GelfLog4jAppenderBuilder maximumMessageSize(int maximumMessageSize) {
        this.maximumMessageSize = maximumMessageSize;
        return this;
    }

    public GelfLog4jAppenderBuilder includeFullMdc(boolean includeFullMdc) {
        this.includeFullMdc = includeFullMdc;
        return this;
    }

    public GelfLogAppender build() {
        final GelfLogAppender gelfAppender = new GelfLogAppender();
        gelfAppender.setThreshold(this.threshold);
        gelfAppender.setHost(this.host);
        gelfAppender.setPort(this.port);
        gelfAppender.setVersion(this.version);
        gelfAppender.setFacility(this.facility);
        gelfAppender.setExtractStackTrace(this.extractStackTrace);
        gelfAppender.setFilterStackTrace(this.filterStackTrace);
        gelfAppender.setMaximumMessageSize(this.maximumMessageSize);
        gelfAppender.setIncludeFullMdc(this.includeFullMdc);
        gelfAppender.setName("gelfAppender");
        gelfAppender.activateOptions();
        return gelfAppender;
    }
}
