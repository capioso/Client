package networkstwo.capstone.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import networkstwo.capstone.services.EventBus;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerConfig {
    private final String host;
    private final int port;
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final BlockingQueue<JsonNode> responseQueue = new LinkedBlockingQueue<>();

    public ServerConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect(SSLSocketFactory sslSocketFactory) {
        try {
            socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startListening();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private void startListening() {
        new Thread(() -> {
            try {
                String response;
                ObjectMapper objectMapper = new ObjectMapper();
                while ((response = in.readLine()) != null) {
                    JsonNode node = objectMapper.readTree(response);
                    responseQueue.offer(node);
                    if (node.get("title").asText().equals("update")){
                        String operation = node.get("body").asText();
                        EventBus.getInstance().sendMessage(operation);
                    }

                }
            } catch (IOException e) {
                System.out.println("Error while listening: " + e.getMessage());
            }
        }).start();
    }

    public JsonNode getNextResponse() throws InterruptedException {
        return responseQueue.take();
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
