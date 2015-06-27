<%@ page import="com.tsystems.javaschool.loginov.logiweb.models.Driver" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver welcome page</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<nav>
    <a href="OrderListService" title="Order list">Orders</a>
</nav>
<br>
<%Driver driver = (Driver) session.getAttribute("driver");%>
<h1>Hello, <%=driver.getName()%>!</h1>
<p>Your email: <%=driver.getEmail()%></p>

<form action="LogoutService" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
