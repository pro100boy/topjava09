<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="f" uri="functions.tld" %>
<html>
<head>
    <title>Добавить/редактировать еду</title>
</head>
<body>
<form method="POST" action="meals" name="frmAddMeal">
    ID : <input type="text" readonly="readonly" name="id"
                value="<c:out value="${meal.id}" />"/> <br/><br/>
    Описание : <input type="text" name="description"
                value="<c:out value="${meal.description}" />"/> <br/><br/>
    Калории : <input type="text" name="calories"
                value="<c:out value="${meal.calories}" />"/> <br/><br/>
    Дата : <input type="text" name="datetime"
                value="${f:formatLocalDateTime(meal.dateTime)}"/> <br/>

    <br/>
    <input type="submit" value="Готово"/>
</form>
</body>
</html>
