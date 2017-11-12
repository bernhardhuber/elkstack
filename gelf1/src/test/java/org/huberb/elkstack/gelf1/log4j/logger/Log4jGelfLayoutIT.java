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

import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import biz.paluch.logging.gelf.log4j.GelfLayout;

public class Log4jGelfLayoutIT {

    private GelfLayout instance;
    private Logger logger;

    @Before
    public void setUp() {
        this.instance = new GelfLog4jLayoutBuilder().build();
        final ConsoleAppender consoleAppender = new ConsoleAppender(this.instance);
        this.logger = Logger.getLogger((Class) this.getClass());
        this.logger.addAppender((Appender) consoleAppender);
    }

    @After
    public void tearDown() {
        this.logger.removeAllAppenders();
    }

    @Test
    public void testHelloWorld() {
        final String message = new StringTemplates().getHelloWorldTemplate();
        this.logger.info((Object) message);
    }
}
