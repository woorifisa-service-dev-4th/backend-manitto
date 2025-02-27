<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>매칭 결과</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<h2>마니또 매칭 결과</h2>
<ul>
    <%
        Map<String, String> matches = (Map<String, String>) request.getAttribute("matches");
        if (matches != null) {
            for (Map.Entry<String, String> entry : matches.entrySet()) {
    %>
    <li><b><%= entry.getKey() %></b> → <%= entry.getValue() %></li>
    <%
            }
        }
    %>
</ul>
<a href="lobby.jsp">다시 하기</a>
</body>
</html>