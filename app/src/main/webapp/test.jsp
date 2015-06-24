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


</body>
</html>
