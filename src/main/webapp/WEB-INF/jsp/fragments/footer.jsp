<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="footer">
    <div class="container">
        <spring:message code="app.footer"/>
    </div>
</div>

<script type="text/javascript">
    var i18n = [];
    //var editTitle = '<spring:message code="users.edit"/>';
    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.failed", "meals.edit", "users.edit"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>

<%--
<script type="text/javascript">
    var i18n = [];
    var editTitle = '<spring:message code="meals.edit"/>';
    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.failed"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>
</script>--%>
