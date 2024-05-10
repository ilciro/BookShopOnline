<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//register.css">

<meta charset="UTF-8">
<title>Register page</title>
</head>
<body>

<h1> benvenuto nella schermata di registrazione</h1>

<h2> Inserire credenziali</h2>

<form action="RegistrazioneServlet" method="post">
<div>
<img alt="source not found" src="immagini/register-button-png-26.png" width=200  height=100 >

<br>
<br>
 inserire nome :
 <input type="text" id="nomeL" name="nomeL">
 <br>
 <br>
 inserire cognome :
 <input type="text" id="cognomeL" name="cognomeL">
 <br>
 <br>
 inserire email :
 <input type="text" id="emailL" name="emailL">
 <br>
 <br>
 inserire password :
 <input type="password" id="passL" name="passL">
 <br>
 <br>
 conferma password :
 <input type="password" id="confermaPassLL" name="confermaPassLL">
 <br>
 <br>
 inserire data (usare '/') formato(yyyy/mm/dd)" :
 <input type="text" id="dataL" name="dataL">

 <br>
 <br>

 <input type="submit" id="inviaB" name="inviaB" value="registrati" class="invia">
  <input type="submit" id="annullaB" name="annullaB" value="indietro" class="annulla">


 <br>
 <br>
 ${beanUb.getMexB()}

 </div>
</form>

</body>
</html>