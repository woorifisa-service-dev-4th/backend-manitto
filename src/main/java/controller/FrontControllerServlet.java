package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/*")  // 모든 /api/* 요청을 처리
public class FrontControllerServlet extends HttpServlet {
    private final Map<String, Controller> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        controllerMap.put("/api/login", new LoginController());
        controllerMap.put("/api/signup", new SignupController());
        controllerMap.put("/api/room/create", new RoomCreateController());
        controllerMap.put("/api/room/join", new RoomJoinController());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        Controller controller = controllerMap.get(path);

        if (controller != null) {
            controller.process(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청을 처리할 수 없습니다.");
        }
    }
}
