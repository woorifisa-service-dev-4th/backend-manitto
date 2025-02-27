package domain;

import java.sql.Timestamp;

public class Room {
    private int id;
    private int user1Id;
    private int user2Id;
    private Timestamp createdAt;

    public Room(int id, int user1Id, int user2Id, Timestamp createdAt) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.createdAt = createdAt;
    }

    public Room(int user1Id, int user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
