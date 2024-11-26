<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//libri.css">
<meta charset="ISO-8859-1">
<title>Pagina libri</title>
</head>
<body>



<h1> Elenco oggetti presenti nel db</h1>




<form action="LibriServlet" method="post">






<table>
<caption>Riepilogo libro</caption>
<tr>
<th>
titolo
</th>
<th>
numPagine
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

<c:choose>
<c:when test="${lista.getNrCopie()<=0 || lista.getDisponibilita()==0}">
    <td style="background-color:RED">${ lista.getTitolo() }</td>
    <td style="background-color:RED">${ lista.getNrPagine() }</td>
    <td style="background-color:RED">${ lista.getCodIsbn() }</td>
    <td style="background-color:RED">${ lista.getEditore() }</td>
    <td style="background-color:RED">${ lista.getAutore() }</td>
    <td style="background-color:RED">${ lista.getLingua() }</td>
    <td style="background-color:RED">${ lista.getCategoria()}</td>
    <td style="background-color:RED">${ lista.getDataPubb()}</td>
    <td style="background-color:RED">${ lista.getRecensione() }</td>
    <td style="background-color:RED">${ lista.getNrCopie() }</td>
    <td style="background-color:RED">${ lista.getDesc() }</td>
    <td style="background-color:RED">${lista.getDisponibilita()}</td>
    <td style="background-color:RED">${ lista.getPrezzo() }</td>
    <td style="background-color:RED">${ lista.getNrCopie() }</td>
    <td style="background-color:RED">${ lista.getId()}</td>

    </c:when>
    <c:otherwise>
    <td >${ lista.getTitolo() }</td>
        <td style="background-color:GREEN">${ lista.getNrPagine() }</td>
        <td style="background-color:GREEN">${ lista.getCodIsbn() }</td>
        <td style="background-color:GREEN">${ lista.getEditore() }</td>
        <td style="background-color:GREEN">${ lista.getAutore() }</td>
        <td style="background-color:GREEN">${ lista.getLingua() }</td>
        <td style="background-color:GREEN">${ lista.getCategoria()}</td>
        <td style="background-color:GREEN">${ lista.getDataPubb()}</td>
        <td style="background-color:GREEN">${ lista.getRecensione() }</td>
        <td style="background-color:GREEN">${ lista.getNrCopie() }</td>
        <td style="background-color:GREEN">${ lista.getDesc() }</td>
        <td style="background-color:GREEN">${lista.getDisponibilita()}</td>
        <td style="background-color:GREEN">${ lista.getPrezzo() }</td>
        <td style="background-color:GREEN">${ lista.getNrCopie() }</td>
        <td style="background-color:GREEN">${ lista.getId()}</td>
</c:otherwise>
</c:choose>



</tr>
</c:forEach>


</table>

<br>
<br>
<div>

Inserire id di oggetto scelto:


<input type="text" id="idOgg" name="idOgg" value="idOgg">
<br>
<br>



<input type="submit" class="invia" id="procedi" name="procedi" value="procedi">

<input type="submit" class="genera" id="genera lista" name="genera lista" value="genera lista" >

<input type="submit" class="annulla" id="annulla" name="annulla" value="indietro">
<br>
</div>


</form>

<div class="exception">
${bean.getMexB()}
</div>



</body>
</html>