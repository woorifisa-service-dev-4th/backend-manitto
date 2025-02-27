package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/*")
public class FrontControllerServlet extends HttpServlet {
    private final Map<String, Controller> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        controllerMap.put("/api/login", new LoginController());
        controllerMap.put("/api/signup", new SignupController());
        controllerMap.put("/api/room/create", new RoomCreateController());
        controllerMap.put("/api/room/join", new RoomJoinController());
        controllerMap.put("/api/user/rooms", new UserRoomsController());
        controllerMap.put("/api/room/participants", new RoomParticipantsController());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        String relativePath = path.substring(contextPath.length());

        Controller controller = controllerMap.get(relativePath);
        if (controller != null) {
            controller.process(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청을 처리할 수 없습니다.");
        }
    }
}
