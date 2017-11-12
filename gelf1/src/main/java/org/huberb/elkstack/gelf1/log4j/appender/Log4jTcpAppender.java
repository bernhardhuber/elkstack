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
package org.huberb.elkstack.gelf1.log4j.appender;

import java.io.OutputStream;
import java.net.SocketAddress;
import java.io.IOException;
import java.nio.channels.IllegalBlockingModeException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.AppenderSkeleton;

/**
 * A simple log4j tcp appender using log4-format.
 *
 * Configuration options:
 * <ul>
 * <li>Host - mandatory - host to connect to
 * </li>
 * <li>Port - mandatory - port to connect to
 * </li>
 * <li>ConnectTimeout- optional - timeout to connect to remote host, defaults to
 * 500ms
 * </li>
 * <li>ReadTimeout - optional - timout to read from remote host, defaults to
 * 500ms
 * </li>
 * </ul>
 *
 * @author berni3
 */
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

    @Override
    protected void append(final LoggingEvent event) {
        final String formattedEvent = this.layout.format(event);
        try (final Socket socket = new Socket()) {
            final SocketAddress endpoint = new InetSocketAddress(this.host, this.port);
            socket.setSoTimeout(this.readTimeout);
            socket.connect(endpoint, this.connectTimeout);
            final OutputStream os = socket.getOutputStream();
            os.write(formattedEvent.getBytes());
        } catch (IllegalBlockingModeException | IllegalArgumentException | IOException ex) {
            this.errorHandler.error("Cannot write LoggingEvent to host " + this.host + ", port " + this.port, ex, -1);
        }
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    @Override
    public void close() {
    }
}
