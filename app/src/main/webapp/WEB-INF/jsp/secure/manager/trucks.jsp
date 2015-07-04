<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trucks</title>
    <link rel="stylesheet" href="../../../../css/style.css" />
    <!-- jTable styles -->
    <link rel="stylesheet" href="../../../../jtable/themes/metro/lightgray/jtable.min.css" />
</head>
<body>

<nav>
    <a href="Drivers.do" title="Driver list">Drivers</a>
    <a href="Orders.do" title="Order list">Orders</a>
    <a href="Welcome.do" title="Welcome page">Home</a>
</nav>
<br><br>

<div class="container">
    <div id="TruckTableContainer"></div>
</div>

<!-- jQuery lib -->
<script src="https://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<!-- jQuery UI lib -->
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js" type="text/javascript"></script>
<!-- jTable script file -->
<script src="../../../../jtable/jquery.jtable.min.js" type="text/javascript"></script>
<!-- My script file -->
<script src="../../../../js/truck_script.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>
