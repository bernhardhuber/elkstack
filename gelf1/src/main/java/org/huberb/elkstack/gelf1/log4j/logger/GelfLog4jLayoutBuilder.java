/*
 * Copyright 2017 berni3.
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
package org.huberb.elkstack.gelf1.log4j.logger;

import biz.paluch.logging.gelf.log4j.GelfLayout;

/**
 * A very simple log4j GelfLayout builder.
 * 
 * @author berni3
 */
public class GelfLog4jLayoutBuilder {

    public GelfLayout build() {
        final GelfLayout gelfLayout = new GelfLayout();
        return gelfLayout;
    }
}
