<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <title>로그인</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>로그인</h2>

    <%-- 에러 메시지 출력 --%>
    <%
        String error = (String) session.getAttribute("error");
        if (!Objects.isNull(error)) {
    %>
    <p class="error-message"><%= error %></p>
    <%
            session.removeAttribute("error"); // 한 번 표시 후 세션에서 삭제
        }
    %>

    <form action="/controller/user" method="post">
        <input type="hidden" name="action" value="login">
        <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>

        <button type="submit">로그인</button>
    </form>

    <p>아직 회원이 아니신가요? <a href="register.jsp">회원가입</a></p>
</div>
</body>
</html>