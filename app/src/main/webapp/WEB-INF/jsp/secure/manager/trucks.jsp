<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trucks</title>
    <link rel="stylesheet" href="../../../css/style.css">
</head>
<body>

<nav>
    <a href="DriverListService" title="Driver list">Drivers</a>
    <a href="OrderListService" title="Order list">Orders</a>
    <a href="WelcomePageService" title="Welcome page">Home</a>
</nav>

<br>
<h2>Add a truck</h2>

<form action="AddTruckService" method="post">
    <input type="text" name="plate_number" placeholder="plate number"><br>
    <input type="text" name="driver_number" placeholder="driver number"><br>
    <input type="text" name="capacity" placeholder="capacity"><br>
    <input type="text" name="drivable" placeholder="drivable"><br>
    <input type="text" name="city" placeholder="city"><br>
    <input type="submit" value="Submit">
</form>

<br>
<div class="container">
<h2>Truck list</h2>

<table>
    <tbody>
    <tr><th>Plate number</th><th>Driver number</th><th>Capacity</th><th>Drivable</th><th>Location</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${requestScope.data}" var="truck">
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
</div>

<br>
<%--<h2>Dynamic truck list</h2>--%>

<div id="truckList">
</div>

<script src="https://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="../../../js/db_fetch.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>