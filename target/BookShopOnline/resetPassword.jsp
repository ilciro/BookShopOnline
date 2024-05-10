<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//resetPassword.css">
<meta charset="UTF-8">
<title>reset password page</title>
</head>
<body>

<h1>Benvenuto nella schermata per resettare la password</h1>

<h2> Per cambiare la password compilare i campi</h2>

<form action="ResetPasswordServlet" method="post">
<div>

<img alt="source not found" src="immagini/password-icon.png" width=100  height=100 >
<br>
<br>
Inserire mail :
<input type="text" name="emailL" id="emailL">
<br>
<br>
Inserire vecchia password:
<input type="password" id="vecchiaPassL" name="vecchiaPassL" required >
<br>
<br>
Inserire nuova password:
<input type="password" id="nuovaPassL" name="nuovaPassL">
<br>
<br>
<input type="submit" id="buttonI" name="buttonI" value="reset pass" class="invia">
<input type="submit" id="buttonA" name="buttonA" value="indietro" class="annulla">

</div>
</form>
</body>
</html>