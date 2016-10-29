<%--
  Created by IntelliJ IDEA.
  User: Vasiliy
  Date: 27.10.2016
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Регистрация нового пользователя</title>
    <link href="/style.css" rel="stylesheet">
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">Левая колонка</div>
    <div id="aside">Правая колонка</div>
    <div id="content">
        <form action="UserRegistrator" method="post">
            <input type="hidden" name="command" value="forward">
            Enter login:<br/>
            <input type="text" name="login" value=""><br/>
            Enter password:<br/>
            <input type="password" name="password" value=""><br/>
            <input type="submit" value="Зарегистрироваться">
        </form>
    </div>
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>
