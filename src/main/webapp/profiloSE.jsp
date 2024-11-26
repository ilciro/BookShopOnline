<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//profiloSE.css">




<meta charset="UTF-8">
<title>admin page</title>
</head>
<body>

<h1> Benvenuto nella schermata per gestire il profilo di ${beanUB.getEmailB()}</h1>
<br>
<br>
<h2> Scegliere se modificare oppure cancellare </h2>

<form action="ProfiloSEServlet" method="post">
<table>
<caption> anagrafica utente</caption>
<tr>
<th> idRuolo </th>
<th> nome </th>
<th> cognome </th>
<th> email </th>
<th> pwd </th>
<th> descrizione </th>
<th> dataNascita </th>
</tr>

<c:forEach items="#{beanTub.lista }" var="lista">
<tr>
  <td >${ lista.getRuoloB() }</td>
   <td >${ lista.getNomeB() }</td>
    <td >${ lista.getCognomeB() }</td>
     <td >${ lista.getEmailB() }</td>
      <td >${ lista.getPassB() }</td>
       <td >${ lista.getDescrizioneB() }</td>
        <td >${ lista.getDataDiNascitaB() }</td>
        </tr>
</c:forEach>
</table>
<br>
<h2> Scegliere elementi da modificare </h2>
<h1> Ruoli possibili W,S,E,A !! </h1>
<div>
<br>
<br>
Ruolo da modificare : <input type="text" name="ruoloN"id="ruoloN" value="${beanTub.getRuoloB()}"/>
<br>
<br>
Nome da modificare : <input type="text" name="nomeN"id="nomeN" value="${beanTub.getNomeB()}"/>
<br>
<br>
Cognome da modificare : <input type="text" name="cognomeN"id="cognomeN" value="${beanTub.getCognomeB()}"/>
<br>
<br>
Email da modificare : <input type="text" name="mailN"id="mailN" value="${beanTub.getEmailB()}"/>
<br>
<br>
Pass da modificare : <input type="password" name="passN"id="passN" value="${beanTub.getPassB()}"/>
<br>
<br>
Descrizione da modificare : <input type="text" name="descN"id="descN" value="${beanTub.getDescrizioneB()}"/>
<br>
<br>
Data da modificare : <input type="date" name="dataN"id="dataN"/>
<br>
<br>
</div>
<div>
<input type="submit" name="genera" id="genera" value="genera" class="genera"/>
<input type="submit" name="modifica" id="modifica" value="modifica" class="modifica"/>
<input type="submit" name="cancella" id="cancella" value="cancella" class="cancella"/>
<input type="submit" name="indietro" id="indietro" value="indietro" class="indietro"/>
</div>


</form>





</body>
</html>