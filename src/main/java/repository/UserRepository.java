package repository;

import domain.User;
import util.DatabaseUtil;
import java.sql.*;

public class UserRepository {

    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"; // created_at 제거

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());  // name 컬럼과 매칭
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // 3개만 설정 (created_at X)

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ 회원가입 성공: " + user.getEmail());
            } else {
                System.out.println("❌ 회원가입 실패: " + user.getEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";  // users로 변경

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("유저 조회 성공: " + email);
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"), // name 컬럼으로 변경
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at")
                );
            } else {
                System.out.println("유저 조회 실패: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User findById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
