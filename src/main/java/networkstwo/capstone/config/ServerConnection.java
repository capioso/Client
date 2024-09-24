package networkstwo.capstone.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import networkstwo.capstone.models.Message;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import networkstwo.capstone.models.Event;
import networkstwo.capstone.services.EventBus;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerConnection {
    private final String host;
    private final int port;
    private SSLSocket socket;
    private OutputStream out;
    private InputStream in;
    private final BlockingQueue<JsonNode> responseQueue = new LinkedBlockingQueue<>();
    private final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    public ServerConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect(SSLSocketFactory sslSocketFactory) {
        try {
            socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
            this.out = socket.getOutputStream();
            this.in = socket.getInputStream();
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
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    byte[] serverResponseBytes = Arrays.copyOf(buffer, bytesRead);;
                    JsonNode node = objectMapper.readTree(serverResponseBytes);
                    String title = node.get("title").asText();
                    JsonNode bodyNode = node.path("body");
                    switch (title) {
                        case "chatUpdate" -> EventBus.getInstance().sendEvent(new Event("chatUpdate", bodyNode));
                        case "messageUpdate" -> EventBus.getInstance().sendEvent(new Event("messageUpdate", bodyNode));
                        default -> responseQueue.offer(node);
                    }
                }
            } catch (IOException e) {
                closeSocket();
                System.out.println("Error while listening: " + e.getMessage());
            }
        }).start();
    }

    public JsonNode getNextResponse() throws InterruptedException {
        return responseQueue.take();
    }

    public void sendMessage(Object message) {
        if (out != null) {
            try {
                byte[] responseBytes = objectMapper.writeValueAsBytes(message);
                out.write(responseBytes);
                out.flush();
            }catch (Exception e) {
                System.out.println("Error sending message: " + e.getMessage());
            }
        }
    }

    private void closeSocket() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
