package networkstwo.capstone.services;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private final String host;
    private final int port;

    public ServerConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean ping() {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
