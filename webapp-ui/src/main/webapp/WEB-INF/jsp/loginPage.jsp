<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Phonebook</title>

    <link rel="stylesheet" href="<c:url  value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">
    <link rel="stylesheet"
          href="<c:url  value="/webjars/bootstrap-3-datepicker/1.5.0/dist/css/bootstrap-datepicker3.min.css"/>"/>
    <link rel="stylesheet" href="<c:url  value="/css/style.css"/>">

    <script src="<c:url  value="/webjars/jquery/1.12.4/jquery.min.js"/>"></script>
    <script src="<c:url  value="/webjars/bootstrap/3.3.7/js/bootstrap.js"/>"></script>

    <script src="<c:url  value="/webjars/bootstrap-3-datepicker/1.5.0/dist/js/bootstrap-datepicker.min.js"/>"></script>
    <script src="<c:url  value="/webjars/bootstrap-3-datepicker/1.5.0/dist/locales/bootstrap-datepicker.ru.min.js"/>"></script>

    <script src="<c:url  value="/webjars/bootstrap3-typeahead/3.1.1/bootstrap3-typeahead.js"/>"></script>

    <script src="<c:url  value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
    <script src="<c:url  value="/js/phonebook.js"/>"></script>

</head>
<body class="login-signin">
<div class="container">
    <form class="form-signin" action="<c:url value="/account/login"/>" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="username" class="sr-only">Login</label>
        <input type="text" id="username" name="username" class="form-control form-signin-login" placeholder="Login" required
               autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="rememberMe" value="on"> Remember me
                <input type="hidden" name="_rememberMe" value="off">
            </label>
        </div> <!-- /checkbox -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

        <div align="center">
            <div class="unauthenticated">
                <h3>Login using facebook  </h3>
                <a class="btn btn-primary" href="/login">Facebook Login</a>
                <br><br>
            </div>
            <div class="authenticated" style="display: none">
                <h2>You have successfully logged in using Facebook.</h2> <br> Logged in as: <strong><span id="user"></span></strong><br> User ID: <strong><span id="id"></span></strong>
                <div>
                    <br>
                    <button onClick="logout()" class="btn btn-warning">Logout</button>
                    <br><br>
                </div>
            </div>
        </div>


    </form>
</div> <!-- /container -->

<script>
    if (window.location.hash == '#_=_') {
        history.replaceState ?
            history.replaceState(null, null, window.location.href.split('#')[0]) :
            window.location.hash = '';
    }
    $.get("/user", function (data) {
        $("#user").html(data.userAuthentication.details.name);
        $("#id").html(data.userAuthentication.details.id);
        $(".unauthenticated").hide()
        $(".authenticated").show()
    });
    const logout = function () {
        $.post("/logout", function () {
            $("#user").html('');
            $("#id").html('');
            $(".unauthenticated").show();
            $(".authenticated").hide();
        });
        return true;
    };
</script>

</body>
</html>
