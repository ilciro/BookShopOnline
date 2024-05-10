<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<input type="text" id="nomeT" name="nomeT" >
</td>
</tr>
<tr>
<td>
inserire il cognome :
</td>
<td>
<input type="text" id="cognomeT" name="cognomeT">
</td>
</tr>
<tr>
<td>
inserire indirizzo  :
</td>
<td>
<input type="text" id="indirizzoT" name="indirizzoT">
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


</body>
</html>