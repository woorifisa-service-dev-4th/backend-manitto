package repository;

import domain.RoomParticipants;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomParticipantsRepository {

    // 참가자 추가
    public void save(RoomParticipants participant) {
        String sql = "INSERT INTO room_participants (room_id, user_id, is_matched, created_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, participant.getRoomId());
            stmt.setLong(2, participant.getUserId());
            stmt.setBoolean(3, participant.getIsMatched());
            stmt.setTimestamp(4, participant.getCreatedAt());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 방의 참가자 조회
    public List<RoomParticipants> findByRoomId(Long roomId) {
        String sql = "SELECT * FROM room_participants WHERE room_id = ?";
        List<RoomParticipants> participants = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                participants.add(new RoomParticipants(
                        rs.getLong("id"),
                        rs.getLong("room_id"),
                        rs.getLong("user_id"),
                        rs.getBoolean("is_matched"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public List<RoomParticipants> findRoomsByUserId(Long userId) {
        String sql = "SELECT * FROM room_participants WHERE user_id = ?";
        List<RoomParticipants> rooms = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                rooms.add(new RoomParticipants(
                        rs.getLong("id"),
                        rs.getLong("room_id"),
                        rs.getLong("user_id"),
                        rs.getBoolean("is_matched"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

}
