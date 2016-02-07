<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Phonebook</title>

    <link rel="stylesheet" href="<c:url context="/" value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>">
    <script src="<c:url context="/" value="/webjars/jquery/1.12.0/jquery.min.js"/>"></script>
    <script src="<c:url context="/" value="/webjars/bootstrap/3.3.6/js/bootstrap.js"/>"></script>

    <link rel="stylesheet"
          href="<c:url context="/" value="/webjars/bootstrap-3-datepicker/1.5.0/dist/css/bootstrap-datepicker3.min.css"/>"/>
    <script src="<c:url context="/" value="/webjars/bootstrap-3-datepicker/1.5.0/dist/js/bootstrap-datepicker.min.js"/>"></script>
    <script src="<c:url context="/" value="/webjars/bootstrap-3-datepicker/1.5.0/dist/locales/bootstrap-datepicker.ru.min.js"/>"></script>

    <script src="<c:url context="/" value="/webjars/bootstrap3-typeahead/3.1.1/bootstrap3-typeahead.js"/>"></script>

    <style>
        .field input {
            display: inline-block;
            width: 20%;
        }

        .field label {
            display: inline-block;
            width: 10%;
            margin-right: 1%;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/menu.jsp" %>

<div class="head" style="margin-top: 65px;">
    <div class="container">

        <div class="row">
            <form action="/employee/insert" method="post">
                <input type="text">
            </form>
        </div>

        <hr>

        <footer>
            <p>&copy; evgeniy@roldukhin.ru </p>
        </footer>
    </div> <!-- /container -->
</div>

</body>
</html>