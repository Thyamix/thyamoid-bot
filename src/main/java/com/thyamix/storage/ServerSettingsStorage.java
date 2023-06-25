package com.thyamix.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thyamix.model.ServerSettings;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerSettingsStorage {
    private static final Path SETTINGS_PATH = Path.of("server_settings.json");

    private static final Gson GSON = new Gson();

    public void save(@NotNull ServerSettings settings) {
        JsonObject json = new JsonObject();
        json.addProperty("logChannelId", settings.getLogChannelId());
        json.addProperty("loggerEnabled", settings.getLoggerEnabled());

        try (Writer writer = new FileWriter(SETTINGS_PATH.toFile())) {
            this.createPathIfNotExists();
            GSON.toJson(json, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull ServerSettings load() {
        if (Files.notExists(SETTINGS_PATH)) {
            return new ServerSettings(-1, false);
        }

        try (BufferedReader reader = Files.newBufferedReader(SETTINGS_PATH)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            long logChannelId = json.get("logChannelId").getAsLong();
            boolean loggerEnabled = json.get("loggerEnabled").getAsBoolean();

            return new ServerSettings(logChannelId, loggerEnabled);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPathIfNotExists() throws IOException {
        if (Files.exists(SETTINGS_PATH)) return;

        Files.createFile(SETTINGS_PATH);
    }
}
