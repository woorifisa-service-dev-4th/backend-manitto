<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="manitto.servlet.model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
<head>
    <title>마니또 로비</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>환영합니다, <%= user.getNickname() %> 님!</h2>
    <p>마니또 게임을 시작하려면 방을 생성하거나 참가하세요.</p>

    <div class="button-group">
        <button onclick="location.href='matching.jsp'">방 생성하기</button>
        <button>방 참가하기</button>
    </div>

    <a href="controller?action=logout" class="logout-button">로그아웃</a>
</div>
</body>
</html>