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
package org.huberb.elkstack.gelf1.jul;

import biz.paluch.logging.gelf.jul.GelfLogHandler;
import java.util.Optional;
import java.util.logging.Level;
import org.huberb.elkstack.gelf1.Configuration;

/**
 *
 * @author berni3
 */
public class GelfLogHandlerBuilder {

    private String host = new Configuration().getUdpHost();
    private int port = new Configuration().getUdpPort();
    private Level level = Level.ALL;
    private Optional<String> originHost = Optional.empty();
    private Optional<String> facility = Optional.empty();
    private String extractStackTrace = "true";
    private boolean filterStackTrace = true;
    private boolean includeLogMessageParameters = false;
    private Optional<String> timestampPattern = Optional.empty();
    private int maximumMessageSize = 8192;
    private String version = "1.0";

    public GelfLogHandlerBuilder host(String host) {
        this.host = host;
        return this;
    }

    public GelfLogHandlerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public GelfLogHandlerBuilder level(Level level) {
        this.level = level;
        return this;
    }

    public GelfLogHandlerBuilder originHost(String originHost) {
        this.originHost = Optional.of(originHost);
        return this;
    }

    public GelfLogHandlerBuilder facility(String facility) {
        this.facility = Optional.of(facility);
        return this;
    }

    public GelfLogHandlerBuilder extractStackTrace(String extractStacktrace) {
        this.extractStackTrace = extractStacktrace;
        return this;
    }

    public GelfLogHandlerBuilder filterStackTrace(boolean filterStackTrace) {
        this.filterStackTrace = filterStackTrace;
        return this;
    }

    public GelfLogHandlerBuilder includeLogMessageParameters(boolean includeLogMessageParameters) {
        this.includeLogMessageParameters = includeLogMessageParameters;
        return this;
    }

    public GelfLogHandlerBuilder timestampPattern(String timestampPattern) {
        this.timestampPattern = Optional.of(timestampPattern);
        return this;
    }

    public GelfLogHandlerBuilder maximumMessageSize(int maximumMessageSize) {
        this.maximumMessageSize = maximumMessageSize;
        return this;
    }

    public GelfLogHandlerBuilder version(String version) {
        this.version = version;
        return this;
    }

    public GelfLogHandler build() {
        final GelfLogHandler gelfLogHandler = new GelfLogHandler();
        gelfLogHandler.setHost(host);
        gelfLogHandler.setPort(port);
        gelfLogHandler.setLevel(level);

        originHost.ifPresent((v) -> gelfLogHandler.setOriginHost(v));
        facility.ifPresent((v) -> gelfLogHandler.setFacility(v));
        gelfLogHandler.setExtractStackTrace(extractStackTrace);
        gelfLogHandler.setFilterStackTrace(filterStackTrace);
        gelfLogHandler.setIncludeLogMessageParameters(includeLogMessageParameters);
        timestampPattern.ifPresent((v) -> gelfLogHandler.setTimestampPattern(v));
        gelfLogHandler.setMaximumMessageSize(maximumMessageSize);
        gelfLogHandler.setVersion(version);

        return gelfLogHandler;
    }

}
