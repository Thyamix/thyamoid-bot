package com.thyamix.model;

public class ServerSettings {
    private long logChannelId;
    private boolean loggerEnabled;

    public ServerSettings(long logChannelId, boolean loggerEnabled) {
        this.logChannelId = logChannelId;
        this.loggerEnabled = loggerEnabled;
    }

    public void setLogChannelId(long logChannelId) {
        this.logChannelId = logChannelId;
    }

    /**
     * @return -1 if not present or the channel ID to send logs to
     */
    public long getLogChannelId() {
        return this.logChannelId;
    }

    public boolean hasLogChannelId() {
        return this.logChannelId != 1;
    }

    public void setLoggerEnabled(boolean loggerEnabled) {
        this.loggerEnabled = loggerEnabled;
    }

    public boolean getLoggerEnabled() {
        return this.loggerEnabled;
    }

}
