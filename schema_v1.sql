-- 데이터베이스 생성 (필요 시)
CREATE DATABASE IF NOT EXISTS manitto_chat DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE manitto_chat;

-- 사용자 테이블
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 채팅방 테이블
CREATE TABLE room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user1_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 채팅 메시지 테이블
CREATE TABLE chat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT NOT NULL,
    sender_id INT NOT NULL,
    message TEXT NOT NULL,
    message_type ENUM('TEXT', 'IMAGE', 'FILE') NOT NULL DEFAULT 'TEXT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 마니또 매칭 테이블
CREATE TABLE manitto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    manitto_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (manitto_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 알림 테이블
CREATE TABLE notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type ENUM('MESSAGE', 'MATCHING', 'ROOM_INVITE') NOT NULL,
    content TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 읽음 상태 테이블
CREATE TABLE read_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chat_id INT NOT NULL,
    user_id INT NOT NULL,
    read_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chat_id) REFERENCES chat(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 인덱스 최적화 (조회 성능 향상)
CREATE INDEX idx_chat_room ON chat (room_id);
CREATE INDEX idx_chat_sender ON chat (sender_id);
CREATE INDEX idx_notification_user ON notification (user_id);
CREATE INDEX idx_read_status_user ON read_status (user_id);
