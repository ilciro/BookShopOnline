<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//libri.css">
<meta charset="ISO-8859-1">
<title>Pagina libri</title>
</head>
<body>

<p> ecco i parametri </p>
<p> systemBean type : ${beanS.getTypeB()}</p>
<p> systemBena logged : ${beanS.isLoggedB()}</p>
<p> email : ${beanUb.getEmailB()}





<h1> Elenco libri presenti nel db</h1>

<form action="LibriServlet" method="post">
<table>
<caption>Riepilogo libri </caption>
<tr>
<th>
titolo
</th>
<th>
codice isbn
</th>
<th>
editore
</th>
<th>
autore
</th>
<th>
lingua
</th>
<th>
categoria
</th>
<th>
data pubblicazione
</th>
<th>
recensione
</th>
<th>
numero copie
</th>
<th>
descrizione
</th>
<th>
disponibilita
</th>
<th>
prezzo
</th>
<th>
copie
</th>
<th>
id prodotto
</th>
</tr>


<c:forEach items="#{beanL.elencoLibriB }" var="lista">


<tr>


<td>${ lista.getTitolo() }</td>
<td>${ lista.getCodIsbn() }</td>
<td>${ lista.getEditore() }</td>
<td>${ lista.getAutore() }</td>
<td>${ lista.getLingua() }</td>
<td>${ lista.getCategoria()}</td>
<td>${ lista.getDataPubb()}</td>
<td>${ lista.getRecensione() }</td>
<td>${ lista.getNrCopie() }</td>
<td>${ lista.getDesc() }</td>
<td>${ lista.getDisponibilita() }</td>
<td>${ lista.getPrezzo() }</td>
<td>${ lista.getNrCopie() }</td>
<td>${ lista.getId()}</td>

</tr>
</c:forEach>


</table>

<br>
<br>
<div>

Inserire id di oggetto scelto:


<input type="text" id="idOgg" name="idOgg" >
<br>
<br>



<input type="submit" class="invia" id="procedi" name="procedi" value="procedi">

<input type="submit" class="genera" id="genera lista" name="genera lista" value="genera lista" >

<input type="submit" class="annulla" id="annulla" name="annulla" value="home">
<br>
</div>


</form>

<div class="exception">
${beanL.getMexB()}
</div>



</body>
</html>