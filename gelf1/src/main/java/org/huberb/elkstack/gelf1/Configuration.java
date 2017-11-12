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
package org.huberb.elkstack.gelf1;

public class Configuration {

    private final int udpPort = 12201;
    private final int httpPort = 12202;
    private final int tcpPort = 12203;
    private final String udpHost = "udp:localhost";
    private final String httpHostPort = "http://localhost:12202/";
    private final String tcpHost = "localhost";

    public int getUdpPort() {
        return udpPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public String getUdpHost() {
        return udpHost;
    }

    public String getHttpHostPort() {
        return httpHostPort;
    }

    public String getTcpHost() {
        return tcpHost;
    }

}
