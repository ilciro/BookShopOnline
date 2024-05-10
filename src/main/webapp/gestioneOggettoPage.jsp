<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css//gestioneOggetto.css">
<title>management page</title>
</head>
<body>

<h1> Benvenuto nella pagina della gestione di ${beanS.getTypeB()}</h1>

<h2> Scegliere cosa fare... Per modificare/eliminare inserire id oggetto corrispondente</h2>

<p> id del libro :${beanL.getIdB()}</p>
<p> id del libro sysBean :${beanS.getIdB()}</p>



<c:set var="tipo" scope="session" value="${beanS.getTypeB()}"/>


<form action="GestioneOggettoServlet" method="post">

<c:choose>
<c:when test="${tipo=='libro'}">
<div>
<table>

<caption>
lista oggetti
</caption>
<tr>
<th>
titolo
</th>
<th>
tipologia
</th>
<th>
editore
</th>
<th>
autore
</th>
<th>
prezzo
</th>
<th>
id
</th>
</tr>

<c:forEach items="#{beanMOB.miaListaB }" var="lista">
<tr>
<td>${ lista.getTitolo() }</td>
<td>${ lista.getCategoria()}</td>
<td>${ lista.getEditore()}</td>
<td>${ lista.getAutore()}</td>
<td>${ lista.getPrezzo() }</td>
<td>${ lista.getId()}</td>
</tr>
</div>
</c:forEach>
</table>

</c:when>

<c:when test="${tipo=='giornale'}">
<div>
<table>

<caption>
lista oggetti
</caption>
<tr>
<th>
titolo
</th>
<th>
tipologia
</th>
<th>
editore
</th>
<th>
lingua
</th>
<th>
prezzo
</th>
<th>
id
</th>
</tr>



<c:forEach items="#{beanMOB.miaListaB }" var="lista">
<tr>
<td>${ lista.getTitolo() }</td>
<td>${ lista.getTipologia()}</td>
<td>${ lista.getEditore()}</td>
<td>${lista.getLingua()} </td>
<td>${ lista.getPrezzo() }</td>
<td>${ lista.getId()}</td>
</tr>

</div>
</c:forEach>
</table>

</c:when>

<c:when test="${tipo=='rivista'}">
<div>
<table>

<caption>
lista oggetti
</caption>
<tr>
<th>
titolo
</th>
<th>
tipologia
</th>
<th>
editore
</th>
<th>
autore
</th>
<th>
prezzo
</th>
<th>
id
</th>
</tr>




<c:forEach items="#{beanMOB.miaListaB }" var="lista">
<tr>
<td>${ lista.getTitolo() }</td>
<td>${ lista.getTipologia()}</td>
<td>${ lista.getEditore()}</td>
<td>${ lista.getAutore()}</td>
<td>${ lista.getPrezzo() }</td>
<td>${ lista.getId()}</td>
</tr>

</div>
</c:forEach>

</table>


</c:when>
</c:choose>
<br>
<br>

<input type="submit" id="buttonGenera" name="buttonGenera" value="genera lista" class="genera">
<br>
<br>
<input type="submit" id="buttonAdd" name="buttonAdd" value="inserisci" class="aggiungi">

<p>
Inserire ID:
<input type="text" name="idL" id="idL">
<input type="submit" id="buttonMod" name="buttonMod" value="modifica" class="modifica">


<input type="submit" id="buttonCanc" name="buttonCanc" value="cancella" class="elimina">
</p>
<input type="submit" id="buttonI" name="buttonI" value="indietro" class="indietro">

</div>
</form>
</body>
</html>