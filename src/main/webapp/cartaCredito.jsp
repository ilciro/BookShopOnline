<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//cartaCredito.css">
<meta charset="UTF-8">
<title>Carta Credito page</title>
</head>
<body>
<h1>Benvenuto nella schermata del pagamento con carta di credito</h1>

<h2>Inserire le credenziali</h2>




<form action="CartaCreditoServlet" method="post">
<table>
<caption></caption>
<tr>
<th scope="col">
</th>
<tr>
<td>
inserire nome
</td>
<td>
</td>
<td>
<input type="text" id="nomeL" name="nomeL" value="${beanUB.getNomeB()}" >
</td>
</tr>
<tr>
<td>
inserire cognome
</td>
<td>
</td>
<td>
<input type="text" id="cognomeL" name="cognomeL" value="${beanUB.getCognomeB()}">
</td>
</tr>
<tr>
<td>
inserire codice carta
</td>
<td>
xxxx-xxxx-xxxx-xxxx
</td>
<td>
<input type="text" id="cartaL" name="cartaL" >
</td>
</tr>
<tr>
<td>
inserire data scadenza
</td>
<td>
yyyy/mm/dd (usare '/')
</td>
<td>
<input type="text" id="scadL" name="scadL" >
</td>
</tr>
<tr>
<td>
inserire pin
</td>
<td>
xyz
</td>
<td>
<input type="password" id="passL" name="passL" >
</td>
</tr>
<tr>
<td>
<br>
</td>
<td>
<input type="submit" id="buttonI" name="buttonI" value="paga" class="invia">
</td>
<td>
<input type="submit" id="buttonA" name="buttonA" value="annulla" class="annulla">
</td>
</table>

<br>
<br>

<c:set var = "tipo" scope = "session" value = "${bean1.isLoggedB()}"/>

<c:choose>
<c:when test="${ tipo==true}">

<div>
<input type="submit" id="regB" name="regB" value="registra e paga" class="registra">
</div>

<br>
<br>
<table>
<caption>elenco carte </caption>
<tr>
<th scope="col"></th>
<tr>
<tr>
<td>
numero carta
</td>
<!--  mette	re foreach by database -->
<c:forEach items="#{beanL.listaCarteCreditoB }" var="lista">


<tr>


<td>${ lista.getNumero() }</td>

</tr>
</c:forEach>
</table>

<br>
<div>

inserire nome utente :

<input type="text" name="utenteL" id="utenteL">

<input type="submit" name="prendiDB" id="prendiDB" value="genera lista">
</div>


</c:when>
</c:choose>

</form>



</body>
</html>