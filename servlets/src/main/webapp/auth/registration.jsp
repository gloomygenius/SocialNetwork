<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="title.registration"/></title>
    <link href="/style.css" rel="stylesheet">
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">Левая колонка</div>
    <div id="aside">Правая колонка</div>
    <div id="content">
        <c:choose>
            <c:when test="${sessionScope.currentUser}.equals(\"\")">
                <p>Вы уже зарегистрированы!!!</p>
            </c:when>
            <c:otherwise>
                <p> ${sessionScope.currentUser} </p>
                <form action="/registration" method="post">
                    <fieldset>
                        <legend><fmt:message key="regform.legend"/></legend>
                        <p><label for="first_name"><fmt:message key="regform.fname"/> <em>*</em></label>
                            <input type="text" name="first_name"></p>
                        <p><label for="last_name"><fmt:message key="regform.lname"/> <em>*</em></label>
                            <input type="text" name="last_name"></p>
                        <p><label for="email">E-mail </label><input type="email" name="email"></p>
                        <p><label for="password"><fmt:message key="regform.password"/> <em>*</em></label>
                            <input type="password" name="password"></p>
                        <p><label for="gender"><fmt:message key="regform.gender"/> <em>*</em></label><br>
                            <input type="radio" name="gender" value="male" checked><fmt:message key="regform.male"/><br>
                            <input type="radio" name="gender" value="female"><fmt:message key="regform.female"/><br>
                        </p>
                    </fieldset>
                    <p><input type="submit" value="<fmt:message key="regform.button"/>"></p>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="footer">
        <p>Язык сайта (site language):<br>
            <a href="/?language=ru_RU">Русский</a> | <a href="/?language=en_US">English</a> <br></p>
        &copy; <fmt:message key="copyright"/>
    </div>
</div>
</body>
</html>