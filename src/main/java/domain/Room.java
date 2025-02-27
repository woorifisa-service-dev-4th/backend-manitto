package domain;

import java.sql.Timestamp;

public class Room {
    private int id;
    private int hostId;  // user1Id → hostId
    private String inviteCode; // 초대 코드 추가
    private String status; // 상태 추가
    private Timestamp createdAt;
    private Timestamp expiredAt;

    public Room(int id, int hostId, String inviteCode, String status, Timestamp createdAt, Timestamp expiredAt) {
        this.id = id;
        this.hostId = hostId;
        this.inviteCode = inviteCode;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public Room(int hostId, String inviteCode, String status) {
        this.hostId = hostId;
        this.inviteCode = inviteCode;
        this.status = status;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public int getHostId() {
        return hostId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }
}
