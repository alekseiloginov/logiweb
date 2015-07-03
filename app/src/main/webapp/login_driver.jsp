<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>Driver login page</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav>
  <a href="landing.html" title="Go to the welcome page">Home</a>
  <button>Fill form</button>
</nav>
<br>

<div class="container">
  <h2>Enter your email and password</h2>

  <form name="loginForm" action="Login.do" onsubmit="return validateLoginForm()" method="post">
    <input type="hidden" name="role" value="driver">
    <input id="email" type="text" name="email" placeholder="email"><br>
    <input id="password" type="password" name="password" placeholder="password"><br>
    <input type="submit" value="Login">
  </form>
  <div>
    <c:if test="${not empty requestScope.error}">
      <p class="db_error">${requestScope.error}</p>
    </c:if>

    <p class="error">Error message</p>
    <p class="success">Success message</p>
  </div>
</div>

<script src="https://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/script.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>
