<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//gestioneOggetti.css">

<meta charset="UTF-8">
<title>report page</title>
</head>
<body>
<h1> Qui &#232; possibile modificare gli oggetti del sistema </h1>
<h2> Per modificare e/o eliminare oggetto scegliere id </h>
<br>
   <h2> IdOggetto &#232; quello della lista </h2>
   <h2> Per modificare o eliminare quello del db </h2>
<br>
<h3> Scegliere tra libro-giornale-rivista </h3>


<form action="GestioneOggettiServlet" method="post">
<div>


<table >
<caption>riepilogo oggetti</caption>
<tr>
<th >idUserLista</th>
<th>idUserOggetto</th>
<th>titolo</th>
<th>categoria</th>
<th>prezzo</th>

</tr>

<c:forEach items="#{beanRepo.elencoReportB}" var="lista">


<tr>

<td style="background-color:YELLOW">${ lista.getId()-1 }</td>
<td style="background-color:GREEN">${ lista.getId() }</td>
<td>${ lista.getTitolo() }</td>
<td>${ lista.getCategoria() }</td>
<td>${ lista.getPrezzo() }</td>


</tr>
</c:forEach>

</table>

<br>
<br>
id selezionato :
<input type="text" id="idOggetto" name="idOggetto" value="scegliere id"/>
<br>
<br>
tipo oggetto desiderato :
<input type="text" id="tipoOgg" name="tipoOgg" value="${beanS.getTypeB()}"/>
<br>
<br>


<input type="submit" id="genera" name="genera" value="genera" class="genera"/>
<input type="submit" id="inserisci" name="inserisci" value="inserisci" class="inserisci"/>
<input type="submit" id="modifica" name="modifica" value="modifica" class="modifica"/>
<input type="submit" id="elimina" name="elimina" value="elimina" class="elimina"/>
<input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>

</div>
</form>
</body>
</html>