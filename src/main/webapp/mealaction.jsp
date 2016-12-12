<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Добавить/редактировать еду</title>
</head>
<body>
<form method="POST" action="mealaction" name="frmAddMeal">
    ID : <input type="text" readonly="readonly" name="id"
                value="<c:out value="${meal.id}" />"/> <br/>
    Описание : <input type="text" name="description"
                value="<c:out value="${meal.description}" />"/> <br/>
    Калории : <input type="text" name="calories"
                value="<c:out value="${meal.calories}" />"/> <br/>
    Дата : <input type="text" name="datetime"
                value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${meal.dateTime}" />"/> <br/>
    <br/>
    <input type="submit" value="Готово"/>
</form>
</body>
</html>
