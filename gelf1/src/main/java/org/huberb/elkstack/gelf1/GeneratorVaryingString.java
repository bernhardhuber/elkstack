/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        final String mShuffeled = StringUtils.join((Iterable) mSplittedList, " ");
        return mShuffeled;
    }
}
