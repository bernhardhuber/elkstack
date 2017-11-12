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

import org.apache.log4j.Appender;
import biz.paluch.logging.gelf.log4j.GelfLogAppender;
import org.apache.log4j.Logger;
import org.huberb.elkstack.gelf1.Configuration;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;

/**
 * A very simple log4j Logger builder using GelfLogAppender as it sole appender.
 *
 * @author berni3
 */
public class GelfLog4jLoggerBuilder {

    private Priority threshold;
    private String category;
    private String host;
    private int port;
    private String version;
    private String facility;
    private String extractStackTrace;
    private String filterStackTrace;
    private int maximumMessageSize;
    private boolean includeFullMdc;

    public GelfLog4jLoggerBuilder() {
        this.threshold = Level.TRACE;
        this.category = "gelflog4j";
        this.host = new Configuration().getUdpHost();
        this.port = new Configuration().getUdpPort();
        this.version = "1.0";
        this.facility = "gelf-log4j";
        this.extractStackTrace = "true";
        this.filterStackTrace = "true";
        this.maximumMessageSize = 8192;
        this.includeFullMdc = true;
    }

    public GelfLog4jLoggerBuilder host(final String host) {
        this.host = host;
        return this;
    }

    public GelfLog4jLoggerBuilder port(final int port) {
        this.port = port;
        return this;
    }

    public GelfLog4jLoggerBuilder category(final String category) {
        this.category = category;
        return this;
    }

    public Logger build() {
        final GelfLogAppender gelfAppender = new GelfLogAppender();
        gelfAppender.setThreshold(this.threshold);
        gelfAppender.setHost(this.host);
        gelfAppender.setPort(this.port);
        gelfAppender.setVersion(this.version);
        gelfAppender.setFacility(this.facility);
        gelfAppender.setExtractStackTrace(this.extractStackTrace);
        gelfAppender.setFilterStackTrace(true);
        gelfAppender.setMaximumMessageSize(this.maximumMessageSize);
        gelfAppender.setIncludeFullMdc(this.includeFullMdc);
        gelfAppender.setName("gelfAppender");
        gelfAppender.activateOptions();
        final Logger logger = Logger.getLogger(this.category);
        logger.addAppender((Appender) gelfAppender);
        return logger;
    }
}
