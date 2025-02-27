package controller;

import service.UserService;
import domain.User;
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

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 데이터 읽기
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        JSONObject jsonRequest = new JSONObject(requestBody.toString());
        String email = jsonRequest.getString("email");
        String password = jsonRequest.getString("password");

        JSONObject jsonResponse = new JSONObject();
        if (userService.login(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email);

            jsonResponse.put("success", true);
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "로그인 실패! 이메일 또는 비밀번호를 확인하세요.");
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
