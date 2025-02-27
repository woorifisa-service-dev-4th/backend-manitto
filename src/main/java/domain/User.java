package domain;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String profileImage; // 🔹 프로필 이미지 추가
    private Timestamp createdAt;

    public User(int id, String name, String email, String password, String profileImage, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
