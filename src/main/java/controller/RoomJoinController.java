package controller;

import service.RoomService;
import service.RoomParticipantsService;
import domain.Room;
import domain.RoomParticipants;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class RoomJoinController implements Controller {
    private final RoomService roomService = new RoomService();
    private final RoomParticipantsService participantsService = new RoomParticipantsService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // ✅ 요청 본문 읽기 (inviteCode 확인)
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        JSONObject jsonRequest = new JSONObject(requestBody.toString());
        String inviteCode = jsonRequest.optString("inviteCode", "");

        JSONObject jsonResponse = new JSONObject();

        // ✅ 초대 코드로 방 찾기
        Optional<Room> room = roomService.getRoomByInviteCode(inviteCode);
        if (room.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "초대 코드가 올바르지 않습니다.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        Long roomId = (long) room.get().getId(); // 🔥 int → long 변환

        // ✅ 세션에서 userId 가져오기 (Integer 타입이면 Long으로 변환)
        Object userIdObj = session.getAttribute("userId");
        Long userId = null;
        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        }

        // ✅ userId가 null이면 401 에러 반환
        if (userId == null) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "로그인이 필요합니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        // ✅ 유저를 대기방에 추가
        RoomParticipants participant = new RoomParticipants(roomId, userId);
        participantsService.addParticipant(participant);

        session.setAttribute("roomId", roomId);
        jsonResponse.put("success", true);
        jsonResponse.put("roomId", roomId);

        response.getWriter().write(jsonResponse.toString());
    }
}
