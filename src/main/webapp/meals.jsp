<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="functions.tld" %>

<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<table border=1>
    <thead>
    <tr bgcolor="blue" style="font-style: normal; color: antiquewhite">
        <th style="display:none;">ID</th>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">

        <c:choose>
            <c:when test="${meal.exceed == true}"> <tr bgcolor="#f08080"> </c:when>
            <c:otherwise> <tr bgcolor="#90ee90"> </c:otherwise>
        </c:choose>

        <%--<tr bgcolor=<c:out value="${meal.exceed == true} ? #f08080 : #90ee90"}">>--%>


            <td style="display:none;"><c:out value="${meal.id}"/></td>
            <td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm')}"/></td>
            <%--<td><c:out value="${ru.javawebinar.topjava.util.TimeUtil.formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy HH:mm')}"/></td>--%>
            <%--<td><c:out value="${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).format(meal.dateTime)}"/></td>--%>
            <%--<td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy')}"/></td>--%>
            <%--<td>${f:formatLocalDateTime(meal.dateTime, "yyyy-MM-dd HH:mm")}</td>--%>
            <%--<td><c:out value="${meal.getDateTime().toString().replace(\"T\",\" \")}"/></td>--%>
            <%--<td><fmt:formatDate pattern="yyyy-MMM-dd" value="<%=new java.util.Date()%>" /></td>--%>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>" style="font-style: italic; color: black">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>" style="font-style: italic; color: black">Delete</a></td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Добавить еду</a></p>
<p><a href="meals?action=listMeal">Обновить список</a></p>
</body>
</html>
