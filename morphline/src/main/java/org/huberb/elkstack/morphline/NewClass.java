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
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.MorphlineContext;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.stdlib.DropRecordBuilder;
import org.kitesdk.morphline.stdlib.GrokBuilder;

/**
 *
 * @author berni3
 */
public class NewClass {

    void xxx() {
        //final Config config = ConfigFactory.parseReader(reader);
        //ConfigFactory.
        Config config = null;
        Command parent = null;
        final MorphlineContext context = new MorphlineContext.Builder().build();

        Command dropRecord = new DropRecordBuilder().build(null, null, null, context);
        Command c = new GrokBuilder().build(config, parent, dropRecord, context);
        Record record = new Record();
        boolean success=c.process(record);
    }
}
