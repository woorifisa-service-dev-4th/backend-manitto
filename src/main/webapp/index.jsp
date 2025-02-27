<%-- /src/main/webapp/index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <title>로그인</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <!-- 로그인 관련 AJAX 처리 JS (친구 API 사용 시) -->
    <script defer src="./assets/login.js"></script>
</head>
<body>
<div class="container">
    <h2>로그인</h2>

    <% 
        String error = (String) session.getAttribute("error");
        if (!Objects.isNull(error)) { 
    %>
    <p class="error-message" id="errorMessage"><%= error %></p>
    <%
            session.removeAttribute("error");
        }
    %>

    <!-- 폼 제출 시 JS로 AJAX 요청을 보낼 수 있도록 id를 부여 -->
    <form id="loginForm">
        <input type="hidden" name="action" value="login">
        <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
        <button type="submit">로그인</button>
    </form>

    <p>아직 회원이 아니신가요? <a href="register">회원가입</a></p>
</div>
</body>
</html>
