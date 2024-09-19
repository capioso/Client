package networkstwo.capstone.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import networkstwo.capstone.messages.Message;

public class MessageSender {
    public static JsonNode getResponse(Message message) {
        try {
            ServerConnection serverConnection = ServerConnection.getInstance();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(message);
            serverConnection.sendMessage(jsonMessage);
            return serverConnection.getResponse();
        } catch (Exception e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", "Error: " + e.getMessage());
            return errorNode;
        }
    }
}
