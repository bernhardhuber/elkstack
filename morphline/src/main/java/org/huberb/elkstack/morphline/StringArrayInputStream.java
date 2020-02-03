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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author berni3
 */
public class StringArrayInputStream extends InputStream {

    private int lineIndex;
    private int colIndex;
    private final List<String> linesAsList;

    public StringArrayInputStream(String[] lines) {
        linesAsList = Arrays.asList(lines);
        lineIndex = 0;
        colIndex = 0;
    }

    @Override
    public synchronized void reset() throws IOException {
        lineIndex = 0;
        super.reset();
    }

    @Override
    public int read() throws IOException {
        if (lineIndex < linesAsList.size()) {
            final String currentLine = linesAsList.get(lineIndex);
            if (colIndex < currentLine.length()) {
            } else {
                colIndex = 0;
                lineIndex += 1;
            }
        }
        int c = -1;
        if (lineIndex < linesAsList.size()) {
            final String currentLine = linesAsList.get(lineIndex);
            if (colIndex < currentLine.length()) {
                c = currentLine.charAt(colIndex);
                colIndex += 1;
            }
        }
        return c;
    }

}
