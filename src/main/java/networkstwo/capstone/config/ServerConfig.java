package networkstwo.capstone.config;

import com.fasterxml.jackson.databind.JsonNode;
import networkstwo.capstone.utils.PropertiesUtils;

import javax.net.ssl.SSLSocketFactory;

public class ServerConfig {
    private static ServerConfig instance;
    private final ServerConnection connector;
    private final SslConfig sslConfig;
    private static final PropertiesUtils PROPERTIES_UTILS;

    static {
        try {
            PROPERTIES_UTILS = PropertiesUtils.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ServerConfig(String host, int port, String trustStorePath, String trustStorePassword) {
        this.sslConfig = new SslConfig(trustStorePath, trustStorePassword);
        this.connector = new ServerConnection(host, port);
    }

    public static ServerConfig getInstance() {
        if (instance == null) {
            instance = new ServerConfig(
                    PROPERTIES_UTILS.getServerHost(),
                    PROPERTIES_UTILS.getSocketPort(),
                    PROPERTIES_UTILS.getKeystorePath(),
                    PROPERTIES_UTILS.getKeystorePass());
        }
        return instance;
    }

    public boolean ping() {
        try {
            SSLSocketFactory sslSocketFactory = sslConfig.createSocketFactory();
            return connector.connect(sslSocketFactory);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void sendMessage(Object message) {
        connector.sendMessage(message);
    }

    public JsonNode getResponse() throws InterruptedException {
        return connector.getNextResponse();
    }
}
