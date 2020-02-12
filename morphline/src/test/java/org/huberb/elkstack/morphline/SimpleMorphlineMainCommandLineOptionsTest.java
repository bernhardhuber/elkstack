/*
 * Copyright 2020 berni3.
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
package org.huberb.elkstack.morphline;

import java.util.Map;
import org.apache.commons.cli.ParseException;
import org.huberb.elkstack.morphline.SimpleMorphlineMainCommandLineOptions.OptionsEnum;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author berni3
 */
public class SimpleMorphlineMainCommandLineOptionsTest {

    SimpleMorphlineMainCommandLineOptions instance;

    public SimpleMorphlineMainCommandLineOptionsTest() {
    }

    @Before
    public void setUp() {
        instance = new SimpleMorphlineMainCommandLineOptions();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHelp() throws ParseException {
        Map<SimpleMorphlineMainCommandLineOptions.OptionsEnum, Object> m;
        //---
        m = instance.parseCommandline(new String[]{"-h"});
        assertEquals(false, m.get(OptionsEnum.continueProcessing));
        //---
        m = instance.parseCommandline(new String[]{"--help"});
        assertEquals(false, m.get(OptionsEnum.continueProcessing));
    }

    @Test
    public void testLoglevel() throws ParseException {
        Map<SimpleMorphlineMainCommandLineOptions.OptionsEnum, Object> m;
        //---
        m = instance.parseCommandline(new String[]{"-l", "debug"});
        assertEquals("debug", m.get(OptionsEnum.loglevel));
        //---
        m = instance.parseCommandline(new String[]{"--loglevel", "trace"});
        assertEquals("trace", m.get(OptionsEnum.loglevel));
    }
}
