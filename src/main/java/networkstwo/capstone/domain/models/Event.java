package networkstwo.capstone.domain.models;

import com.fasterxml.jackson.databind.JsonNode;

public record Event(String type, JsonNode body) {
}
