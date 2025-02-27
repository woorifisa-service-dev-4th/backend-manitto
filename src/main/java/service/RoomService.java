package service;

import domain.Room;
import repository.RoomRepository;
import java.util.Optional;
import java.util.UUID;

public class RoomService {
    private final RoomRepository roomRepository = new RoomRepository();

    public Room createRoom(int hostId) {
        String inviteCode = generateInviteCode();
        String status = "WAITING"; // 기본 상태

        Room newRoom = new Room(hostId, inviteCode, status);
        try {
            roomRepository.save(newRoom);
        } catch (Exception e) {
            System.err.println("방 생성 실패: " + e.getMessage());
            // 필요에 따라 예외 처리 로직 추가
        }
        return newRoom;
    }

    // 초대 코드 생성 로직 분리
    private String generateInviteCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Optional<Room> getRoomById(int roomId) {
        return Optional.ofNullable(roomRepository.findById(roomId));
    }

    public Optional<Room> getRoomByInviteCode(String inviteCode) {
        return roomRepository.findByInviteCode(inviteCode);
    }
}
