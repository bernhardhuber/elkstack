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
import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;

public class Log4jGelfLoggerUdpVaryStressIT
{
    private Logger instance;
    private int maxSize;
    
    public Log4jGelfLoggerUdpVaryStressIT() {
        this.maxSize = 16384;
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("logstash-gelf.skipHostnameResolution", "true");
    }
    
    @Before
    public void setUp() {
        this.instance = new GelfLog4jLoggerBuilder().
                host(new Configuration().getUdpHost()).
                port(new Configuration().getUdpPort()).
                category(Log4jGelfLoggerUdpVaryStressIT.class.getName()).
                build();
    }
    
    @After
    public void tearDown() {
        this.instance.removeAllAppenders();
    }
    
    @Test
    public void testSubmitMessage_10_TheQuickBrownFox() {
        for (int i = 0; i < 10; ++i) {
            final String message = new GeneratorVaryingString().generateVaryWords(new StringTemplates().getTheQuickBrownFoxTemplate());
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, this.maxSize);
            this.instance.info((Object)m);
        }
    }
    
    @Test
    public void testSubmitMessage_10_LoremIpsum() {
        for (int i = 0; i < 10; ++i) {
            final String message = new GeneratorVaryingString().generateVaryWords(new StringTemplates().getLoremIpsumTemplate());
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, this.maxSize);
            this.instance.info((Object)m);
        }
    }
    
    @Test
    public void testSubmitMessage_10_AtVero() {
        for (int i = 0; i < 10; ++i) {
            final String message = new GeneratorVaryingString().generateVaryWords(new StringTemplates().getAtVeroTemplate());
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, this.maxSize);
            this.instance.info((Object)m);
        }
    }
}
