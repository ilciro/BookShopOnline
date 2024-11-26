<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
         <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html lang=it>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css//fattura.css">
<title>Fattura page</title>
</head>
<body>






<h1> Benvenuto nella schermata del pagamento con fattura</h1>



<h2> Inserire le credenziali</h2>


<form action="FatturaServlet" method="post">
<table>
<caption>dettagli per fattura</caption>
<tr>
<th scope="col">
</th>
</tr>
<tr>
<td>
inserire il nome :
</td>
<td>
<input type="text" id="nomeL" name="nomeL" value="${beanUB.getNomeB()}">
</td>
</tr>
<tr>
<td>
inserire il cognome :
</td>
<td>
<input type="text" id="cognomeL" name="cognomeL" value="${beanUB.getCognomeB()}">
</td>
</tr>
<tr>
<td>
inserire indirizzo  :
</td>
<td>
<input type="text" id="indirizzoL" name="indirizzoL">
</td>
</tr>
<tr>
<td>
inserire eventuali comunicazioni :
</td>
<td>
<textarea id="com" name="com" rows="20" cols="50" class="textareaProp">
</textarea>
</td>
</tr>
<tr>
<td>
<input type="submit" id="buttonC" name="buttonC" value="procedi" class="invia">
</td>
<td>
<input type="submit" class="indietro" id="annulla" name="annulla" value="annulla" >
</td>
</tr>
</table>
</form>



<div>
${beanF.getMexB() }
${aB.getTitoloB() }
</div>
</body>
</html>