package manitto.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

/*
구본훈 권민지 권지윤 김새봄 김성준 김지연 남승현 노영재 마서영 박찬진 배승혁 서용준 서채연 신희원 유승한 윤선영 윤영찬 윤예진 윤태경 이서연 이소연 이승준 이원빈 이정민 이한비 임지섭 조윤주 조현식 차승훈 허연규 황유환 황혜영
 */