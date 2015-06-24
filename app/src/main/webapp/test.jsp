<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test page</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<br/>
<h2>Manager list</h2>

<table>
    <tbody>
    <tr><th>Name</th><th>Surname</th><th>Email</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.managerList}" var="manager">
        <tr><td><c:out value="${manager.name}"></c:out></td>
            <td><c:out value="${manager.surname}"></c:out></td>
            <td><c:out value="${manager.email}"></c:out></td>
            <td><c:out value="${manager.created_time}"></c:out></td>
            <td><c:out value="${manager.last_modified_time}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>

<br/>
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

<br/>
<h2>Driver list</h2>

<table>
    <tbody>
    <tr><th>Name</th><th>Surname</th><th>Email</th><th>Worked hours</th><th>Status</th><th>City</th><th>Truck number</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.driverList}" var="driver">
        <tr><td><c:out value="${driver.name}"></c:out></td>
            <td><c:out value="${driver.surname}"></c:out></td>
            <td><c:out value="${driver.email}"></c:out></td>
            <td><c:out value="${driver.worked_hours}"></c:out></td>
            <td><c:out value="${driver.status}"></c:out></td>
            <td><c:out value="${driver.location.city}"></c:out></td>
            <td><c:out value="${driver.truck.plate_number}"></c:out></td>
            <td><c:out value="${driver.created_time}"></c:out></td>
            <td><c:out value="${driver.last_modified_time}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>

<br/>
<h2>Driver status change list</h2>

<table>
    <tbody>
    <tr><th>Driver</th><th>Status</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.driverStatusChangeList}" var="driverStatusChange">
        <tr><td><c:out value="${driverStatusChange.driver.name}"></c:out></td>
            <td><c:out value="${driverStatusChange.status}"></c:out></td>
            <td><c:out value="${driverStatusChange.created_time}"></c:out></td>
            <td><c:out value="${driverStatusChange.last_modified_time}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>

<br/>
<h2>Order list</h2>

<table>
    <tbody>
    <tr><th>Truck number</th><th>Drivers</th><th>Waypoints</th><th>Completed</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.orderList}" var="order">
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

<br/>
<h2>Waypoint list</h2>

<table>
    <tbody>
    <tr><th>Operation</th><th>City</th><th>Freight</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.waypointList}" var="waypoint">
        <tr><td><c:out value="${waypoint.operation}"></c:out></td>
            <td><c:out value="${waypoint.location.city}"></c:out></td>
            <td><c:out value="${waypoint.freight.name}"></c:out></td>
            <td><c:out value="${waypoint.created_time}"></c:out></td>
            <td><c:out value="${waypoint.last_modified_time}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>

<br/>
<h2>Freight list</h2>

<table>
    <tbody>
    <tr><th>Name</th><th>Weight</th><th>Status</th><th>Added</th><th>Last modified</th></tr>
    <c:forEach items="${sessionScope.freightList}" var="freight">
        <tr><td><c:out value="${freight.name}"></c:out></td>
            <td><c:out value="${freight.weight}"></c:out></td>
            <td><c:out value="${freight.status}"></c:out></td>
            <td><c:out value="${freight.created_time}"></c:out></td>
            <td><c:out value="${freight.last_modified_time}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
