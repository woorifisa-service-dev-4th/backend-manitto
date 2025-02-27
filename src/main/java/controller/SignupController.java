package controller;

import service.UserService;
import org.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupController extends BaseController {
    private final UserService userService = new UserService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonRequest = parseJsonRequest(request);
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
        writeJsonResponse(response, jsonResponse);
    }
}
