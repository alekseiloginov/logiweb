<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Drivers</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav>
    <a href="TruckListService" title="Truck list">Trucks</a>
    <a href="OrderListService" title="Order list">Orders</a>
    <a href="welcome_manager.jsp" title="Welcome page">Home</a>
</nav>

<br>
<div class="container">
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
</div>

</body>
</html>
