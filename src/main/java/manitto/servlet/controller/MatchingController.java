package manitto.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class MatchingController implements Controller {
    private static final List<String> MISSIONS = Arrays.asList(
            "커피 한 잔 사주기", "작은 간식 선물하기", "좋아하는 음료 사주기", "귀여운 포스트잇에 응원 메시지 남기기",
            "칭찬 한 마디 하기", "마니또가 한 일에 대해 감사 표현하기", "따뜻한 인사 먼저 건네기", "진심 어린 응원의 한 마디 전하기",
            "마니또에게 그림 그려서 선물하기", "마니또에게 재미있는 밈(Meme) 공유하기", "마니또를 도와줄 수 있는 일 찾아서 하기",
            "마니또의 공부를 도와주기", "마니또가 힘들어하는 부분 공감해주기", "마니또가 필요한 물건 챙겨주기"
    );

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namesInput = request.getParameter("names");
        if (namesInput == null || namesInput.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/matching.jsp");
            return;
        }

        List<String> names = new ArrayList<>(Arrays.asList(namesInput.split("[,\\s]+")));
        Collections.shuffle(names);

        Map<String, String> matches = new LinkedHashMap<>();
        for (int i = 0; i < names.size(); i++) {
            String giver = names.get(i);
            String receiver = names.get((i + 1) % names.size());
            String mission = MISSIONS.get(new Random().nextInt(MISSIONS.size()));
            matches.put(giver, receiver + " (미션: " + mission + ")");
        }

        request.setAttribute("matches", matches);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}