<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <link rel="stylesheet" href="../../../css/style.css">
</head>
<body>

<nav>
    <a href="WelcomePageService" title="Welcome page">Home</a>
</nav>

<br>
<div class="container">
<h2>Order list</h2>

<table>
    <tbody>
    <tr><th>Truck number</th><th>Drivers</th><th>Waypoints</th><th>Completed</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${requestScope.orderList}" var="order">
        <tr>
            <td><c:out value="${order.truck.plate_number}"></c:out></td>
            <td><c:forEach items="${order.drivers}" var="driver">
                <c:out value="${driver.name}"></c:out>
            </c:forEach></td>
            <td><c:forEach items="${order.waypoints}" var="waypoint">
                <c:out value="${waypoint.location.city}"></c:out>
            </c:forEach></td>
            <td><c:out value="${order.completed}"></c:out></td>
            <td><c:out value="${order.created_time}"></c:out></td>
            <td><c:out value="${order.last_modified_time}"></c:out></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>

</body>
</html>