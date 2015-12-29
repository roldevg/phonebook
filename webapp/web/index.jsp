<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>$Title$</title>
</head>
<body>

  <ul>
    <c:forEach items="${bookList}" var="book">
      <li>Book name: ${book} </li>
    </c:forEach>
  </ul>

  <%
    // Скриптлет
    // List<String> bookList = (List<String>) request.getAttribute("bookList");
  %>

</body>
</html>