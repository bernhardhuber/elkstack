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

import biz.paluch.logging.gelf.intern.ErrorReporter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;

/**
 *
 * @author berni3
 */
class StoringErrorReporter implements ErrorReporter {
    
    private List<ImmutablePair<String, Exception>> reportErrorList = new ArrayList<>();

    @Override
    public void reportError(String message, Exception e) {
        ImmutablePair<String, Exception> immutablePair = ImmutablePair.of(message, e);
        reportErrorList.add(immutablePair);
    }

    public List<ImmutablePair<String, Exception>> getReportErrorList() {
        return reportErrorList;
    }

    void assertReportErrorListEmpty() {
        StringBuilder sb = new StringBuilder();
        reportErrorList.forEach((ImmutablePair<String, Exception> v) -> sb.append(String.valueOf(v)).append("\n"));
        String m = sb.toString();
        Assert.assertEquals(m, true, reportErrorList.isEmpty());
    }
    
}
