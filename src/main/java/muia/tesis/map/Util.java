package muia.tesis.map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util{
    /**
     * Loads a configuration file into a Map.
     *
     * @param fileName   The name of the properties file to load.
     * @param classLoad  The class used to get the ClassLoader for loading the file.
     * @return A Map containing the key-value pairs from the properties file.
     * @throws IllegalArgumentException if the file is not found or cannot be loaded.
     */

    public static Map<String, String> loadConfig(String fileName,Class<?> classLoad){

        Properties prop = new Properties();
        Map<String, String> config = new HashMap<>();

        try {
            // Load the properties file
            if (classLoad.getClassLoader().getResourceAsStream(fileName) == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            prop.load(classLoad.getClassLoader().getResourceAsStream(fileName));

            // Convert properties to Map
            for (String key : prop.stringPropertyNames()) {
                config.put(key, prop.getProperty(key));
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Error loading configuration file: " + fileName, ex);
        }

        return config;
    }
}



