<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<table border=1>
    <thead>
    <tr bgcolor="blue" style="font-style: normal; color: antiquewhite">
        <th>ID</th>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <%--<th>Email</th>
        <th colspan=2>Action</th>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal" varStatus="mealLoopCount">
        <c:choose>
            <c:when test="${meal.exceed == true}"> <tr bgcolor="#f08080"> </c:when>
            <c:otherwise> <tr bgcolor="#90ee90"> </c:otherwise>
        </c:choose>

        <td><c:out value="${mealLoopCount.count}"/></td>
        <td><c:out value="${meal.dateTimeStr}"/></td>
        <%--<td><c:out value="${meal.getDateTime().toString().replace(\"T\",\" \")}"/></td>--%>
        <%--<td><fmt:formatDate pattern="yyyy-MMM-dd" value="<%=new java.util.Date()%>" /></td>--%>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
