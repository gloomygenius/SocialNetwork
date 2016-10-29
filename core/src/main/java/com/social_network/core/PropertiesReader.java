package com.social_network.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("WeakerAccess")
public class PropertiesReader {
    private final String PATH;

    public PropertiesReader(String path) {
        PATH = path;
    }

    public static String getProperty(String path, String key) throws RuntimeException {
        String value = null;

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path))) {
            Properties properties = new Properties();
            properties.load(stream);

            try {
                value = properties.getProperty(key);
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return value;
    }
}