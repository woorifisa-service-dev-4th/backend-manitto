package repository;

import domain.User;
import util.DatabaseUtil;
import java.sql.*;
import java.util.Optional;

public class UserRepository {

    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"; // created_at 자동 설정됨

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    System.out.println("✅ 회원가입 성공: " + user.getEmail() + " (ID: " + userId + ")");
                }
            } else {
                System.out.println("❌ 회원가입 실패: " + user.getEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        System.out.println("🔍 [DB 조회] 이메일 찾기 요청: " + email);

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ [DB 조회 성공] 이메일 존재 확인: " + email);
                System.out.println("    🔹 ID: " + rs.getInt("id"));
                System.out.println("    🔹 Name: " + rs.getString("name"));
                System.out.println("    🔹 Email: " + rs.getString("email"));
                System.out.println("    🔹 Password: " + rs.getString("password"));

                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile_image"),
                        rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("❌ [DB 조회 실패] 해당 이메일을 찾을 수 없음: " + email);
        return Optional.empty();
    }

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?"; // 테이블명 수정

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile_image"),
                        rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findById(Long userId) {
        String sql = "SELECT id, name, email, profile_image, created_at FROM users WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        null, // 🔹 password는 반환하지 않음 (보안상 문제 방지)
                        rs.getString("profile_image"),
                        rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
