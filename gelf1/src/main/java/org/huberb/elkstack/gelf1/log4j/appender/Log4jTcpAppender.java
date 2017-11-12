/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.gelf1.log4j.appender;

import java.io.OutputStream;
import java.net.SocketAddress;
import java.io.IOException;
import java.nio.channels.IllegalBlockingModeException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.AppenderSkeleton;

public class Log4jTcpAppender extends AppenderSkeleton {

    private String host;
    private int port;
    private int connectTimeout;
    private int readTimeout;

    public Log4jTcpAppender() {
        this.connectTimeout = 500;
        this.readTimeout = 500;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(final int readTimeout) {
        this.readTimeout = readTimeout;
    }

    protected void append(final LoggingEvent event) {
        final String formattedEvent = this.layout.format(event);
        try (final Socket socket = new Socket()) {
            final SocketAddress endpoint = new InetSocketAddress(this.host, this.port);
            socket.connect(endpoint, this.connectTimeout);
            socket.setSoTimeout(this.readTimeout);
            final OutputStream os = socket.getOutputStream();
            os.write(formattedEvent.getBytes());
        } catch (IllegalBlockingModeException | IllegalArgumentException | IOException ex) {
            this.errorHandler.error("Cannot write LoggingEvent to host " + this.host + ", port " + this.port, ex, -1);
        }
    }

    public boolean requiresLayout() {
        return true;
    }

    public void close() {
    }
}
