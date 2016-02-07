<jsp:useBean id="employee" scope="request" type="com.getjavajob.web06.roldukhine.entity.Employee"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Phonebook</title>

    <link rel="stylesheet" href="<c:url context="/" value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>">
    <script src="<c:url context="/" value="/webjars/jquery/1.12.0/jquery.min.js"/>"></script>
    <script src="<c:url context="/" value="/webjars/bootstrap/3.3.6/js/bootstrap.js"/>"></script>

    <link rel="stylesheet"
          href="<c:url context="/" value="/webjars/bootstrap-3-datepicker/1.5.0/dist/css/bootstrap-datepicker3.min.css"/>"/>
    <script src="<c:url context="/" value="/webjars/bootstrap-3-datepicker/1.5.0/dist/js/bootstrap-datepicker.min.js"/>"></script>
    <script src="<c:url context="/" value="/webjars/bootstrap-3-datepicker/1.5.0/dist/locales/bootstrap-datepicker.ru.min.js"/>"></script>

    <script src="<c:url context="/" value="/webjars/bootstrap3-typeahead/3.1.1/bootstrap3-typeahead.js"/>"></script>

    <script src="<c:url context="/" value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>

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

        .btn-file {
            position: relative;
            overflow: hidden;
            padding: 2px 12px;
        }

        .btn-file input[type=file] {
            position: absolute;
            top: 0;
            right: 0;
            min-width: 100%;
            min-height: 100%;
            font-size: 100px;
            text-align: right;
            filter: alpha(opacity=0);
            opacity: 0;
            outline: none;
            background: white;
            cursor: inherit;
            display: block;
        }

        .field input {
            display: inline-block;
            width: 20%;
        }

        .field label {
            display: inline-block;
            width: 10%;
            margin-right: 1%;
        }

        .loginLbl {
            color: #9d9d9d;
            font-size: 16px;
            line-height: 20px;
            margin-right: 20px;
        }
    </style>

    <script>
        $(document).ready(function () {
            $("#datepicker").datepicker({
                format: 'dd.mm.yyyy',
                language: 'ru',
                orientation: 'bottom'
            });

            $("#saveEmployee").submit(function (e) {
                var currentForm = this;
                e.preventDefault();
                bootbox.confirm("Уверены ли вы, что хотите сохранить изменения?", function (result) {
                    if (result) {
                        currentForm.submit();
                    }
                });
            });

            $(document).on('change', '.btn-file :file', function () {
                var input = $(this),
                        numFiles = input.get(0).files ? input.get(0).files.length : 1,
                        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                input.trigger('fileselect', [numFiles, label]);
            });

            $("#addPhone").click(function () {
                $("#phoneContainer").append("<br><input type=\"text\" name=\"phone\" value=\"\" />");
            });
        });
    </script>
</head>
<body>
<%@include file="/WEB-INF/views/menu.jsp" %>


<div class="head" style="margin-top: 65px;">
    <div class="container">

        <div class="row">
            <div class="col-sm-2">
                <img src="${photo}" alt="user image"
                     height="150" width="150">

                <div class="row" style="margin: 10px 10px">

                    <form action="/photo/upload" method="post" enctype="multipart/form-data"
                          style="display: inline">

                                    <span class="btn btn-default btn-file">
                                        Browse <input type="file" name="file">
                                    </span>

                        <button type="submit" class="btn btn-success btn-xs glyphicon glyphicon-ok"
                                name="employeeId" value="${employee.id}"></button>
                    </form>

                    <form action="/photo/delete" method="post" style="display: inline;">
                        <button type="submit" class="btn btn-danger btn-xs glyphicon glyphicon-remove"
                                name="employeeId" value="${employee.id}"></button>
                    </form>
                </div>
            </div>

            <div class="col-sm-10">
                <form:form method="post" action="/employee/save" id="saveEmployee">
                    <input type="hidden" name="id" value="${employee.id}">

                    <div class="field" id="firstname"><label for="firstname">Firstname</label>
                        <input type="text" name="firstName" value="${employee.firstName}">
                    </div>

                    <div class="field" id="secondName"><label for="secondName">Secondname</label>
                        <input type="text" name="secondName" value="${employee.secondName}">
                    </div>

                    <div class="field" id="lastName"><label for="lastName">Lastname</label>
                        <input type="text" name="lastName" value="${employee.lastName}">
                    </div>

                    <div class="field" id="birthdate"><label for="birthdate">Birthday</label>
                        <input type="text" name="birthdate" id="datepicker"
                               value="<fmt:formatDate pattern="dd.MM.yyyy" value="${employee.birthdate}"/>">
                    </div>

                    <div class="field" id="email"><label for="email">email</label>
                        <input type="email" name="email" value="${employee.email}">
                    </div>

                    <div class="field" id="icq"><label for="icq">icq</label>
                        <input type="text" name="icq" value="${employee.icq}">
                    </div>

                    <div class="field" id="skype"><label for="skype">skype</label>
                        <input type="text" name="skype" value="${employee.skype}">
                    </div>

                    <div class="field" id="note"><label for="note">note</label>
                        <input type="text" name="note" value="${employee.note}">
                    </div>

                    <%--<div class="field" id="manager"><label for="manager">Manager</label>
                        <input type="text" value="${employee.manager.lastName}">
                        <input type="hidden" name="managerId" value="${employee.manager.id}" >
                    </div>
                    <div class="field" id="department"><label for="department">Department</label>
                        <input type="text" value="${employee.department.name}">
                        <input type="hidden" name="departmentId" value="${employee.department.id}">
                    </div>--%>

                    <div class="field" id="workAddress"><label for="workAddress">Work address</label>
                        <input type="text" name="workAddress" value="${employee.workAddress}">
                    </div>
                    <div class="field" id="homeAddress"><label for="homeAddress">Home address</label>
                        <input type="text" name="homeAddress" value="${employee.homeAddress}">
                    </div>

                    <div class="field" id="phoneContainer"><label for="phoneContainer">Phone</label>
                        <button type="button" id="addPhone" class="btn btn-success btn-xs glyphicon glyphicon-plus"
                                name="employeeId" value="Add phone"></button>

                            <%--<c:forEach items="${employee.phoneList}" var="phone" varStatus="status">
                                <input type="text" name="phoneList[${status.index}].number" id="phone"
                                       value="${phone.number}">
                                <input type="hidden" name="phoneList[${status.index}].id" id="phone" value="${phone.id}">
                            </c:forEach>--%>

                    </div>

                    <button type="submit" class="btn btn-success" id="save">Save</button>
                </form:form>
            </div>

        </div>
        <hr>

        <footer>
            <p>&copy; evgeniy@roldukhin.ru </p>
        </footer>
    </div> <!-- /container -->
</div>

</body>
</html>