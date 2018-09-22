<!DOCTYPE html>
<html>
<head>
	<title>CSSO</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<form class="form" action="/springmvc/login" method='POST'>
			<label for="username">Email: </label>
			<input class="form-control" id="username" name="username" autofocus required>
			<label for="password">Password: </label><input class="form-control" id="password" name="password" required>
			<br/>
			<input class="form-control btn btn-lg btn-primary" type="submit">
		</form>
	</div>
</body>
</html>