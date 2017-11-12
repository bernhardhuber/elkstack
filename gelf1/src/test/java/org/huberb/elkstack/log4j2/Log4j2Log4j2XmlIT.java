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
package org.huberb.elkstack.log4j2;

import org.junit.Test;
import org.apache.logging.log4j.Logger;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;

public class Log4j2Log4j2XmlIT {

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }

    @Test
    public void testHelloWorld() {
        final Logger logger = LogManager.getLogger((Class) this.getClass());
        final String message = new StringTemplates().getHelloWorldTemplate();
        logger.info(message);
    }
}
