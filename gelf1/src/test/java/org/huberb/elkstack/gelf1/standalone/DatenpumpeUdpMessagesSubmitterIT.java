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
package org.huberb.elkstack.gelf1.standalone;

import org.huberb.elkstack.gelf1.GeneratorVaryingString;
import org.junit.Test;
import org.huberb.elkstack.gelf1.StringTemplates;
import org.junit.After;
import org.junit.Before;
import org.huberb.elkstack.gelf1.Configuration;
import biz.paluch.logging.gelf.standalone.Datenpumpe;

public class DatenpumpeUdpMessagesSubmitterIT
{
    private Datenpumpe datenpumpe;
    
    @Before
    public void setUp() {
        this.datenpumpe = new DatenpumpeBuilder().
                host(new Configuration().getUdpHost()).
                port(new Configuration().getUdpPort()).
                specificConfiguration("version", (Object)"1.1").
                build();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testSubmitMessage() {
        final DatenpumpeMessagesSubmitter instance = new DatenpumpeMessagesSubmitter();
        final String message = new StringTemplates().getHelloWorldTemplate();
        instance.submitMessage(this.datenpumpe, message);
    }
    
    @Test
    public void testSubmitMessage_10() {
        final DatenpumpeMessagesSubmitter instance = new DatenpumpeMessagesSubmitter();
        final String message = new StringTemplates().getTheQuickBrownFoxTemplate();
        for (int i = 0; i < 10; ++i) {
            final String m = new GeneratorVaryingString().generateVaryMaxSize(message, 1028);
            instance.submitMessage(this.datenpumpe, m);
        }
    }
}
