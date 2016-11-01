<%@ page import="static security.SecurityFilter.USER_KEY" %>
<%@ page import="com.social_network.jdbc.model.User" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="title.mainpage"/></title>
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
            %>"><fmt:message key="menu.mypage"/> </a></li>
            <li class="item2"><a href="#"><fmt:message key="menu.messages"/></a></li>
            <li class="item3"><a href="#"><fmt:message key="menu.friends"/></a></li>
            <li class="item4"><a href="#"><fmt:message key="menu.myteam"/></a></li>
            <li class="item5"><a href="#"><fmt:message key="menu.events"/></a></li>
        </ul>
    </div>
    <div id="aside">Правая колонка<br>
        <a href="/logout"><fmt:message key="menu.logout"/></a>
    </div>
    <div id="content">Добро пожаловать!</div>
    <div id="footer">
        <p>Язык сайта (site language):<br>
            <a href="/?language=ru_RU">Русский</a> | <a href="/?language=en_US">English</a> <br></p>
        &copy; <fmt:message key="copyright"/>
    </div>
</div>
</body>
</html>