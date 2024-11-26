<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//utente.css">




<meta charset="UTF-8">
<title>utente page</title>
</head>
<body>

<form action="UtenteServlet" method="post">

<div>
        <h1>Benvenuto nella page di utente </h1>
                <h2>
                   Qui &#232; possibile comperare oggetti come Utente loggato.
                   <br>
                   <br>
                   Inoltre &#232; possibile accedere ad elenco degli ordini dello stesso
                </h2>

                <br>
                <br>



    <input type="submit" name="libri" id="libri" class="libro" value="libri"/>
     <input type="submit" name="giornali" id="giornali" class="giornale" value="giornali"/>
      <input type="submit" name="riviste" id="riviste" class="rivista" value="riviste"/>
       <input type="submit" name="cerca" id="cerca" class="cerca" value="cerca"/>
        <input type="submit" name="ordini" id="ordini" class="ordini" value="ordini"/>
         <input type="submit" name="indietro" id="indietro" class="logout" value="logout"/>

 </div>

</form>

</body>
</html>