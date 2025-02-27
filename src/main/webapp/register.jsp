<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>회원가입</h2>

    <%
        String error = (String) session.getAttribute("error");
        if (!Objects.isNull(error)) {
    %>
    <p class="error-message"><%= error %></p>
    <%
            session.removeAttribute("error");
        }
    %>

    <form action="/controller/user" method="post">
        <input type="hidden" name="action" value="register">
        <input type="text" id="nickname" name="nickname" placeholder="닉네임을 입력하세요" required>
        <input type="email" id="email" name="email" placeholder="이메일을 입력하세요" required>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
        <input type="password" id="confirm-password" name="confirmPassword" placeholder="비밀번호를 다시 입력하세요" required>

        <button type="submit">회원가입</button>
    </form>

    <p>이미 회원이신가요? <a href="index.jsp">로그인</a></p>
</div>

<script>
    document.querySelector("form").addEventListener("submit", function(event) {
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirm-password").value;

        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            event.preventDefault();
        }
    });
</script>
</body>
</html>