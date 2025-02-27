package controller;

import service.RoomService;
import domain.Room;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class RoomJoinController implements Controller {
    private final RoomService roomService = new RoomService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
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

        Optional<Room> room = roomService.getRoomByInviteCode(inviteCode);
        if (room.isPresent()) {
            session.setAttribute("roomId", room.get().getId());
            jsonResponse.put("success", true);
            jsonResponse.put("roomId", room.get().getId());
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "초대 코드가 올바르지 않습니다.");
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
