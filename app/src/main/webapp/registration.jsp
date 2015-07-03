<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>Manager sign-up Page</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav>
  <a href="landing.html" title="Go to the welcome page">Home</a>
  <a href="login_manager.jsp" title="Go to the manager login page">Log in</a>
  <button>Fill form</button>
</nav>
<br>

<div class="container">
  <h2>Enter your name, surname, email and password</h2>

  <form name="regForm" action="Register.do" onsubmit="return validateRegForm()" method="post">
    <input id="name" type="text" name="name" placeholder="name"><br>
    <input id="surname" type="text" name="surname" placeholder="surname"><br>
    <input id="email" type="text" name="email" placeholder="email"><br>
    <input id="password" type="password" name="password" placeholder="password"><br>
    <input type="submit" value="Sign-up">
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