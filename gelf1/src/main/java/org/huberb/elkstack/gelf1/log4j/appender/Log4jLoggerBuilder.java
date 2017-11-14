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
package org.huberb.elkstack.gelf1.log4j.appender;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * A very simple log4j Logger builder using GelfLogAppender as it sole appender.
 *
 * @author berni3
 */
public class Log4jLoggerBuilder {

    private Level level;
    private String category;
    private Appender appender;

    public Log4jLoggerBuilder() {
        this.level = Level.TRACE;
        this.category = "gelflog4j";
    }

    public Log4jLoggerBuilder level(Level level) {
        this.level = level;
        return this;
    }

    public Log4jLoggerBuilder category(final String category) {
        this.category = category;
        return this;
    }

    public Log4jLoggerBuilder appender(Appender appender) {
        this.appender = appender;
        return this;
    }

    public Logger build() {
        final Logger logger = Logger.getLogger(this.category);
        logger.addAppender(appender);
        logger.setLevel(level);
        return logger;
    }
}
