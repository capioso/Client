package networkstwo.capstone.services;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

public class ServerConnection {
    private static ServerConnection instance;
    private final String host;
    private final int port;
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;

    private ServerConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection("34.130.54.17", 10852);
        }
        return instance;
    }

    public boolean ping() {
        try {
            String trustStorePath = "clienttruststore.jks";
            String trustStorePassword = "J~aBG6vCQ059";

            KeyStore trustStore = KeyStore.getInstance("JKS");
            try (FileInputStream trustStoreInput = new FileInputStream(trustStorePath)) {
                trustStore.load(trustStoreInput, trustStorePassword.toCharArray());
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public String receiveMessage() throws IOException {
        if (in != null) {
            return in.readLine();
        }
        return null;
    }

    public void closeSocket() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
