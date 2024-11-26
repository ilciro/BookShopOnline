<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//giornali.css">

<meta charset="UTF-8">
<title>Pagina giornali</title>
</head>
<body>
<h1> Elenco oggetti prensenti nel db</h1>

<form action="GiornaliServlet" method="post">
<table>
<caption>Riepilogo giornali</caption>
<tr>
<th>
titolo
</th>
<th>
tipologia
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
copie rimanenti
</th>
<th>
disp
</th>
<th>
prezzo
</th>
<th>
id
</th>
</tr>

<c:forEach items="#{beanG.listaGiornaliB }" var="lista">


<tr>

<c:choose>
<c:when test="${lista.getCopieRimanenti()<=0 || lista.getDisponibilita()==0}">
<td style="background-color:RED">${ lista.getTitolo() }</td>
<td style="background-color:RED">${ lista.getCategoria() }</td>
<td style="background-color:RED">${ lista.getLingua() }</td>
<td style="background-color:RED">${ lista.getEditore() }</td>
<td style="background-color:RED">${ lista.getDataPubb()}</td>
<td style="background-color:RED">${ lista.getCopieRimanenti() }</td>
<td style="background-color:RED">${ lista.getDisponibilita() }</td>
<td style="background-color:RED">${ lista.getPrezzo() }</td>
<td style="background-color:RED">${ lista.getId()}</td>
</c:when>
<c:otherwise>
<td style="background-color:GREEN">${ lista.getTitolo() }</td>
<td style="background-color:GREEN">${ lista.getCategoria() }</td>
<td style="background-color:GREEN">${ lista.getLingua() }</td>
<td style="background-color:GREEN">${ lista.getEditore() }</td>
<td style="background-color:GREEN">${ lista.getDataPubb()}</td>
<td style="background-color:GREEN">${ lista.getCopieRimanenti() }</td>
<td style="background-color:GREEN">${ lista.getDisponibilita() }</td>
<td style="background-color:GREEN">${ lista.getPrezzo() }</td>
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

<div class="exception">
${beanG.getMexB()}
</div>





</body>
</html>