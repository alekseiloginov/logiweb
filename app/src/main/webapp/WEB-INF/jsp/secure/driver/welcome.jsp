<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver welcome page</title>
    <link rel="stylesheet" href="../../../../css/style.css">
</head>
<body>

<%--If driver's truck_id is null, don't show a link to his orders because he have no yet.--%>
<c:choose>
    <c:when test="${empty sessionScope.user.getTruck().getId()}">
        <nav>
            <a></a>
        </nav>
        <br>

        <div class="container">
            <h1>Hello, <c:out value="${sessionScope.user.getName()}"/>!</h1>
            <p>Your email: <c:out value="${sessionScope.user.getEmail()}"/></p>
            <p>Your personal driver number: <c:out value="${sessionScope.user.getId()}"/></p>

            <form action="Logout" method="post">
                <input type="submit" value="Logout">
            </form>
        </div>
    </c:when>

    <c:otherwise>
        <nav>
            <a href="Orders.do?role=driver" title="Order list">Orders</a>
        </nav>
        <br>

        <div class="container">
            <h1>Hello, <c:out value="${sessionScope.user.getName()}"/>!</h1>
            <p>Your email: <c:out value="${sessionScope.user.getEmail()}"/></p>
            <p>Your personal driver number: <c:out value="${sessionScope.user.getId()}"/></p>

            <form action="Logout" method="post">
                <input type="submit" value="Logout">
            </form>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>
