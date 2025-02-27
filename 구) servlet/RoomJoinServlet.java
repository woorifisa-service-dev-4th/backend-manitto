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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/api/room/join") // 🔹 방 참가 엔드포인트
public class RoomJoinServlet extends HttpServlet {
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

        // JSON 데이터 읽기
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

        if (inviteCode.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "초대 코드가 필요합니다.");
        } else {
            Optional<Room> room = roomService.getRoomByInviteCode(inviteCode);
            if (room.isPresent()) {
                session.setAttribute("roomId", room.get().getId()); // 🔹 참가한 방 ID 저장
                jsonResponse.put("success", true);
                jsonResponse.put("roomId", room.get().getId());
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "초대 코드가 올바르지 않습니다.");
            }
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
