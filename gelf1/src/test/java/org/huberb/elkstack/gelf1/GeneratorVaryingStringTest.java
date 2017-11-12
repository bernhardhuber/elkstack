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
package org.huberb.elkstack.gelf1;

import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;

public class GeneratorVaryingStringTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGenerateVaryMaxSize() {
        final String m = "ABC";
        final GeneratorVaryingString instance = new GeneratorVaryingString();
        final String result = instance.generateVaryMaxSize(m, 10);
        Assert.assertTrue(result.length() >= 3);
        Assert.assertTrue(result.length() <= 10);
        Assert.assertNotSame((Object) m, (Object) result);
    }

    @Test
    public void testGenerateVaryWords() {
        final String m = "ABC DEF GHI";
        final GeneratorVaryingString instance = new GeneratorVaryingString();
        final String result = instance.generateVaryWords(m);
        Assert.assertEquals((long) m.length(), (long) result.length());
        Assert.assertNotSame((Object) m, (Object) result);
    }
}
