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

import org.huberb.elkstack.gelf1.log4j.appender.Log4jLoggerBuilder;
import org.junit.Test;
import java.util.List;
import org.apache.log4j.Priority;
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import java.util.Arrays;
import org.apache.log4j.Level;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerUdpLevelIT {

    private Logger instance;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }

    @Before
    public void setUp() {
        this.instance = new Log4jLoggerBuilder().
                category(this.getClass().getName()).
                appender(new GelfLog4jAppenderBuilder().
                        host(new Configuration().getUdpHost()).
                        port(new Configuration().getUdpPort()).build()).
                build();
    }

    @Test
    public void testSubmitMessage_Levels_2048() {
        final String message = new StringTemplates().getLoremIpsumTemplate();
        final List<Level> allLevels = Arrays.asList(
                Level.OFF,
                Level.FATAL,
                Level.ERROR,
                Level.WARN,
                Level.INFO,
                Level.DEBUG,
                Level.ALL);
        for (final Level level : allLevels) {
            for (int i = 0; i < 10; ++i) {
                final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 2048);
                this.instance.log(level, m);
            }
        }
    }
}
