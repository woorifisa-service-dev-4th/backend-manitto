package controller;

import service.RoomParticipantsService;
import domain.RoomParticipants;
import domain.User;
import service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomParticipantsController extends BaseController {
    private final RoomParticipantsService participantsService = new RoomParticipantsService();
    private final UserService userService = new UserService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roomIdParam = request.getParameter("roomId");
        if (roomIdParam == null || roomIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"roomId가 필요합니다.\"}");
            return;
        }

        Long roomId;
        try {
            roomId = Long.parseLong(roomIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"유효한 roomId가 아닙니다.\"}");
            return;
        }

        List<RoomParticipants> participants = participantsService.getParticipantsByRoomId(roomId);
        JSONArray jsonArray = new JSONArray();

        for (RoomParticipants participant : participants) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", participant.getUserId());
            jsonObject.put("isMatched", participant.getIsMatched());

            User user = userService.getUserById(participant.getUserId());
            jsonObject.put("username", user != null ? user.getName() : "알 수 없음");
            jsonArray.put(jsonObject);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("participants", jsonArray);
        writeJsonResponse(response, jsonResponse);
    }
}
