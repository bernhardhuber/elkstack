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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.huberb.elkstack.gelf1.Configuration;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author berni3
 */
public class JulGelfLogHandlerUdpIT {

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }

    public JulGelfLogHandlerUdpIT() {
    }
    GelfLogHandler gelfLogHandler;
    Logger logger;

    @Before
    public void setUp() {
        gelfLogHandler = new GelfLogHandlerBuilder().
                host(new Configuration().getUdpHost()).
                port(new Configuration().getUdpPort()).
                level(Level.ALL).
                build();

        logger = Logger.getLogger(this.getClass().getName());
        logger.addHandler(gelfLogHandler);
    }

    @After
    public void tearDown() {
        this.gelfLogHandler.close();
    }

    @Test
    public void testHelloWorld() {
        String message = new StringTemplates().getHelloWorldTemplate();

        logger.info(message);
    }
}
