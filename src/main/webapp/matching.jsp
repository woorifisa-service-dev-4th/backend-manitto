<%-- /src/main/webapp/matching.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>마니또 매칭</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <script>
        // 텍스트영역 높이 자동 조절 스크립트
        function autoResize(textarea) {
            textarea.style.height = "auto";
            textarea.style.height = textarea.scrollHeight + "px";
        }
    </script>
</head>
<body>
<h1>💌</h1>
<h2>이름을 입력하세요</h2>
<form action="/controller/matching" method="post">
    <input type="hidden" name="action" value="match">
    <textarea name="names" placeholder="이름을 쉼표 또는 공백으로 구분하여 입력" required oninput="autoResize(this)"></textarea>
    <button type="submit">매칭하기</button>
</form>
</body>
</html>
