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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Compiler;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;

/**
 *
 * @author berni3
 */
public class Morphline {

    public static class MorphlineCommandBuilder {

        private static final Object LOCK = new Object();

        private String configAsString = null;
        private Reader configAsReader = null;
        private String configAsResource = null;
        private Command finalCommand = null;

        public MorphlineCommandBuilder finalCommand(Command finalCommand) {
            this.finalCommand = finalCommand;
            return this;
        }

        public MorphlineCommandBuilder configAsString(String configAsString) {
            clearConfigFields();

            this.configAsString = configAsString;
            return this;
        }

        public MorphlineCommandBuilder configAsReader(Reader configAsReader) {
            clearConfigFields();

            this.configAsReader = configAsReader;
            return this;
        }

        public MorphlineCommandBuilder configAsResource(String configAsResource) {
            clearConfigFields();

            this.configAsResource = configAsResource;
            return this;
        }

        void clearConfigFields() {
            this.configAsString = null;
            this.configAsReader = null;
            this.configAsResource = null;
        }

        public Command build() {
            Command command;

            if (this.configAsString != null) {
                command = buildFromConfigAsString(configAsString);
            } else if (this.configAsResource != null) {
                command = buildFromConfigAsResource(configAsResource);
            } else if (this.configAsReader != null) {
                try {
                    command = buildFromConfigAsReader(configAsReader);
                } catch (IOException ioex) {
                    command = null;
                }
            } else {
                command = null;
            }
            return command;
        }

        Command buildFromConfigAsString(String morphlineDefinition) {
            final MorphlineContext context = new MorphlineContext.Builder()
                    .build();

            final Config config = ConfigFactory.parseString(configAsString);
//                synchronized (LOCK) {
//                    ConfigFactory.invalidateCaches();
//                    config = ConfigFactory.load(config);
//                    config.checkValid(ConfigFactory.defaultReference()); // eagerly validate aspects of tree config
//                }
            final Command morphline = new Compiler().compile(config, context, finalCommand);
            return morphline;
        }

        Command buildFromConfigAsResource(String morphlineDefinition) {
            final MorphlineContext context = new MorphlineContext.Builder().build();
            final Config config = ConfigFactory.parseResources(morphlineDefinition);
//                synchronized (LOCK) {
//                    ConfigFactory.invalidateCaches();
//                    config = ConfigFactory.load(config);
//                    config.checkValid(ConfigFactory.defaultReference()); // eagerly validate aspects of tree config
//                }
            final Command morphline = new Compiler().compile(config, context, finalCommand);
            return morphline;
        }

        Command buildFromConfigAsReader(Reader reader) throws IOException {
            final MorphlineContext context = new MorphlineContext.Builder().build();
            final Config config = ConfigFactory.parseReader(reader);
//            synchronized (LOCK) {
//                ConfigFactory.invalidateCaches();
//                config = ConfigFactory.load(config);
//                config.checkValid(ConfigFactory.defaultReference()); // eagerly validate aspects of tree config
//            }
            final Command morphline = new Compiler().compile(config, context, finalCommand);
            return morphline;
        }
    }

    public static class MorphlineSimpleProcessor {

        private final Command morphline;

        public MorphlineSimpleProcessor(Command morphline) {
            this.morphline = morphline;
        }

        public boolean process(InputStream is) {
            // process each input data file
            try {
                Notifications.notifyBeginTransaction(morphline);
                {
                    final Record record = new Record();
                    record.put(Fields.ATTACHMENT_BODY, is);
                    boolean success = morphline.process(record);
                    return success;
                }
            } finally {
                Notifications.notifyCommitTransaction(morphline);
            }
        }
    }

}
