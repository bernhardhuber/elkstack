// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.testsuite;

import org.huberb.elkstack.log4j2.Log4j2Log4j2XmlIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpLevelIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpExceptionIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpVaryStressIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpStressIT;
import org.huberb.elkstack.gelf1.log4j.logger.Log4jGelfLoggerUdpIT;
import org.huberb.elkstack.gelf1.standalone.DatenpumpeUdpMessagesSubmitterIT;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DatenpumpeUdpMessagesSubmitterIT.class, Log4jGelfLoggerUdpIT.class, Log4jGelfLoggerUdpStressIT.class, Log4jGelfLoggerUdpVaryStressIT.class, Log4jGelfLoggerUdpExceptionIT.class, Log4jGelfLoggerUdpLevelIT.class, Log4j2Log4j2XmlIT.class })
public class UdpTestSuite
{
}
