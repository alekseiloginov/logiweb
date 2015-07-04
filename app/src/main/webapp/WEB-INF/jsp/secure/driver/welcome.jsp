<%@ page import="com.tsystems.javaschool.loginov.logiweb.models.Driver" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver welcome page</title>
    <link rel="stylesheet" href="../../../css/style.css">
</head>
<body>
<nav>
    <a href="Orders.do" title="Order list">Orders</a>
</nav>
<br>

<div class="container">
<%Driver driver = (Driver) session.getAttribute("user");%>
<h1>Hello, <%=driver.getName()%>!</h1>
<p>Your email: <%=driver.getEmail()%></p>

<form action="Logout" method="post">
    <input type="submit" value="Logout">
</form>
</div>

</body>
</html>
