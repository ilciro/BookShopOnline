

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

<h1> Benvenuto </h1>

<p> ecco il tipo di oggetto dal bean : ${beanS.getTypeB()}</p>

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


<td>${ lista.getTitolo() }</td>
<td>${ lista.getTipologia() }</td>
<td>${ lista.getLingua() }</td>
<td>${ lista.getEditore() }</td>
<td>${ lista.getDataPubb()}</td>
<td>${ lista.getCopieRimanenti() }</td>
<td>${ lista.getDisponibilita() }</td>
<td>${ lista.getPrezzo() }</td>
<td>${ lista.getId()}</td>

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