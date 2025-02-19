package service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import domain.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.RoomRepository;
import util.RoomCodeGenerator;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomCodeGenerator roomCodeGenerator;

    @InjectMocks
    private RoomService roomService;

    @Test
    public void testCreateRoom() {
        // given
        String roomName = "Test Room";
        int maxParticipants = 10;
        String roomCode = "123456";
        String encryptedCode = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        when(roomCodeGenerator.generateRoomCode()).thenReturn(roomCode);

        Room room = new Room(roomName, maxParticipants, encryptedCode);
        doReturn(room).when(roomRepository).saveRoom(any(Room.class));

        // when
        Room createdRoom = roomService.createRoom(roomName, maxParticipants);

        // then
        assertNotNull(createdRoom);
        assertEquals(roomName, createdRoom.getName());
        assertEquals(maxParticipants, createdRoom.getMaxParticipants());
        assertEquals(encryptedCode, createdRoom.getJoinCode());
    }
}