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

import org.junit.Test;
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import java.net.MalformedURLException;
import org.apache.log4j.Appender;
import biz.paluch.logging.gelf.log4j.GelfLayout;
import org.huberb.elkstack.gelf1.Configuration;
import org.apache.log4j.Logger;

public class Log4jTcpAppenderStressIT {

    private Logger logger;
    private Log4jTcpAppender instance;

    @Before
    public void setUp() throws MalformedURLException {
        this.instance = new Log4jTcpAppender();
        this.instance.setHost(new Configuration().getTcpHost());
        this.instance.setPort(new Configuration().getTcpPort());
        this.instance.setLayout(new GelfLayout());
        this.instance.activateOptions();
        this.logger = Logger.getLogger(this.getClass());
        this.logger.addAppender((Appender) this.instance);
    }

    @After
    public void tearDown() {
        this.logger.removeAllAppenders();
    }

    @Test
    public void testSubmitMessage_10_1028() {
        final String message = new StringTemplates().getTheQuickBrownFoxTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 1028);
            this.logger.info((Object) m);
        }
    }

    @Test
    public void testSubmitMessage_10_8192() {
        final String message = new StringTemplates().getAtVeroTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 8192);
            this.logger.info((Object) m);
        }
    }
}
