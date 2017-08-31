<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check User</title>
</head>
<body>
	<h3>File Successfully uploaded!</h3>
	<br />
	<h3>Check User Login:</h3>
	<form action="/login" method="post">
		Name:<input type="text" name="username" /><br /> <br /> Password:<input
			type="password" name="password" /><br /> <br /> <input
			type="submit" value="login" />
	</form>

	<h3>Migrate Data to Bigquery</h3>
	<form action="/migrate" method="get">
		<input type="submit" value="Migrate" />
	</form>
</body>
</html>