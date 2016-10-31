<%@ page import="static security.SecurityFilter.USER_KEY" %>
<%@ page import="com.social_network.jdbc.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Vasiliy
  Date: 29.10.2016
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Главная страница</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">
        <ul class="menu">
            <li class="item1"><a href="/id<%
                User user = (User) session.getAttribute(USER_KEY);
                out.print(String.valueOf(user.getId()));
            %>">Моя страница </a></li>
            <li class="item2"><a href="#">Сообщения</a></li>
            <li class="item3"><a href="#">Друзья</a></li>
            <li class="item4"><a href="#">Мой отряд</a></li>
            <li class="item5"><a href="#">Мероприятия</a></li>
        </ul>
    </div>
    <div id="aside">Правая колонка<br>
        <a href="/logout">Выйти из аккаунта</a>
    </div>
    <div id="content">Добро пожаловать!
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>
