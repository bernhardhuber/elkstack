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
package org.huberb.elkstack.morphline.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigSyntax;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author berni3
 */
public class SimpleConfigTest {

    private final Logger logger = LoggerFactory.getLogger(SimpleConfigTest.class.getName());

    public SimpleConfigTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParseJson() {
        String resourceConfigJson = "config/simple1.json";
        ConfigParseOptions configParseOptions = ConfigParseOptions.defaults().setSyntax(ConfigSyntax.JSON);
        Config config = ConfigFactory.parseResources(resourceConfigJson, configParseOptions);
        assertNotNull(config);

        ConfigRenderOptions configRenderOptions = ConfigRenderOptions.defaults().setOriginComments(false);
        logger_debug("testParseJson", config.root().render(configRenderOptions));
    }

    @Test
    public void testParseProperties() {
        String resourceConfigJson = "config/simple1.properties";
        ConfigParseOptions configParseOptions = ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES);
        Config config = ConfigFactory.parseResources(resourceConfigJson, configParseOptions);
        assertNotNull(config);

        ConfigRenderOptions configRenderOptions = ConfigRenderOptions.defaults().setOriginComments(false);
        logger_debug("testParseProperties", config.root().render(configRenderOptions));
    }

    @Test
    public void testParseConf() {
        String resourceConfigJson = "config/simple1.conf";
        ConfigParseOptions configParseOptions = ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF);
        Config config = ConfigFactory.parseResources(resourceConfigJson, configParseOptions);
        assertNotNull(config);

        ConfigRenderOptions configRenderOptions = ConfigRenderOptions.defaults().setOriginComments(false);
        logger_debug("testParseConf", config.root().render(configRenderOptions));
    }

    @Test
    public void testParseFlumeProperties() {
        String resourceConfigJson = "config/flume-conf-berni-netcat.properties";
        ConfigParseOptions configParseOptions = ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES);
        Config config = ConfigFactory.parseResources(resourceConfigJson, configParseOptions);
        assertNotNull(config);

        ConfigRenderOptions configRenderOptions = ConfigRenderOptions.defaults()
                .setComments(true)
                .setFormatted(true)
                .setJson(false)
                .setOriginComments(false);
        logger_debug("testParseFlumeProperties", config.root().render(configRenderOptions));
    }

    @Test
    public void testParseServerLogReadMultiLineConf() {
        String resourceConfigJson = "morphline/jbossas7/server_log_readMultiLine.conf";
        ConfigParseOptions configParseOptions = ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF);
        Config config = ConfigFactory.parseResources(resourceConfigJson, configParseOptions);
        assertNotNull(config);

        ConfigRenderOptions configRenderOptions = ConfigRenderOptions.defaults()
                .setComments(true)
                .setFormatted(true)
                .setJson(false)
                .setOriginComments(false);
        logger_debug("testParseServerLogReadMultiLineConf", config.root().render(configRenderOptions));
    }

    private void logger_debug(String prefix, String message) {
        logger.debug("{}:\n{}", prefix, message);
    }
}
