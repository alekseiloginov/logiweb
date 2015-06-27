<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trucks</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav>
    <a href="DriverListService" title="Driver list">Drivers</a>
    <a href="OrderListService" title="Order list">Orders</a>
    <a href="welcome_manager.jsp" title="Welcome page">Home</a>
</nav>

<br>
<h2>Truck list</h2>

<table>
    <tbody>
    <tr><th>Plate number</th><th>Driver number</th><th>Capacity</th><th>Drivable</th><th>Location</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.truckList}" var="truck">
        <tr><td><c:out value="${truck.plate_number}"></c:out></td>
            <td><c:out value="${truck.driver_number}"></c:out></td>
            <td><c:out value="${truck.capacity}"></c:out></td>
            <td><c:out value="${truck.drivable}"></c:out></td>
            <td><c:out value="${truck.location.city}"></c:out></td>
            <td><c:out value="${truck.created_time}"></c:out></td>
            <td><c:out value="${truck.last_modified_time}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
