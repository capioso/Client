package networkstwo.capstone.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private static PropertiesUtils instance;
    private final Properties properties;


    private PropertiesUtils() throws Exception{
        properties = new Properties();
        try (InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(".properties")) {
            if (input == null) {
                throw new IOException("Sorry, unable to find config.properties");
            }
            properties.load(input);
        }
    }

    public static synchronized PropertiesUtils getInstance() throws Exception {
        if (instance == null) {
            instance = new PropertiesUtils();
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
