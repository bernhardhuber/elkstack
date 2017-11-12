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
package org.huberb.elkstack.gelf1.testsuite;

import org.huberb.elkstack.gelf1.jul.JulGelfLogHandlerUdpIT;
import org.huberb.elkstack.gelf1.log4j2.Log4j2Log4j2XmlIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpLevelIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpExceptionIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpVaryStressIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpStressIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpIT;
import org.huberb.elkstack.gelf1.standalone.DatenpumpeUdpMessagesSubmitterIT;
import org.huberb.elkstack.gelf1.wildfly.WildfFlyGelfLogHandlerUdpIT;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({DatenpumpeUdpMessagesSubmitterIT.class,
    Log4jGelfLoggerUdpIT.class,
    Log4jGelfLoggerUdpStressIT.class,
    Log4jGelfLoggerUdpVaryStressIT.class,
    Log4jGelfLoggerUdpExceptionIT.class,
    Log4jGelfLoggerUdpLevelIT.class,
    Log4j2Log4j2XmlIT.class,
    JulGelfLogHandlerUdpIT.class,
    WildfFlyGelfLogHandlerUdpIT.class
})
public class UdpTestSuite {
}
