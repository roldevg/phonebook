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
            <form action="/employee/insert" method="post">
                <div class="field" id="firstname"><label for="firstname">Firstname</label><input type="text"
                                                                                                 name="firstname"></div>
                <div class="field" id="secondname"><label for="secondname">Secondname</label><input type="text"
                                                                                                    name="secondname">
                </div>
                <div class="field" id="lastname"><label for="lastname">Lastname</label><input type="text"
                                                                                              name="lastname"></div>
                <div class="field" id="birthday"><label for="birthday">Birthday</label><input type="text"
                                                                                              name="birthday"></div>
                <div class="field" id="email"><label for="email">email</label><input type="text" name="email"></div>
                <div class="field" id="icq"><label for="icq">icq</label><input type="text" name="icq"></div>
                <div class="field" id="skype"><label for="skype">skype</label><input type="text" name="skype"></div>
                <div class="field" id="note"><label for="note">note</label><input type="text" name="note"></div>
                <div class="field" id="manager"><label for="manager">Manager</label><input type="text" name="manager">
                </div>
                <div class="field" id="department"><label for="department">Department</label><input type="text"
                                                                                                    name="department">
                </div>
                <div class="field" id="work"><label for="work">Work address</label><input type="text" name="work"></div>
                <div class="field" id="home"><label for="home">Home address</label><input type="text" name="home"></div>
                <div class="field" id="phone"><label for="phone">Phone</label><input type="text" name="phone"></div>
                <button type="submit" class="btn btn-success">Save</button>
            </form>
        </div>

        <hr>

        <footer>
            <p>&copy; evgeniy@roldukhin.ru </p>
        </footer>
    </div> <!-- /container -->
</div>


<script>
    $({
        function() {
            $('.input-group.date').datepicker({});
        }
    })

</script>

</body>
</html>