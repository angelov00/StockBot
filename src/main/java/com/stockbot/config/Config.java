package com.stockbot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final Properties properties;

    private static final String CONFIG_FILE_PATH = "src/main/resources/application.properties";

    public Config() {
        properties = new Properties();
        loadProperties();
    }

    public Config(String configFilePath) {
        properties = new Properties();
        loadProperties(configFilePath);
    }

    private void loadProperties() {
        loadProperties(CONFIG_FILE_PATH);
    }

    private void loadProperties(String configFilePath) {
        try (InputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot open config file: " + configFilePath, ex);
        }
    }

    public String getDiscordBotToken() {
        return properties.getProperty("discord.bot.token");
    }

    public String getFinnhubApiKey() {
        return properties.getProperty("finnhub.api.key");
    }
}
