<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%--http://www.tutorialrepublic.com/twitter-bootstrap-tutorial/bootstrap-dropdowns.php--%>
<%--http://www.tutorialrepublic.com/codelab.php?topic=bootstrap&file=enable-dropdowns-via-data-attributes-01--%>
<li class="dropdown pull-right">

    <%--с кнопкой красивее, но не знаю как выровнять вровень с остальными элементами--%>
    <%--<button type="button" data-toggle="dropdown"
            class="btn btn-info btn-xs dropdown-toggle">${pageContext.response.locale}<span class="caret"></span>
    </button>--%>
    <a class="btn btn-inverse dropdown-toggle" data-toggle="dropdown" role="button"
       href="#">${pageContext.response.locale}<span class="caret"></span>
    </a>

    <ul class="dropdown-menu">
        <li><a onclick="addLocaleURL('en')">English</a></li>
        <li><a onclick="addLocaleURL('ru')">Русский</a></li>
    </ul>
</li>

<script type="text/javascript">
    /*запоминаем локаль для использования в календаре*/
    var langID = "${pageContext.response.locale}";
    function addLocaleURL(lang) {
        /**
         * http://javascript.ru/window-location
         * */
        window.location.search = 'locale=' + lang;
    }
</script>