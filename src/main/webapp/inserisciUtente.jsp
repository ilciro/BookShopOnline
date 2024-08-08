<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//aggiungiUtente.css">
<meta charset="UTF-8">
<title>insert utente page</title>
</head>
<body>
<h1> Benvenuto nella pagina per inserire un nuovo utente</h1>

<h2> Compilare il form</h2>

<form action="InserisciUtenteServlet" method="post">
<div>
nome:
<input type="text" id="nomeU" name="nomeU">
<br>
<br>
cognome:
<input type="text" id="cognomeU" name="cognomeU">
<br>
<br>
email:
<input type="text" id="emailU" name="emailU">
<br>
<br>
pwd:
<input type="password" id="passU" name="passU">
<br>
<br>
descrizione:
<input type="text" id="descU" name="descU">
<br>
<br>
data di nascita (yyyy/mm/dd):
<input type="text" id="dataU" name="dataU">
<br>
<br>
<input type="submit" id="buttonI" name="buttonI" value="invia" class="invia">
<input type="submit" id="buttonA" name="buttonA" value="indietro" class="annulla">

<p class="exception">
${beanUb.getMexB() }
</p>
</div>
</form>

</body>
</html>