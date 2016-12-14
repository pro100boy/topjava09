<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="functions.tld" %>

<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<style type="text/css">
    .myrow-container {
        margin: 20px;
    }
</style>

<html>
<head>
    <title>Meal list</title>
</head>
<body class=".container-fluid">

<div class="container myrow-container">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">
                <div align="left"><b>Список еды</b></div>
                <div align="right"><a href="meals?action=insert">Добавить еду</a></div>
            </h3>
        </div>

        <div class="panel-body">
            <table class="table table-hover table-bordered" style="font-weight: bold">
                <thead class="bg-primary">
                <tr>
                    <th style="display:none;">ID</th>
                    <th class="text-center">Время</th>
                    <th class="text-center">Описание</th>
                    <th class="text-center">Калории</th>
                    <th class="text-center" colspan=2>Действия</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meals}" var="meal">
                    <tr class=${meal.isExceed() ? "text-danger" : "text-success"}>
                        <td style="display:none;"><c:out value="${meal.id}"/></td>
                        <td class="text-center col-xs-3"><c:out value="${f:formatLocalDateTime(meal.dateTime)}"/></td>
                        <td><c:out value="${meal.description}"/></td>
                        <td class="text-center col-xs-2"><c:out value="${meal.calories}"/></td>
                        <td class="text-center col-xs-1"><a class="btn btn-primary btn-xs" href="meals?action=edit&id=<c:out value="${meal.id}"/>" role="button">Редактировать</a></td>
                        <td class="text-center col-xs-1"><a class="btn btn-danger btn-xs" href="meals?action=delete&id=<c:out value="${meal.id}"/>" role="button">Удалить</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="panel-footer">
            <h3 class="panel-title">
                <div align="left"><a href="index.html">Home</a></div>
                <div align="right"><a href="meals">Обновить список</a></div>
            </h3>
        </div>
    </div>
</div>
</body>
</html>
