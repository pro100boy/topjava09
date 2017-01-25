<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<head>
    <jsp:include page="fragments/bodyHeader.jsp"/>
</head>
<body>
<section>
    <%--<h2><a href="index"><fmt:message key="app.home"/></a></h2>--%>
    <h3><fmt:message key="meals.title"/></h3>

    <form method="post" action="meals?action=filter">
        <dl>
            <dt><fmt:message key="from.date"/></dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="to.date"/></dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="from.time"/></dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="to.time"/></dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><fmt:message key="common.select"/></button>
    </form>
    <hr>
    <a href="meals?action=create"><fmt:message key="add.meal"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="header.date"/></th>
            <th><fmt:message key="header.description"/></th>
            <th><fmt:message key="header.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <%--<jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>--%>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}"><fmt:message key="action.update"/></a></td>
                <td><a href="meals?action=delete&id=${meal.id}"><fmt:message key="action.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="meals?language=ru">RU</a>
    <a href="meals?language=en">EN</a>
    <a href="meals?language=cz">CZ</a>

    <div>
        <p>выбранный язык: ${selectedLanguage}</p>
        <p>установленный язык: ${factLanguage}</p>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>