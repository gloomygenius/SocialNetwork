<%@ page import="static security.SecurityFilter.USER_KEY" %>
<%@ page import="static filters.IdPageFilter.REF_PAGE" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="currentUser" scope="page" value="${sessionScope.currentUser}"/>
<c:set var="refUser" scope="page" value="${sessionScope.referencePage}"/>


<c:choose>
    <c:when test="${refUser.id eq 0}">
        <%--This user does not exist--%>
        <jsp:forward page="/jsp/error.jsp"/>
    </c:when>
    <c:otherwise>
        <c:set scope="page" var="id" value="${refUser.firstName}"/>
        <c:set scope="page" var="firstName" value="${refUser.firstName}"/>
        <c:set scope="page" var="lastName" value="${refUser.lastName}"/>
    </c:otherwise>
</c:choose>

<c:set var="userInfo" scope="page" value="${sessionScope.userInfo}"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Страница авторизации</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <div id="header">[В]Отряде</div>
    <div id="nav">Левая колонка</div>
    <div id="aside">Правая колонка</div>
    <div id="content">
        <div id="avatar">
            <img src="/img/default_ava.png" alt="avatar"/>
        </div>
        <div>
            <div class="user_info">
                <div class="fio">
                    <p>${firstName} ${lastName}</p>
                </div>
                <c:choose>
                    <c:when test="${userInfo.isPresent()}">
                        <c:set scope="page" var="info" value="${userInfo.get()}"/>
                        <div class="block_inf">
                            <div class="f_col">День рождения:</div>
                            <div class="s_col">${info.bithday}</div>
                        </div>
                        <div class="block_inf">
                            <div class="f_col">Город:</div>
                            <div class="s_col">${info.city}</div>
                        </div>
                        <div class="block_inf">
                            <div class="f_col">Место учёбы:</div>
                            <div class="s_col">${info.university}</div>
                        </div>
                        <div class="block_inf">
                            <div class="f_col">Отряд:</div>
                            <div class="s_col">${info.team}</div>
                        </div>
                        <div class="block_inf">
                            <div class="f_col">Должность:</div>
                            <div class="s_col">${info.position}</div>
                        </div>
                        <div class="block_inf">
                            <div class="f_col">Сезоны:</div>
                            <div class="s_col">В рзработке</div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p>Дополнительной информации о пользователе нет</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div id="footer">&copy; Василий Бобков</div>
</div>
</body>
</html>