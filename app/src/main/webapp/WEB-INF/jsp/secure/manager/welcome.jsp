<%@ page import="com.tsystems.javaschool.loginov.logiweb.models.Manager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager welcome page</title>
    <link rel="stylesheet" href="../../../css/style.css">
</head>
<body>
<nav>
    <a href="Trucks.do" title="Truck list">Trucks</a>
    <a href="Drivers.do" title="Driver list">Drivers</a>
    <a href="Orders.do" title="Order list">Orders</a>
    <a href="Freights.do" title="Freight list">Freights</a>
</nav>
<br>

<div class="container">
<%Manager manager = (Manager) session.getAttribute("user");%>
<h1>Hello, <%=manager.getName()%>!</h1>
<p>Your email: <%=manager.getEmail()%></p>

<form action="Logout" method="post">
    <input type="submit" value="Logout">
</form>

</div>
</body>
</html>
