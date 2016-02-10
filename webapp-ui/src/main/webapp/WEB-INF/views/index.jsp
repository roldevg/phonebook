<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <script>
        $(document).ready(function () {
            $('.typeahead').typeahead({
                source: function (query, process) {
                    return $.post(
                            '/ajax/search',
                            {query: query},
                            function (data) {
                                process(data);
                            },
                            "json");
                },
                minLength: 3
            });
        });
    </script>

    <style>
        .loginLbl {
            color: #9d9d9d;
            font-size: 16px;
            line-height: 20px;
            margin-right: 20px;
        }
    </style>

</head>
<body>
<%@include file="/WEB-INF/views/menu.jsp" %>

<div class="head" style="margin-top: 65px;">
    <div class="container">

        <div class="row">
            <p>Телефонная книга позволяет вам легко хранить телефоны ваших друзей в одном месте.</p>

            <div class="form-group">
                <label for="sel1">Выберите отдел: </label>
                <select class="form-control" id="sel1">
                    <option>Все</option>
                    <option>Руководство</option>
                    <option>Бухгалтерия</option>
                    <option>Разработка</option>
                </select>
            </div>

            <div class="col-lg-6" style="margin-top: 10px; padding-left: 0px; padding-right: 0px;">
                <div class="input-group">
                    <input type="text" class="form-control typeahead" placeholder="Search for...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->

            <div class="col-lg-6" style="margin-top: 10px; padding-left: 0px; padding-right: 0px;">
                <form action="/employee/add" method="get" style="display: inline;">
                    <button type="submit" class="btn btn-primary">
                        <i class="icon-user icon-white"></i> Добавить
                    </button>
                </form>

                <!-- Single button -->
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true"
                            aria-expanded="false">
                        Action <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul>
                </div>
            </div><!-- /.col-lg-6 -->

            <table class="table table-hover">
                <thead>
                <tr>
                    <th></th>
                    <th>Firstname</th>
                    <th>Secondname</th>
                    <th>Lastname</th>
                    <th>Birthday</th>
                    <th>Email</th>
                    <th>ICQ</th>
                    <th>Skype</th>
                    <th>Note</th>
                    <th>Home address</th>
                    <th>Work address</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employeeList}" var="employee">
                    <tr>
                        <td>
                            <form action="<c:url context="/" value="/employee/edit/${employee.id}"/>" method="get"
                                  style="display: inline;">
                                <button type="submit"
                                        class="btn btn-success btn-xs glyphicon glyphicon-pencil"></button>
                            </form>
                            <form action="<c:url context="/" value="/employee/delete/${employee.id}"/>" method="post"
                                  style="display: inline;">
                                <button type="submit" class="btn btn-danger btn-xs glyphicon glyphicon-remove"></button>
                            </form>
                        </td>
                        <td>${employee.firstName}</td>
                        <td>${employee.secondName}</td>
                        <td>${employee.lastName}</td>
                        <td>${employee.birthdate}</td>
                        <td>${employee.email}</td>
                        <td>${employee.icq}</td>
                        <td>${employee.skype}</td>
                        <td>${employee.note}</td>
                        <td>${employee.workAddress}</td>
                        <td>${employee.homeAddress}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <hr>

        <footer>
            <p>&copy; evgeniy@roldukhin.ru </p>
        </footer>
    </div> <!-- /container -->
</div>

</body>
</html>