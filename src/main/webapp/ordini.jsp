<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//ordini.css">

<meta charset="UTF-8">
<title>report page</title>
</head>
<body>
<h1> Qui &#232; possibile consultare gli ordini di ${beanUb.getEmailB()}<br>
 <br>
<form action="OrdiniServlet" method="post">
<div>


<table >
<caption>riepilogo pagamenti</caption>
<tr>
<th>idPagamento</th>
<th>metodo</th>
<th>spesaTotale</th>
<th>tipoAcquisto</th>
<th>idProdotto</th>
</tr>

<c:forEach items="#{beanP.listaPagamentiB}" var="lista">


<tr>

<td>${ lista.getIdPag() }</td>
<td>${ lista.getMetodo() }</td>
<td>${ lista.getAmmontare() }</td>
<td>${ lista.getTipo() }</td>
<td>${ lista.getIdOggetto() }</td>



</tr>
</c:forEach>

</table>

<br>
<br>

leggere idPagamento da cancellare : <input type="text" name="idP" id="idP" value="pagameto da eliminare"/>

<br>
<br>


<input type="submit" id="genera" name="genera" value="genera" class="genera"/>
<input type="submit" id="elimina" name="elimina" value="elimina" class="elimina"/>
<input type="submit" id="indietro" name="indietro" value="indietro" class="annulla"/>

</div>
</form>
</body>
</html>