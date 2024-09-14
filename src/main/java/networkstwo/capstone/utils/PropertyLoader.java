package networkstwo.capstone.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static PropertyLoader instance;
    private final Properties properties;


    private PropertyLoader() {
        properties = new Properties();
        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream(".properties")) {
            if (input == null) {
                throw new IOException("Sorry, unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Error loading config.properties: " + e.getMessage());
        }
    }

    public static synchronized PropertyLoader getInstance() {
        if (instance == null) {
            instance = new PropertyLoader();
        }
        return instance;
    }

    public String getServerHost() {
        return properties.getProperty("SERVER_HOST");
    }

    public int getSocketPort() {
        return Integer.parseInt(properties.getProperty("SOCKET_PORT"));
    }

    public String getKeystorePath() {
        return properties.getProperty("KEYSTORE_PATH");
    }

    public String getKeystorePass() {
        return properties.getProperty("KEYSTORE_PASS");
    }
}
