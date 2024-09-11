package networkstwo.capstone.messages;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserMessage {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
