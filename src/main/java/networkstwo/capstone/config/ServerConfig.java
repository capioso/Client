package networkstwo.capstone.config;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConfig {
    private final String host;
    private final int port;
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect(SSLSocketFactory sslSocketFactory) {
        try {
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
