<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//riviste.css">
<meta charset="UTF-8">
<title>pagina riviste</title>
</head>
<body>
<h1> Elenco oggetti prensenti nel db</h1>

<form action="RivisteServlet" method="post">
<table>
<caption>riepilogo rivista</caption>
<tr>
<th>
titolo
</th>
<th>
tipologia
</th>
<th>
autore
</th>
<th>
lingua
</th>
<th>
editore
</th>
<th>
data pubblicazione
</th>
<th>
disp
</th>
<th>
prezzo
</th>
<th>
copie rimanenti
</th>
<th>
id
</th>
<c:forEach items="#{beanR.listaRivisteB }" var="lista">


<tr>
<c:choose>
<c:when test="${lista.getDisp()<=0 || lista.getCopieRim()<=0}">

<td style="background-color:RED">${ lista.getTitolo() }</td>
<td style="background-color:RED">${ lista.getCategoria() }</td>
<td style="background-color:RED">${ lista.getAutore() }</td>
<td style="background-color:RED">${ lista.getLingua() }</td>
<td style="background-color:RED">${ lista.getEditore() }</td>
<td style="background-color:RED">${ lista.getDataPubb()}</td>
<td style="background-color:RED">${ lista.getDisp() }</td>
<td style="background-color:RED">${ lista.getPrezzo() }</td>
<td style="background-color:RED">${ lista.getCopieRim() }</td>
<td style="background-color:RED">${ lista.getId()}</td>
</c:when>
<c:otherwise>
<td style="background-color:GREEN">${ lista.getTitolo() }</td>
<td style="background-color:GREEN">${ lista.getCategoria() }</td>
<td style="background-color:GREEN">${ lista.getAutore() }</td>
<td style="background-color:GREEN">${ lista.getLingua() }</td>
<td style="background-color:GREEN">${ lista.getEditore() }</td>
<td style="background-color:GREEN">${ lista.getDataPubb()}</td>
<td style="background-color:GREEN">${ lista.getDisp() }</td>
<td style="background-color:GREEN">${ lista.getPrezzo() }</td>
<td style="background-color:GREEN">${ lista.getCopieRim() }</td>
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


<input type="text" id="idOgg" name="idOgg">
<br>
<br>



<input type="submit" class="invia" id="procedi" name="procedi" value="procedi">

<input type="submit" class="genera" id="genera lista" name="genera lista" value="genera lista" >

<input type="submit" class="annulla" id="annulla" name="annulla" value="indietro">
<br>
</div>

</form>

</body>
</html>