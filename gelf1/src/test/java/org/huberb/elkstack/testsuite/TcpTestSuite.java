// 
// Decompiled by Procyon v0.5.30
// 

package org.huberb.elkstack.testsuite;

import org.huberb.elkstack.gelf1.log4j.appender.Log4jTcpAppenderStressIT;
import org.huberb.elkstack.gelf1.log4j.appender.Log4jTcpAppenderIT;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({ Log4jTcpAppenderIT.class, Log4jTcpAppenderStressIT.class })
public class TcpTestSuite
{
}
