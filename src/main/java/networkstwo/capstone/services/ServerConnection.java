package networkstwo.capstone.services;

import com.fasterxml.jackson.databind.JsonNode;
import networkstwo.capstone.config.ServerConfig;
import networkstwo.capstone.config.SslConfig;
import networkstwo.capstone.utils.PropertiesUtils;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class ServerConnection {
    private static ServerConnection instance;
    private final ServerConfig connector;
    private final SslConfig sslConfig;
    private static final PropertiesUtils PROPERTIES_UTILS;

    static {
        try {
            PROPERTIES_UTILS = PropertiesUtils.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ServerConnection(String host, int port, String trustStorePath, String trustStorePassword) {
        this.sslConfig = new SslConfig(trustStorePath, trustStorePassword);
        this.connector = new ServerConfig(host, port);
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection(
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

    public Socket getSocket() {
        return connector.getSocket();
    }

    public void sendMessage(String message) {
        connector.sendMessage(message);
    }
    public JsonNode getResponse() throws InterruptedException {
        return connector.getNextResponse();
    }

    public void closeSocket() {
        connector.closeSocket();
    }
}
