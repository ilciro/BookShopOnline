<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//gestioneUtenti.css">

<meta charset="UTF-8">
<title>report page</title>
</head>
<body>
<h1> Qui &#232; possibile modificare gli utenti del sistema </h1>
<h2> Per modificare e/o eliminare utente scegliere id </h>
<br>
   <h2> IdUser &#232; quello della lista </h2>


<form action="GestioneUtentiServlet" method="post">
<div>


<table class="utente">
<caption>riepilogo utenti</caption>
<tr>
<th >idUserLista</th>
<th>idUserDb</th>
<th>idRuolo</th>
<th>nome</th>
<th>cognome</th>
<th>email</th>
<th>pwd</th>
<th>descrizione</th>
<th>dataDiNascita</th>
</tr>

<c:forEach items="#{tempUb.lista}" var="lista">


<tr>

<td style="background-color:YELLOW">${ lista.getIdB()-1 }</td>
<td>${ lista.getIdBNOS() }</td>
<td>${ lista.getRuoloBNOS() }</td>
<td>${ lista.getNomeBNOS() }</td>
<td>${ lista.getCognomeBNOS() }</td>
<td>${ lista.getEmailBNOS() }</td>
<td>${ lista.getPassBNOS() }</td>
<td>${ lista.getDescrizioneBNOS() }</td>
<td>${ lista.getDataDiNascitaBNOS() }</td>


</tr>
</c:forEach>

</table>

<br>
<br>

<input type="text" id="idUtente" name="idUtente" value=" scegliere id"/>
<br>
<br>

<input type="submit" id="genera" name="genera" value="genera" class="genera"/>
<input type="submit" id="inserisci" name="inserisci" value="inserisci" class="inserisci"/>
<input type="submit" id="modifica" name="modifica" value="modifica" class="modifica"/>
<input type="submit" id="elimina" name="elimina" value="elimina" class="elimina"/>
<input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>

</div>
</form>
</body>
</html>