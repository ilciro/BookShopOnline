<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang=it>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css//download.css">

<title>Download page</title>
</head>
<body>
<h1> Benvenuto nella schermata per il download</h1>
<br>
<br>


<c:set var = "tipo" scope = "session" value = "${beanS.getTypeB() }"/>

<c:choose>
<c:when test="${ tipo=='libro'}">
<form action="DownloadServlet" method="post">
<div>
titolo del libro da scaricare:
<input type="text" id="titoloL" name="titoloL" value="${beanS.getTitoloB()}">
</div>
<br>
<div>
<input type="submit" class="invia" id="downloadB" name="downloadB" value="scarica e leggi">
<input type="submit" class="annulla" id="annullaB" name="annullaB" value="annulla">
</div>
</form>
</c:when>
<c:when test="${ tipo=='giornale'}">
<form action="DownloadServletG" method="post">
<div>
titolo del giornale da scaricare:
<input type="text" id="titoloL" name="titoloL" value="${beanS.getTitoloB()}">
</div>
<br>
<div>
<input type="submit" class="invia" id="downloadB" name="downloadB" value="scarica e leggi">
<input type="submit" class="annulla" id="annullaB" name="annullaB" value="annulla">
</div>
</form>
</c:when>
<c:when test="${ tipo=='rivista'}">
<form action="DownloadServletR" method="post">
<div>
titolo della rivista da scaricare:
<input type="text" id="titoloL" name="titoloL" value="${beanS.getTitoloB()}">
</div>
<br>
<div>
<input type="submit" class="invia" id="downloadB" name="downloadB" value="scarica e leggi">
<input type="submit" class="annulla" id="annullaB" name="annullaB" value="annulla">
<input type="submit" class="homepage" id="homePage" name="homePage" value="home page">

</div>
</form>
</c:when>
</c:choose>
<br>
<br>

<p> parametri </p>
<p> id da systembean :${beanS.getIdB()}</p>
<p> titolo da susyemBean : ${beanS.getTitoloB()}</p>
<p> titolo del bean download :${beanD.getTitoloB()}</p>
<div>
<a href="${beanD.getTitoloB()}">open pdf</a>
</div>
</body>
</html>