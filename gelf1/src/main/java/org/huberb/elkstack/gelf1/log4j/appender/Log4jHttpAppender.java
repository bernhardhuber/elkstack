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
import java.io.IOException;
import java.net.HttpURLConnection;
import org.apache.log4j.spi.LoggingEvent;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.AppenderSkeleton;

/**
 * A simple log4j http appender using log4j formatter.
 *
 * Configuration options:
 * <ul>
 * <li>Url - mandatory - http url to post the formatted LogEvent to
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
public class Log4jHttpAppender extends AppenderSkeleton {

    private static final int HTTP_SUCCESSFUL_LOWER_BOUND = 200;
    private static final int HTTP_SUCCESSFUL_UPPER_BOUND = 299;
    private int connectTimeoutMs;
    private int readTimeoutMs;
    private URL url;

    public Log4jHttpAppender() {
        this.connectTimeoutMs = 500;
        this.readTimeoutMs = 500;
    }

    public int getConnectTimeoutMs() {
        return this.connectTimeoutMs;
    }

    public void setConnectTimeoutMs(final int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getReadTimeoutMs() {
        return this.readTimeoutMs;
    }

    public void setReadTimeoutMs(final int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }

    public String getUrl() {
        String urlAsString = null;
        if (this.url != null) {
            urlAsString = this.url.toExternalForm();
        }
        return urlAsString;
    }

    public void setUrl(final String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    protected void append(final LoggingEvent event) {
        final String formattedEvent = this.layout.format(event);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setConnectTimeout(this.connectTimeoutMs);
            connection.setReadTimeout(this.readTimeoutMs);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-type", "application/json");
            try (final OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(formattedEvent.getBytes());
            }
            final int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode <= 299) {
                return;
            }
            this.errorHandler.error("Server responded with unexpected status code: " + responseCode);
        } catch (IOException e) {
            this.errorHandler.error("Cannot send data to " + this.url, (Exception) e, -1);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
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
