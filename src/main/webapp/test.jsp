<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test page</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>



<table>
    <tbody>
    <tr><th>Name</th><th>Surname</th><th>Email</th></tr>
    <c:forEach items="${sessionScope.managerList}" var="manager">
        <tr><td><c:out value="${manager.name}"></c:out></td>
            <td><c:out value="${manager.surname}"></c:out></td>
            <td><c:out value="${manager.email}"></c:out></td></tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>
