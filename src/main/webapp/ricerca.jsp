<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//ricerca.css">

<meta charset="UTF-8">
<title>ricerca page</title>
</head>
<body>
<h1>Benvenuto nella schermata per ricercare il volume </h1>
<br>
<h2> Utente loggato : ${param.email} </h2>
<br>
<br>

 <c:set var = "oggetto" scope = "session" value = "${beanS.getTypeB()}"/>


<form action="RicercaServlet" method="post">

<br>
<div >
Scegliere il titolo / editore / autore che si desidera :
<input type="text" name="ricerca" id="ricerca" value=" titolo / editore / autore"/>
</div>
<br>
<div >
Scegliere dove cercare se in libri / giornali / riviste :
<input type="text" name="tipoR" id="tipoR" value="scegliere dove "/>
</div>
<br>
<br>

<c:choose>
  <c:when test="${oggetto=='libro'}">
  <table>
  <caption>Riepilogo ${beanS.getTypeB()} cercato</caption>
  <tr>
  <th>idOggetto</th>
  <th>Tipologia</th>
  <th>Titolo</th>
  <th>Editore</th>
  <th>Autore</th>
  </tr>

  <c:forEach items="#{beanRic.listaLB }" var="lista">
  <tr>
  <td>${ lista.getId()}</td>
  <td>${ lista.getCategoria()}</td>
  <td>${ lista.getTitolo() }</td>
  <td>${ lista.getEditore() }</td>
  <td>${ lista.getAutore() }</td>
  </tr>
  </c:forEach>
  </table>
  <br>
  <br>
  <p>
  eccezione : ${beanRic.getMexB()}
  </p>
  </c:when>
  <c:when test="${oggetto=='giornale'}">
    <table>
    <caption>Riepilogo ${beanS.getTypeB()} cercato</caption>
    <tr>
    <th>idOggetto</th>
    <th>Tipologia</th>
    <th>Titolo</th>
    <th>Editore</th>
    </tr>
    <tr>
    <c:forEach items="#{beanRic.listaGB }" var="lista">
    <tr>
    <td>${ lista.getId()}</td>
    <td>${ lista.getCategoria()}</td>
    <td>${ lista.getTitolo() }</td>
    <td>${ lista.getEditore() }</td>
    </tr>
    </c:forEach>
    </table>
    <br>
    <br>
    <p>
    eccezione : ${beanRic.getMexB()}
    </p>
    </c:when>
    <c:when test="${oggetto=='rivista'}">
        <table>
        <caption>Riepilogo ${beanS.getTypeB()} cercato</caption>
        <tr>
        <th>idOggetto</th>
        <th>Tipologia</th>
        <th>Titolo</th>
        <th>Editore</th>
        <th>Autore</th>
        </tr>
        <tr>
        <c:forEach items="#{beanRic.listaRB }" var="lista">
        <tr>
        <td>${ lista.getId()}</td>
        <td>${ lista.getCategoria()}</td>
        <td>${ lista.getTitolo() }</td>
        <td>${ lista.getEditore() }</td>
        <td>${ lista.getAutore() }</td>
        </tr>
        </c:forEach>
        </table>
        <br>
        <br>
        <p>
        eccezione : ${beanRic.getMexB()}
        </p>
        </c:when>

</c:choose>
<br>
<br>
<div>
<input type="submit" id="cerca" name="cerca" value="genera lista" class="cerca"/>
<input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>
</div>
</form>
</body>
</html>