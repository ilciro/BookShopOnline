<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//login.css">

<meta charset="UTF-8">
<title>login page</title>
</head>
<body>
<h1> Benvenuto nella pagina di login</h1>

<p> ecco i parametri <p>
<p> email : ${beanUb.getEmailB()}</p>
<p> pass : ${beanUb.getPassB()}</p>
<p> ruolo : ${beanUb.getRuoloB()}</p>

<h2> Accedere con le credenziali oppure registrarsi</h2>

<form action="LoginServlet" method="post">
<div>
inserire email :
<input type="text"  id="emailL" name="emailL" >
<br>
<br>
inserire password:
<input type="password" id="passL" name="passL" >


<br>
<br>
<input type="submit" id="loginB" name="loginB" value="login" class="invia">
<input type="submit" id="annullaB" name="annullaB" value="indietro" class="annulla">
<input type="submit" id="registerB" name="registerB" value="registrati" class="registrati">
<input type="submit" id="resetB" name="resetB" value="reset password" class="reset">

<br>
<br>

<p class="exception">
${beanUb.getMexB() }
</p>
</div>


</form>
</body>
</html>