package controller;

import service.RoomService;
import domain.Room;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class RoomCreateController implements Controller {
    private final RoomService roomService = new RoomService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");

        if (userIdObj == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        int hostId = (int) userIdObj;
        JSONObject jsonResponse = new JSONObject();

        Room newRoom = roomService.createRoom(hostId);
        jsonResponse.put("success", true);
        jsonResponse.put("roomId", newRoom.getId());
        jsonResponse.put("inviteCode", newRoom.getInviteCode());

        response.getWriter().write(jsonResponse.toString());
    }
}
