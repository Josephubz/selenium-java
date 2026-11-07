
package core;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties prop = loadProperties();

    private ConfigReader() {}


    private static Properties loadProperties() {

        Properties prop = new Properties();

        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)){
            if (input == null) throw new IllegalStateException("Configuration file not found: " + CONFIG_FILE);
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load configuration file", e);
        }
    }

    private static String resolve(String key) {
        Objects.requireNonNull(key,  "Config key cannot be null");

        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }

        String configValue = prop.getProperty(key);
        if (configValue != null && !configValue.isBlank()) {
            return configValue.trim();
        }
        return null;
    }

    public static String getString(String key,  String defaultValue) {
        String value = resolve(key);

        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static int getInt(String key, int defaultValue) {
        String value = resolve(key);
        if (value == null) return defaultValue;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Value for key: " + key + " must be integer. Actual value: " + value, e);
        }
    }

    public static int getPositiveInt(String key, int defaultValue) {
        int value = getInt(key, defaultValue);
        if (value <= 0)  throw new IllegalStateException("Value for key: " + key + " must be positive integer.");
        return value;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = resolve(key);
        if (value == null) return defaultValue;

        if (value.equalsIgnoreCase("true")) return true;
        if (value.equalsIgnoreCase("false")) return false;

        throw new IllegalStateException("Value for key: " + key + " must be boolean. Actual value: " + value);
    }

    public static Duration getDurationSeconds(String key, int defaultValue) {
        int seconds = getInt(key , defaultValue);
        return Duration.ofSeconds(seconds);
    }

    public static Duration getDurationMillis(String key, int defaultValue) {
        int millis =  getInt(key , defaultValue);
        return Duration.ofMillis(millis);
    }


    public static String getUsername () {
        return System.getenv("VALID_USERNAME");
    }

    public static String getPassword () {
        return System.getenv("PASSWORD");
    }

}
