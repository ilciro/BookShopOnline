<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//reset.css">

<meta charset="UTF-8">
<title>reset page</title>
</head>
<body>
<h1>Benvenuto nella schermata per resettare la password</h1>

<h1> Email : "${beanUb.getEmailB()}"</h1>

<p class="exception"> esito : ${beanUb.getMexB()}</p>



<form action="ResetServlet" method="post">
<div class="reset">

<p> Inserire nuova password :     <input type="password" name="pass" id="pass"/></p>
<br>
<br>
<img alt="" src="immagini/resetPwd.png" width=100 height=100/>
<br>
<br>
<input type="submit" class="resetPass" id="reset" name="reset" value="reset"/>
<br>
<br>
<input type="submit" class="indietro" id="indietro" name="indietro" value="indietro"/>
<br>

</div>
</form>

</body>
</html>