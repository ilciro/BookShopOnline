<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//registra.css">

<meta charset="UTF-8">
<title>registra page</title>
</head>
<body>
<h1>Benvenuto nella schermata per registrarsi</h1>



<form action="RegistraServlet" method="post">
<div class="registra">
<p> Inserire ruolo tra
    ADMIN/
    SCRITTORE/
    EDITORE/
    UTENTE/
    WRITER :     <input type="text" name="ruolo" id="ruolo"/>
<br>
<p> Inserire nome :     <input type="text" name="nome" id="nome"/>
<br>
<p> Inserire cognome :     <input type="text" name="cognome" id="cognome"/>
<br>
<p> Inserire email :     <input type="text" name="email" id="email"/>
<br>
<p> Inserire password :     <input type="password" name="pwd" id="pwd"/>
<br>
<p> Inserire descrizione :    <textarea class="textarea" row="5" col="100" id="textarea" name="textarea"></textarea>
<br>
<p> Inserire data nascita :    <input type="date" name="data" id="data"/>
<br>
<br>
<br>
<img alt="" src="immagini/user.png" width=100 height=100/>
<br>
<br>
<input type="submit" class="reg" id="registra" name="registra" value="registra"/>
<br>
<br>
<input type="submit" class="indietro" id="indietro" name="indietro" value="indietro"/>
<br>
<p> ${beanUb.getMexB()}</p>
</div>
</form>



<div>
${beanUb.getMexB()}
</div>

</body>
</html>