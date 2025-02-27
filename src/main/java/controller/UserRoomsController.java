package controller;

import service.RoomParticipantsService;
import domain.RoomParticipants;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserRoomsController implements Controller {
    private final RoomParticipantsService participantsService = new RoomParticipantsService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");
        Long userId = null;

        // ✅ 세션에서 userId 가져오기 (Integer → Long 변환)
        if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        }

        // ✅ userId가 없으면 로그인 필요
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"로그인이 필요합니다.\"}");
            return;
        }

        // ✅ 해당 유저가 참가한 방 목록 가져오기
        List<RoomParticipants> userRooms = participantsService.getRoomsByUserId(userId);

        JSONArray jsonArray = new JSONArray();
        for (RoomParticipants room : userRooms) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomId", room.getRoomId());
            jsonArray.put(jsonObject);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("rooms", jsonArray);

        response.getWriter().write(jsonResponse.toString());
    }
}
