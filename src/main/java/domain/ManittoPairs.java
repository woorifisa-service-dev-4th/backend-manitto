package domain;

import java.sql.Timestamp;

public class ManittoPairs {
    private Long id;
    private Long roomId;
    private Long giverId;
    private Long receiverId;
    private Timestamp revealedAt; // 마니또 공개 시간

    public ManittoPairs(Long id, Long roomId, Long giverId, Long receiverId, Timestamp revealedAt) {
        this.id = id;
        this.roomId = roomId;
        this.giverId = giverId;
        this.receiverId = receiverId;
        this.revealedAt = revealedAt;
    }

    // 생성자 (마니또 매칭 직후 공개 시간 없음)
    public ManittoPairs(Long roomId, Long giverId, Long receiverId) {
        this.roomId = roomId;
        this.giverId = giverId;
        this.receiverId = receiverId;
        this.revealedAt = null; // 기본적으로 공개되지 않음
    }

    // Getter & Setter
    public Long getId() { return id; }
    public Long getRoomId() { return roomId; }
    public Long getGiverId() { return giverId; }
    public Long getReceiverId() { return receiverId; }
    public Timestamp getRevealedAt() { return revealedAt; }

    public void setId(Long id) { this.id = id; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public void setGiverId(Long giverId) { this.giverId = giverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public void setRevealedAt(Timestamp revealedAt) { this.revealedAt = revealedAt; }
}
