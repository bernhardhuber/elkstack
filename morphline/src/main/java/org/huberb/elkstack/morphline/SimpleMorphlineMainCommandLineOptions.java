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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author berni3
 */
class SimpleMorphlineMainCommandLineOptions {

    public enum OptionsEnum {
        conf, loglevel, files, continueProcessing
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
        final String conf = commandLine.getOptionValue("c", "morphline.conf");
        final String loglevel = commandLine.getOptionValue("l", "info");
        final List<String> argsList = commandLine.getArgList();
        //---
        final Map<OptionsEnum, Object> m = new HashMap<>();
        m.put(OptionsEnum.conf, conf);
        m.put(OptionsEnum.loglevel, loglevel);
        m.put(OptionsEnum.files, argsList);
        m.put(OptionsEnum.continueProcessing, continueProcessing);
        return m;
    }

    protected Options createOptions() {
        final Options options = new Options();
        options.addOption(Option.builder("c").longOpt("conf").hasArg().argName("file").desc("morphline configuration").build());
        options.addOption(Option.builder("l").longOpt("loglevel").hasArg().argName("level").desc("logging level ").build());
        options.addOption(Option.builder("h").longOpt("help").desc("show help").build());
        return options;
    }

}
