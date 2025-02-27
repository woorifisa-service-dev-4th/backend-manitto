package service;

import domain.RoomParticipants;
import repository.RoomParticipantsRepository;
import java.util.List;

public class RoomParticipantsService {
    private final RoomParticipantsRepository participantsRepository = new RoomParticipantsRepository();

    // 방 참가자 추가
    public void addParticipant(RoomParticipants participant) {
        participantsRepository.save(participant);
    }

    // 특정 방의 참가자 목록 조회
    public List<RoomParticipants> getParticipantsByRoomId(Long roomId) {
        return participantsRepository.findByRoomId(roomId);
    }

    public List<RoomParticipants> getRoomsByUserId(Long userId) {
        return participantsRepository.findRoomsByUserId(userId);
    }

}
