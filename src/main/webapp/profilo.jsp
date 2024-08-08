<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//profilo.css">
<meta charset="UTF-8">
<title>Profilo page</title>
</head>
<body>

<h1>Benvenuto nella schermata del profilo</h1>

<h2> Scegliere cosa fare</h2>

<form action="ProfiloServlet" method="post">

<c:set var = "tipo" scope = "session" value = "${beanUb.getRuoloB() }"/>

<c:choose>
<c:when test="${ tipo=='A'}">


<table>
<caption>
elenco azioni possibili
</caption>

<tr>
<th scope="col">
</th>
</tr>


<tr>
<td>

<textarea id="utenti" name="utenti" rows="25" cols="85" class="textareaProp">
${beanUb.getStringB()}
</textarea>
</td>

<td>
<input type="submit" class="prendiDati" id="genera lista" name="genera lista" value="genera lista" >
</td>
<td>
<input type="submit" class="nuovoUtente" id="inserisci" name="inserisci" value="nuovo utente">
</td>
<td>
<input type="text" id="idOgg" name="idOgg">
<br>
<input type="submit" class="modifica" id="modifica" name="modifica" value="modifica utente">
</td>
<td>
<input type="submit" class="cancella" id="elimina" name="elimina" value="cancella utente">
</td>
<td>
<input type="submit" class="annulla" id="indietro" name="indietro" value="indietro">
</td>
</tr>
</table>
</c:when>

<c:when test="${ tipo=='W' || tipo=='S' || tipo=='E' || tipo=='U'}">

<div>
nome utente:
<input type="text" name="nomeL" id="nomeL" value="${beanUb.getNomeB()}">
<br>
<br>
cognome utente:
<input type="text" id="cognomeL" name="cognomeL" value="${beanUb.getCognomeB() }">
<br>
<br>
email utente:
<input type="text" id="emailL" name="emaiL" value="${beanUb.getEmailB() }">
<br>
<br>
data nascita:
<input type="text" id="dataL" name="dataL" value="${beanUb.getDataDiNascitaB() }">
<br>
<br>

<table>
<caption>
lista pagamenti
</caption>
<tr>
<th>
id operazione
</th>
<th>
metodo acquisto
</th>
<th>
esito
</th>
<th>
nome utente
</th>
<th>
spesa totale
</th>
<th>
tipo acquisto
</th>
<th>
id prodotto
</th>
</tr>

<c:forEach items="#{beanP.listaPagamentiB }" var="lista">


<tr>


<td>${ lista.getId() }</td>
<td>${ lista.getMetodo() }</td>
<td>${ lista.getEsito() }</td>
<td>${ lista.getNomeUtente() }</td>
<td>${ lista.getAmmontare() }</td>
<td>${ lista.getTipo() }</td>
<td>${ lista.getIdOggetto()}</td>

</tr>
</c:forEach>

</table>

<input type="submit" name="prendiDatiB" id="prendiDatiB" value="prendi dati" class="prendiDati">
<input type="submit" name="ordiniB" id="ordiniB" value="cronologia ordini" class="ordini">
<input type="submit" name="indietroB" id="indietroB" value="indietro" class="annulla">

<p class="exception">
${beanUb.getMexB() }
</p>
</div>

</c:when>



</c:choose>



</form>

</body>
</html>