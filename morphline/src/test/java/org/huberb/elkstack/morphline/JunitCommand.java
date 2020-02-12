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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;

/**
 * A command for collecting {@link Record}s for JUnit tests.
 * 
 * @author berni3
 */
public class JunitCommand implements Command {

    private final Command parent;
    private final List<Record> recordList = new ArrayList<>();

    public JunitCommand(Command parent) {
        this.parent = parent;
    }

    public Record peekRecordFromIndex(int i) {
        return recordList.get(i);
    }

    @Override
    public Command getParent() {
        return parent;
    }

    @Override
    public void notify(Record notification) {
    }

    @Override
    public boolean process(Record record) {
        this.recordList.add(record);
        return true;
    }

    public List<Record> getRecordList() {
        List<Record> result =Collections.unmodifiableList(recordList);
        return result;
    }

}
