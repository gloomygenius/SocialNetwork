<%@ page import="static security.SecurityFilter.USER_KEY" %>
<%@ page import="static filters.IdPageFilter.REF_PAGE" %>
<%@ page import="com.social_network.jdbc.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        %>
        <%
            User refUser = (User) session.getAttribute(REF_PAGE);
            if (refUser==null) {
        %>
        <p>Добро пожаловать, ${user.getFirstName()} ${user.getLastName()}<br>
        </p>
        <%
            }else {
                if (refUser.getId()==0) out.print("Такого пользователя не существует");
                else {
                    out.print(refUser.getFirstName() +" "+ refUser.getLastName());
                }

        %>
        <p></p>

        <%
            }
        %>
    </div>
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>