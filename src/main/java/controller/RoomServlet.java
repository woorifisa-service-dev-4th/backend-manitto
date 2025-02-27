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

@WebServlet("/api/room")
public class RoomServlet extends HttpServlet {
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
        String action = jsonRequest.getString("action");
        JSONObject jsonResponse = new JSONObject();

        if ("create".equals(action)) {
            int user1Id = jsonRequest.getInt("user1Id");
            int user2Id = jsonRequest.getInt("user2Id");
            Room newRoom = roomService.createRoom(user1Id, user2Id);
            jsonResponse.put("success", true);
            jsonResponse.put("roomId", newRoom.getId());
        } else if ("join".equals(action)) {
            int roomId = jsonRequest.getInt("roomId");
            int userId = jsonRequest.getInt("userId");
            boolean success = roomService.joinRoom(roomId, userId);

            jsonResponse.put("success", success);
            if (!success) {
                jsonResponse.put("message", "방 참가 실패! 방이 가득 찼거나 존재하지 않습니다.");
            }
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
