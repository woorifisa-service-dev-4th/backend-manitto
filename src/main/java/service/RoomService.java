package service;

import domain.Room;
import repository.RoomRepository;
import java.util.Optional;

public class RoomService {
    private final RoomRepository roomRepository = new RoomRepository();

    public Room createRoom(int user1Id, int user2Id) {
        Room newRoom = new Room(user1Id, user2Id);
        roomRepository.save(newRoom);
        return newRoom;
    }

    public Optional<Room> getRoomById(int roomId) {
        return Optional.ofNullable(roomRepository.findById(roomId));
    }

    public boolean joinRoom(int roomId, int userId) {
        Optional<Room> roomOpt = getRoomById(roomId);

        if (roomOpt.isEmpty()) {
            return false; // 방이 존재하지 않음
        }

        Room room = roomOpt.get();
        if (room.getUser2Id() == 0) { // user2가 없을 경우
            roomRepository.save(new Room(room.getUser1Id(), userId));
            return true;
        } else {
            return false; // 방이 이미 가득 참
        }
    }
}
