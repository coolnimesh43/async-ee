
package org.coolnimesh.async.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

public class ConfigurationProperties {

    @Inject
    private Logger logger;

    private static ConfigurationProperties configurationProperties;

    private final String CONFIG_FILE = "/config.properties";
    private final Properties properties;

    private ConfigurationProperties() {
        this.properties = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream(CONFIG_FILE);
        try {
            properties.load(inputStream);
        }
        catch (IOException e) {
            logger.error("Error while reading properties file.");
            throw new RuntimeException();
        }
    }

    public static ConfigurationProperties getInstance() {
        if (configurationProperties == null) {
            configurationProperties = new ConfigurationProperties();
        }
        return configurationProperties;
    }

    public Object getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }
}
