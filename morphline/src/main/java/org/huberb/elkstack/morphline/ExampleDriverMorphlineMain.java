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
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Fields;
import org.kitesdk.morphline.base.Notifications;
import org.kitesdk.morphline.base.Compiler;

/**
 *
 * @author berni3
 */
public class ExampleDriverMorphlineMain {

    /**
     * Usage: java ... <morphlines.conf> <dataFile1> ... <dataFileN>
     */
    public static void main(String[] args) throws IOException {
        // compile morphlines.conf file on the fly
        File configFile = new File(args[0]);
        MorphlineContext context = new MorphlineContext.Builder().build();
        Command morphline = new Compiler().compile(configFile, null, context, null);

        // process each input data file
        Notifications.notifyBeginTransaction(morphline);
        for (int i = 1; i < args.length; i++) {
            try (InputStream in = new FileInputStream(new File(args[i]))) {
                Record record = new Record();
                record.put(Fields.ATTACHMENT_BODY, in);
                morphline.process(record);
            }
        }
        Notifications.notifyCommitTransaction(morphline);
    }

}
