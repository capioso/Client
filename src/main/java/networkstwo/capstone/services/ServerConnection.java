package networkstwo.capstone.services;

import networkstwo.capstone.config.ServerConfig;
import networkstwo.capstone.config.SslConfig;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class ServerConnection {
    private static ServerConnection instance;
    private final ServerConfig connector;
    private final SslConfig sslConfig;

    private ServerConnection(String host, int port, String trustStorePath, String trustStorePassword) {
        this.sslConfig = new SslConfig(trustStorePath, trustStorePassword);
        this.connector = new ServerConfig(host, port);
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection("34.130.54.17", 10852, "clienttruststore.jks", "J~aBG6vCQ059");
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

    public String receiveMessage() throws IOException {
        return connector.receiveMessage();
    }

    public void closeSocket() {
        connector.closeSocket();
    }
}
