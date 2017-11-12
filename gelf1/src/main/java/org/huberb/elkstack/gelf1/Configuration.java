/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
