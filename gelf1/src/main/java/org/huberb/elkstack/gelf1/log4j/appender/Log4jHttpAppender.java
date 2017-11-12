/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.elkstack.gelf1.log4j.appender;

import java.io.OutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.apache.log4j.spi.LoggingEvent;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.AppenderSkeleton;

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

    public boolean requiresLayout() {
        return true;
    }

    public void close() {
    }
}
