<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//utenti.css">
<meta charset="UTF-8">
<title>User page</title>
</head>
<body>

<h1> Loggato come ${beanUb.getRuoloB()}</h1>
<h2> Scegli cosa vuoi fare</h2>

<p> parametri:</p>
<p> email : ${beanUb.getEmailB()}</p>
<p> ruolo : ${beanUb.getRuoloB()}</p>
<p> pass: ${beanUb.getPassB()}</p>
<p> loggato : ${beanS.isLoggedB()}</p>


<form action="UtentiServlet" method="post">

<c:set var = "tipo" scope = "session" value = "${beanUb.getRuoloB() }"/>

<c:choose>
<c:when test="${ tipo=='A'}">

<table>

<caption>
gestione profilo
</caption>

<tr>
<th scope="col">
visualizza profilo
</th>
</tr>
<tr>
<td>
<img alt="source not found" src="immagini/vector-users-icon-png_302626.jpg" width=100  height=100 >
</td>
</tr>
<td>
<input type="submit" name="buttonProfilo" id="buttonProfilo" value="profilo" class="profilo">
</td>
</tr>
</table>

</c:when>

<c:when test="${(tipo=='E') || ( tipo=='W') || ( tipo=='S') ||( tipo=='U')}" >
<table>
<caption>
scegliere tra libri , giornali e riviste ,logout , ricerca, gestione profilo
<br>
</caption>
<tr>
<th scope="col">
libro
</th>
<th>
giornale
</th>
<th>
rivista
</th>
<th>
logout
</th>
<th>
ricerca
</th>
</tr>
<tr>
<td>
<img alt="source not found" src="immagini/libro.png_860.png" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/newspaper-284-542534.png" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/una-rivista_318-1607.jpg" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/icons8-logout-rounded-48.png" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/Search-icon-13.png" width=100  height=100 >
</td>
</tr>
<tr>
<td>
<input type="submit" name="buttonL" id="buttonL" value="libri" class="libri">
</td>
<td>
<input type="submit" name="buttonG" id="buttonG" value="giornali" class="giornali">
</td>
<td>
<input type="submit" name="buttonR" id="buttonR" value="riviste" class="riviste">
</td>
<td>
<input type="submit" name="buttonLogout" id="buttonLogout" value="logout" class="logout">
</td>
<td>
<input type="submit" name="buttonRic" id="buttonRic" value="ricerca" class="ricerca">
</td>

</tr>
</table>
</c:when>
</c:choose>



</form>

</body>
</html>