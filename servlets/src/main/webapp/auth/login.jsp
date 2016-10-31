<%--
  Created by IntelliJ IDEA.
  User: Vasiliy
  Date: 26.10.2016
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Страница авторизации</title>
    <link href="../style.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">Левая колонка</div>
    <div id="aside">Правая колонка</div>
    <div id="content">
        Надо залогиниться! <br>
        <form method="POST" action="j_security_check">
            <input name="j_username" title="Login"/><br/>
            <input type="password" name="j_password" autocomplete="off" title="Password"/><br/>
            <input type="submit" value="submit"/>
        </form><br>
        <a href="/auth/registration.jsp">Зарегистрироваться</a>
    </div>
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>