package service;

import domain.RoomParticipants;
import repository.RoomParticipantsRepository;
import java.util.List;

public class RoomParticipantsService {
    private final RoomParticipantsRepository participantsRepository = new RoomParticipantsRepository();

    // 방 참가자 추가
    public void addParticipant(RoomParticipants participant) {
        try {
            participantsRepository.save(participant);
        } catch (Exception e) {
            System.err.println("방 참가자 추가 실패: " + e.getMessage());
            // 필요 시 예외 재발행 또는 추가 로깅
        }
    }

    // 특정 방의 참가자 목록 조회
    public List<RoomParticipants> getParticipantsByRoomId(Long roomId) {
        return participantsRepository.findByRoomId(roomId);
    }

    // 유저가 참여한 방 목록 조회
    public List<RoomParticipants> getRoomsByUserId(Long userId) {
        return participantsRepository.findRoomsByUserId(userId);
    }
}
