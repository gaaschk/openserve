<%@ page isErrorPage="true"%>
<html>
<head>
<title>Error Page</title>
</head>
<body>
	<h2>Your application has generated an error</h2>
	<h3>Please notify your help desk.</h3>
	<b>Exception:</b>
	<br>
	<%=exception.toString()%>
</body>
</html>