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
package org.huberb.elkstack.testsuite;

import org.huberb.elkstack.gelf1.log4j.appender.Log4jHttpAppenderIT;
import org.huberb.elkstack.gelf1.log4j.appender.Log4jHttpAppenderStressIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerHttpExceptionIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerHttpLevelIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerHttpStressIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerHttpVaryStressIT;
import org.huberb.elkstack.gelf1.standalone.DatenpumpeHttpMessagesSubmitterIT;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({DatenpumpeHttpMessagesSubmitterIT.class,
    Log4jHttpAppenderIT.class,
    Log4jHttpAppenderStressIT.class,
    Log4jGelfLoggerHttpStressIT.class,
    Log4jGelfLoggerHttpVaryStressIT.class,
    Log4jGelfLoggerHttpExceptionIT.class,
    Log4jGelfLoggerHttpLevelIT.class})
public class HttpTestSuite {
}
