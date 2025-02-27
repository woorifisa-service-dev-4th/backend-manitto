package controller;

import service.RoomService;
import domain.Room;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/room/create") // 🔹 방 생성 엔드포인트
public class RoomCreateServlet extends HttpServlet {
    private final RoomService roomService = new RoomService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        // 🔹 userId가 세션에 없으면 오류 방지
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "세션에 사용자 정보가 없습니다.");
            return;
        }

        int hostId = (int) userIdObj; // 🔹 userId가 null이 아님이 확인된 후 캐스팅
        JSONObject jsonResponse = new JSONObject();

        Room newRoom = roomService.createRoom(hostId);
        jsonResponse.put("success", true);
        jsonResponse.put("roomId", newRoom.getId());
        jsonResponse.put("inviteCode", newRoom.getInviteCode());

        response.getWriter().write(jsonResponse.toString());
    }
}
