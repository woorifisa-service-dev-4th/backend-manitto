package service;

import domain.Room;
import repository.RoomRepository;
import java.util.Optional;
import java.util.UUID;

public class RoomService {
    private final RoomRepository roomRepository = new RoomRepository();

    public Room createRoom(int hostId) {
        String inviteCode = UUID.randomUUID().toString().substring(0, 8); // 초대 코드 생성
        String status = "WAITING"; // 기본 상태

        Room newRoom = new Room(hostId, inviteCode, status);
        roomRepository.save(newRoom);
        return newRoom;
    }

    public Optional<Room> getRoomById(int roomId) {
        return Optional.ofNullable(roomRepository.findById(roomId));
    }

    public Optional<Room> getRoomByInviteCode(String inviteCode) {
        return roomRepository.findByInviteCode(inviteCode);
    }
}
