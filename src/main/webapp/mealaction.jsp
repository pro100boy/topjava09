<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="f" uri="functions.tld" %>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<style type="text/css">
    .myrow-container {
        margin: 20px;
    }
</style>
<html>
<head>
    <title>Добавить/редактировать еду</title>
</head>
<body class=".container-fluid">
<div class="container myrow-container">
    <form class="form-horizontal" method="POST" action="meals" name="frmAddMeal">
        <div class="panel panel-success">
            <div class="panel-heading">
                <h3 class="panel-title">
                    Действия с едой
                </h3>
            </div>
            <div class="panel-body">
                <%--ID : --%><input type="text" readonly="readonly" name="id" hidden="true"
                                    value="<c:out value="${meal.id}" />"/>

                <div class="form-group">
                    <label class="control-label col-xs-3" for="description">Описание:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" name="description" id="description"
                               placeholder="Описание еды" value="<c:out value="${meal.description}" />"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-3" for="calories">Калории:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" name="calories" id="calories"
                               placeholder="Количество калорий (от 0 до 9999)"
                               min="0" max="9999" value="<c:out value="${meal.calories}" />"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-3" for="datetime">Дата:</label>
                    <div class="col-xs-6">
                        <input type="datetime-local" class="form-control" name="datetime" id="datetime"
                               placeholder="дд.мм.гггг чч:мм"
                               value="<c:out value="${f:formatLocalDateTime(meal.dateTime)}" />"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-10">
                        <button type="submit" class="btn btn-success" onclick="return submitUserForm();">Готово</button>
                        <a class="btn btn-warning" href="meals" role="button">Отмена</a>
                    </div>
                </div>
            </div>
        </div>
    </form>


</div>

<script src="<c:url value="/resources/js/jquery-2.1.3.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

<script type="text/javascript">
    function submitUserForm() {

        // getting the user form values
        var desc = $('#description').val().trim();
        var calories = $('#calories').val();
        var createdDate = $('#datetime').val().trim();

        if (desc.length == 0) {
            alert('Please enter description');
            $('#description').focus();
            return false;
        }

        if (createdDate.length == 0) {
            alert('Please enter date');
            $('#createdDate').focus();
            return false;
        }

        if (calories < 0 || calories > 9999) {
            alert('Please enter valid calories count (from 0 to 9999)');
            $('#calories').focus();
            return false;
        }

        return true;
    };
</script>

</body>
</html>
