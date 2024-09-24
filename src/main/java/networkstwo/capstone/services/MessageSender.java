package networkstwo.capstone.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import networkstwo.capstone.config.ServerConfig;

public class MessageSender {
    public static JsonNode getResponse(Object message) {
        try {
            ServerConfig serverConfig = ServerConfig.getInstance();
            serverConfig.sendMessage(message);
            return serverConfig.getResponse();
        } catch (Exception e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", "Error: " + e.getMessage());
            return errorNode;
        }
    }
}
