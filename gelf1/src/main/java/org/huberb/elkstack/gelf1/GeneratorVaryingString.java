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

import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class GeneratorVaryingString {

    public String generateVaryMaxSize(final String m, final int maxSize) {
        final int mLength = m.length();
        final int repeat = maxSize / mLength;
        String result = StringUtils.repeat(m, repeat);
        final int maxLength = RandomUtils.nextInt(3 * maxSize / 4, result.length());
        result = result.substring(0, maxLength);
        return result;
    }

    public String generateVaryWords(final String m) {
        final String wordDelim = " \t\r\n";
        final List<String> mSplittedList = Arrays.asList(StringUtils.split(m, wordDelim));
        Collections.shuffle(mSplittedList);
        final String mShuffeled = StringUtils.join(mSplittedList, " ");
        return mShuffeled;
    }
}
