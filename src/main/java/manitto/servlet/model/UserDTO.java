package manitto.servlet.model;

public class UserDTO {
    private int userId;
    private String email;
    private String password;
    private String nickname;

    public UserDTO() {}

    public UserDTO(int userId, String email, String password, String nickname) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}