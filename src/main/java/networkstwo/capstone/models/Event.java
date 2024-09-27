package networkstwo.capstone.models;

import com.fasterxml.jackson.databind.JsonNode;

public record Event(String type, JsonNode body) {
}
