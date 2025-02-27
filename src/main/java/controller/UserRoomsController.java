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

public class UserRoomsController extends BaseController {
    private final RoomParticipantsService participantsService = new RoomParticipantsService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Long userId = getUserIdFromSession(request);

        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"로그인이 필요합니다.\"}");
            return;
        }

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
        writeJsonResponse(response, jsonResponse);
    }
}
