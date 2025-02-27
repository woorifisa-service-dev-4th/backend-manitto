package controller;

import service.RoomService;
import service.RoomParticipantsService;
import domain.Room;
import domain.RoomParticipants;
import org.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class RoomJoinController extends BaseController {
    private final RoomService roomService = new RoomService();
    private final RoomParticipantsService participantsService = new RoomParticipantsService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        JSONObject jsonResponse = new JSONObject();

        JSONObject jsonRequest = parseJsonRequest(request);
        String inviteCode = jsonRequest.optString("inviteCode", "");

        Optional<Room> room = roomService.getRoomByInviteCode(inviteCode);
        if (room.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "초대 코드가 올바르지 않습니다.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJsonResponse(response, jsonResponse);
            return;
        }

        Long roomId = (long) room.get().getId();
        Long userId = getUserIdFromSession(request);
        if (userId == null) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "로그인이 필요합니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            writeJsonResponse(response, jsonResponse);
            return;
        }

        RoomParticipants participant = new RoomParticipants(roomId, userId);
        participantsService.addParticipant(participant);

        session.setAttribute("roomId", roomId);
        jsonResponse.put("success", true);
        jsonResponse.put("roomId", roomId);
        writeJsonResponse(response, jsonResponse);
    }
}
