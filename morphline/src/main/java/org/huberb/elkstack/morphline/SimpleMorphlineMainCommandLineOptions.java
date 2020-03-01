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

import com.google.common.io.CharStreams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.huberb.elkstack.morphline.SimpleMorphlineMainCommandLineOptions.ConfsCommands;
import org.huberb.elkstack.morphline.SimpleMorphlineMainCommandLineOptions.DictonariesCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author berni3
 */
class SimpleMorphlineMainCommandLineOptions {

    public enum OptionsEnum {
        conf, loglevel, files,
        continueProcessing;
    }

    public Map<OptionsEnum, Object> parseCommandline(String[] args) throws ParseException {
        final Options options = createOptions();
        // create the command line parser
        final CommandLineParser parser = new DefaultParser();
        // parse the command line arguments
        final CommandLine commandLine = parser.parse(options, args);
        boolean continueProcessing = true;
        if (commandLine.hasOption("h")) {
            // validate that block-size has been set
            HelpFormatter formatter = new HelpFormatter();
            String header = "";
            String footer = "";
            formatter.printHelp(SimpleMorphlineMain.class.getSimpleName(), header, options, footer, true);
            continueProcessing = false;
        }
        if (commandLine.hasOption("list-dictionaries")) {
            new DictonariesCommands().listDictonaries();
            continueProcessing = false;
        }
        if (commandLine.hasOption("show-dictionaries")) {
            new DictonariesCommands().showDictonaries();
            continueProcessing = false;
        }
        if (commandLine.hasOption("list-confs")) {
            new ConfsCommands().listConfs();
            continueProcessing = false;
        }
        if (commandLine.hasOption("show-confs")) {
            new ConfsCommands().showConfs();
            continueProcessing = false;
        }
        final String conf = commandLine.getOptionValue("c", "morphlines.conf");
        final String loglevel = commandLine.getOptionValue("l", "info");
        //---
        final List<String> argsList = commandLine.getArgList();
        //---
        final Map<OptionsEnum, Object> m = new FluentMapBuilder<>()
                .keyValue(OptionsEnum.conf, conf)
                .keyValue(OptionsEnum.loglevel, loglevel)
                .keyValue(OptionsEnum.files, argsList)
                .keyValue(OptionsEnum.continueProcessing, continueProcessing)
                .build();

        return m;
    }

    static class FluentMapBuilder<K, V> {

        final Map<K, V> m = new HashMap<>();

        FluentMapBuilder keyValue(K k, V v) {
            this.m.put(k, v);
            return this;
        }

        FluentMapBuilder keyValueIfAbsent(K k, V v) {
            this.m.putIfAbsent(k, v);
            return this;
        }

        Map<K, V> build() {
            return m;
        }
    }

    protected Options createOptions() {
        final Options options = new Options();
        options.addOption(Option.builder("h").longOpt("help").desc("show help").build());
        options.addOption(Option.builder("c").longOpt("conf").hasArg().argName("morphile-conf").desc("morphline configuration").build());
        options.addOption(Option.builder("l").longOpt("loglevel").hasArg().argName("level").desc("logging level").build());
        options.addOption(Option.builder().longOpt("list-dictionaries").desc("list packaged morphline dictionaries").build());
        options.addOption(Option.builder().longOpt("show-dictionaries").desc("show packaged morphline dictionaries").build());
        options.addOption(Option.builder().longOpt("list-confs").desc("list packaged morphline confs").build());
        options.addOption(Option.builder().longOpt("show-confs").desc("show packaged morphline confs").build());
        return options;
    }

    static class DictonariesCommands {

        final List<String> dictionariesResourceList = Arrays.asList(
                "grok-dictionaries/grok-patterns",
                "grok-dictionaries/firewalls",
                "grok-dictionaries/grok-patterns",
                "grok-dictionaries/java",
                "grok-dictionaries/linux-syslog",
                "grok-dictionaries/mcollective",
                "grok-dictionaries/mcollective-patterns",
                "grok-dictionaries/nagios",
                "grok-dictionaries/postgresql",
                "grok-dictionaries/redis",
                "grok-dictionaries/ruby"
        );

        void listDictonaries() {
            String content = dictionariesResourceList.stream().collect(Collectors.joining("\n"));
            new ShowSupport().showStringContent("dictionaries\n" + content);
        }

        void showDictonaries() {
            for (String resource : dictionariesResourceList) {
                try {
                    new ShowSupport().showResourceContent(resource);
                } catch (IOException ioex) {
                }
            }
        }

    }

    static class ConfsCommands {

        final List<String> confsResourceList = Arrays.asList(
                "confs/access_log_readLine.conf",
                "confs/server_log_readLine.conf",
                "confs/server_log_readLineTry.conf",
                "confs/server_log_readMultiLine.conf"
        );

        void listConfs() {
            String content = confsResourceList.stream().collect(Collectors.joining("\n"));
            new ShowSupport().showStringContent("dictionaries\n" + content);
        }

        void showConfs() {
            for (String resource : confsResourceList) {
                try {
                    new ShowSupport().showResourceContent(resource);
                } catch (IOException ioex) {
                }
            }
        }
    }

    static class ShowSupport {

        void showResourceContent(String resource) throws IOException {
            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource)) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
                    String content = resource + ":\n" + CharStreams.toString(br);
                    showStringContent(content);
                }
            }
        }

        void showStringContent(String content) {
            Logger showContentLogger = LoggerFactory.getLogger("showContent");
            showContentLogger.info(content);
        }
    }
}
