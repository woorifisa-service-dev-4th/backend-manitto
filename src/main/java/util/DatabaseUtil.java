package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3309/manitto_chat?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";  // MySQL 사용자명
    private static final String JDBC_PASSWORD = "root"; // MySQL 비밀번호

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL 드라이버 로드
            initializeDatabase();  // 테이블 자동 생성
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL 드라이버를 찾을 수 없습니다!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // 사용자 테이블 (users)
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " + // UNIQUE 제거
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "profile_image TEXT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

            // 채팅방 테이블 (rooms)
            stmt.execute("CREATE TABLE IF NOT EXISTS rooms (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "host_id BIGINT NOT NULL, " +
                    "invite_code VARCHAR(255) NOT NULL UNIQUE, " +
                    "status ENUM('WAITING', 'MATCHED', 'CLOSED') NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "expired_at TIMESTAMP NULL, " +
                    "FOREIGN KEY (host_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

            // 채팅 메시지 테이블 (chats)
            stmt.execute("CREATE TABLE IF NOT EXISTS chats (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "room_id BIGINT NOT NULL, " +
                    "sender_id BIGINT NOT NULL, " +
                    "receiver_id BIGINT NOT NULL, " + // 수신자 추가
                    "message TEXT NOT NULL, " +
                    "is_read BOOLEAN DEFAULT FALSE, " +
                    "sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

        } catch (SQLException e) {
            throw new RuntimeException("MySQL 데이터베이스 초기화 중 오류 발생!", e);
        }
    }
}
