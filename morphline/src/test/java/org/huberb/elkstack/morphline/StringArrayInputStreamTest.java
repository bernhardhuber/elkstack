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

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author berni3
 */
public class StringArrayInputStreamTest {

    /**
     * Test of read method, of class StringArrayInputStream.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRead() throws Exception {
        final String[] lines = {"ABC", "DEF"};
        final StringArrayInputStream instance = new StringArrayInputStream(lines);

        int result = instance.read();
        assertEquals((int) 'A', result);
    }

    /**
     * Test of read method, of class StringArrayInputStream.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadAll() throws Exception {
        final String[] lines = {"ABC", "DEF"};
        final StringArrayInputStream instance = new StringArrayInputStream(lines);

        final byte[] readBytes = new byte[512];
        final int countReadBytes = instance.read(readBytes);
        final int expectedCountReadBytes = 6;
        assertEquals(expectedCountReadBytes, countReadBytes);
        final byte[] expectedBytes = new byte[]{
            (byte) 'A',
            (byte) 'B',
            (byte) 'C',
            (byte) 'D',
            (byte) 'E',
            (byte) 'F',};
        final byte[] trimmedReadBytes = Arrays.copyOf(readBytes, expectedCountReadBytes);
        assertArrayEquals(expectedBytes, trimmedReadBytes);
    }

}
