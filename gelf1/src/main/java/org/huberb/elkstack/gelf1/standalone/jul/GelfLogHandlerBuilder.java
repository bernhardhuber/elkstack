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
package org.huberb.elkstack.gelf1.standalone.jul;

import biz.paluch.logging.gelf.jul.GelfLogHandler;
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

    public GelfLogHandler build() {
        GelfLogHandler gelfLogHandler = new GelfLogHandler();
        gelfLogHandler.setHost(host);
        gelfLogHandler.setPort(port);
        gelfLogHandler.setLevel(level);
        return gelfLogHandler;
    }
}
