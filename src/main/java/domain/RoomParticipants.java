package domain;

import java.sql.Timestamp;

public class RoomParticipants {
    private Long id;
    private Long roomId;
    private Long userId;
    private Boolean isMatched;
    private Timestamp createdAt;

    public RoomParticipants(Long id, Long roomId, Long userId, Boolean isMatched, Timestamp createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.isMatched = isMatched;
        this.createdAt = createdAt;
    }

    // 생성자 (매칭 안 된 상태로 기본값 설정)
    public RoomParticipants(Long roomId, Long userId) {
        this.roomId = roomId;
        this.userId = userId;
        this.isMatched = false; // 기본값
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getter & Setter
    public Long getId() { return id; }
    public Long getRoomId() { return roomId; }
    public Long getUserId() { return userId; }
    public Boolean getIsMatched() { return isMatched; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setIsMatched(Boolean isMatched) { this.isMatched = isMatched; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
