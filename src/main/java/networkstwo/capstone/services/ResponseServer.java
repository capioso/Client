package networkstwo.capstone.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import networkstwo.capstone.messages.Message;

import java.io.IOException;

public class ResponseServer {
    public static String getResponse(Message message) throws IOException {
        try {
            ServerConnection serverConnection = ServerConnection.getInstance();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(message);
            serverConnection.sendMessage(jsonMessage);

            return serverConnection.receiveMessage();
        }catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
