// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.gelf1;

import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;

public class GeneratorVaryingStringTest
{
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
        Assert.assertNotSame((Object)m, (Object)result);
    }
    
    @Test
    public void testGenerateVaryWords() {
        final String m = "ABC DEF GHI";
        final GeneratorVaryingString instance = new GeneratorVaryingString();
        final String result = instance.generateVaryWords(m);
        Assert.assertEquals((long)m.length(), (long)result.length());
        Assert.assertNotSame((Object)m, (Object)result);
    }
}
