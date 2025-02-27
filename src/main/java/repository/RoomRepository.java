package repository;

import domain.Room;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    public void save(Room room) {
        String sql = "INSERT INTO rooms (user1_id, user2_id) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, room.getUser1Id());
            stmt.setInt(2, room.getUser2Id());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Room findById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Room(
                        rs.getInt("id"),
                        rs.getInt("user1_id"),
                        rs.getInt("user2_id"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getInt("user1_id"),
                        rs.getInt("user2_id"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
