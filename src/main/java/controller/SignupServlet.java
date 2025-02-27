package controller;

import service.UserService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/signup")
public class SignupServlet extends HttpServlet {
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
        String username = jsonRequest.getString("username");
        String email = jsonRequest.getString("email");
        String password = jsonRequest.getString("password");

        JSONObject jsonResponse = new JSONObject();
        if (userService.signup(username, email, password)) {
            jsonResponse.put("success", true);
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "회원가입 실패! 이메일이 중복되었거나 유효하지 않습니다.");
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
