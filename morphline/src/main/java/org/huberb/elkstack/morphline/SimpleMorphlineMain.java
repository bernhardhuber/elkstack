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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.commons.cli.ParseException;
import org.huberb.elkstack.morphline.SimpleMorphlineMainCommandLineOptions.OptionsEnum;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;
import org.kitesdk.morphline.base.Compiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author berni3
 */
public class SimpleMorphlineMain {

    private static final Logger logger = LoggerFactory.getLogger(SimpleMorphlineMain.class);

    /**
     * Usage: java ... <morphlines.conf> <dataFile1> ... <dataFileN>
     */
    public static void main(String[] args) throws IOException, ParseException {
        SimpleMorphlineMain simpleMorphlineMain = new SimpleMorphlineMain();
        simpleMorphlineMain.doProcessing(args);
    }

    void doProcessing(String[] args) throws ParseException {
        final SimpleMorphlineMainCommandLineOptions simpleMorphlineMainCommandLineOptions = new SimpleMorphlineMainCommandLineOptions();

        final Map<SimpleMorphlineMainCommandLineOptions.OptionsEnum, Object> m
                = simpleMorphlineMainCommandLineOptions.parseCommandline(args);
        if (!(boolean) m.get(OptionsEnum.continueProcessing)) {
            return;
        }

        final List<String> fileList = (List<String>) m.get(OptionsEnum.files);

        {
            final String loglevel = (String) m.get(OptionsEnum.loglevel);
            // see simpleLogger config/documentation
            System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", loglevel);
        }

        final String conf = (String) m.get(OptionsEnum.conf);
        // compile morphlines.conf file on the fly
        final File configFile = new File(conf);
        final MorphlineContext context = new MorphlineContext.Builder().build();
        final Command morphline = new Compiler().compile(configFile, null, context, null);

        // process each input data file
        Notifications.notifyBeginTransaction(morphline);
        for (String fileListElement : fileList) {
            final File fileListElementAsFile = new File(fileListElement);
            final boolean canReadFromFile = isFromFileReadable(fileListElementAsFile);
            if (canReadFromFile) {
                try (InputStream in = new FileInputStream(fileListElementAsFile)) {
                    final Record record = new Record();
                    record.put(Fields.ATTACHMENT_BODY, in);
                    morphline.process(record);
                } catch (IOException ex) {
                    logger.warn("Cannot read from " + fileListElementAsFile, ex);
                }
            }
        }
        Notifications.notifyCommitTransaction(morphline);
    }

    boolean isFromFileReadable(File f) {
        return f.isFile()
                && f.exists()
                && f.canRead();
    }
}
