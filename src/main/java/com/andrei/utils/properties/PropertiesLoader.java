package com.andrei.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    /**
     * Reads the configuration file ("application.yaml" or "application.properties") and converts the properties to variables.
     * @param resourceFileName file path
     * @exception RuntimeException malformed YAML
     */
    public static Properties loadProperties(String resourceFileName){
        Properties configuration = new Properties();

        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);

        try {
            configuration.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return configuration;
    }
}
