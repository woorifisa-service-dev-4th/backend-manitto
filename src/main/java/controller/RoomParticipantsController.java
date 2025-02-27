package controller;

import service.RoomParticipantsService;
import domain.RoomParticipants;
import domain.User; // 유저 정보 포함하기 위해 추가
import service.UserService; // 유저 정보를 가져오기 위한 서비스 추가
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomParticipantsController implements Controller {
    private final RoomParticipantsService participantsService = new RoomParticipantsService();
    private final UserService userService = new UserService(); // 사용자 정보 조회 서비스 추가

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ✅ `roomId` 요청 파라미터 검증
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

        // ✅ 참가자 목록 조회
        List<RoomParticipants> participants = participantsService.getParticipantsByRoomId(roomId);
        JSONArray jsonArray = new JSONArray();

        for (RoomParticipants participant : participants) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", participant.getUserId());
            jsonObject.put("isMatched", participant.getIsMatched());

            // ✅ 유저 닉네임 가져오기
            User user = userService.getUserById(participant.getUserId());
            if (user != null) {
                jsonObject.put("username", user.getName()); // 사용자 이름 추가
            } else {
                jsonObject.put("username", "알 수 없음");
            }

            jsonArray.put(jsonObject);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("participants", jsonArray);

        response.getWriter().write(jsonResponse.toString());
    }
}
