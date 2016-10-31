<%@ page import="static security.SecurityFilter.USER_KEY" %>
<%@ page import="com.social_network.jdbc.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Страница авторизации</title>
    <link href="../style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">Левая колонка</div>
    <div id="aside">Правая колонка</div>
    <div id="content">
        <%
            session = request.getSession();
            User user = (User) session.getAttribute(USER_KEY);
            System.out.println(user.getFirstName());
        %>
        <p>Добро пожаловать, <% out.print(user.getFirstName() +" "+ user.getLastName()); %><br>
        </p>
    </div>
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>