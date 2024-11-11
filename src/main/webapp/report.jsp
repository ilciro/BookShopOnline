<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//report.css">

<meta charset="UTF-8">
<title>report page</title>
</head>
<body>
<h1> Qui &#232; possibile generare tutti i report ( oggetti /  utenti) </h1>

<form action="ReportServlet" method="post">
<div>
<table class="oggetto">
<caption>riepilogo oggetti</caption>
<tr>
<th>idProdotto</th>
<th>titolo</th>
<th>categoria</th>
<th>spesaTotale</th>
</tr>

<c:forEach items="#{beanR.elencoReportB }" var="lista">


<tr>


<td>${ lista.getIdReport() }</td>
<td>${ lista.getTitoloOggetto() }</td>
<td>${ lista.getTipologiaOggetto() }</td>
<td>${ lista.getTotale() }</td>


</tr>
</c:forEach>
</table>

<br>
<br>

<table class="utente">
<caption>riepilogo utenti</caption>
<tr>
<th>idUser</th>
<th>idRuolo</th>
<th>nome</th>
<th>cognome</th>
<th>email</th>
<th>pwd</th>
<th>descrizione</th>
<th>dataDiNascita</th>
</tr>

<c:forEach items="#{tempUb.lista}" var="lista">


<tr>


<td>${ lista.getIdB() }</td>
<td>${ lista.getRuoloB() }</td>
<td>${ lista.getNomeB() }</td>
<td>${ lista.getCognomeB() }</td>
<td>${ lista.getEmailB() }</td>
<td>${ lista.getPassB() }</td>
<td>${ lista.getDescrizioneB() }</td>
<td>${ lista.getDataDiNascitaB() }</td>


</tr>
</c:forEach>

</table>

<br>
<br>

<input type="submit" id="libri" name="libri" value="libri" class="libri"/>
<input type="submit" id="giornali" name="giornali" value="giornali" class="giornali"/>
<input type="submit" id="riviste" name="riviste" value="riviste" class="riviste"/>
<input type="submit" id="utenti" name="utenti" value="utenti" class="utenti"/>
<input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>

</div>
</form>
</body>
</html>