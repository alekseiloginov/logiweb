<%@ page import="com.tsystems.javaschool.loginov.logiweb.models.Manager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager welcome page</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<nav>
    <a href="TruckListService" title="Truck list">Trucks</a>
    <a href="DriverListService" title="Driver list">Drivers</a>
    <a href="OrderListService" title="Order list">Orders</a>
</nav>
<br>

<div class="container">
<%Manager manager = (Manager) session.getAttribute("manager");%>
<h1>Hello, <%=manager.getName()%>!</h1>
<p>Your email: <%=manager.getEmail()%></p>

<form action="LogoutService" method="post">
    <input type="submit" value="Logout">
</form>

</div>
</body>
</html>
