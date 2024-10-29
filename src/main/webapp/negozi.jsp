<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//negozio.css">

<meta charset="UTF-8">
<title>Negozi Page</title>
</head>
<body>
<h1> Benvenuto nella schermata della scelta dei negozi</h1>

<h2> Cliccare sul pulsante del negozio quale si vuole effettuare il ritiro</h2>

<form action="NegozioServlet" method="post">
<table>
<caption>
elenco negozi presenti
</caption>
<tr>
<th scope="col"></th>
</tr>
<tr>
<td>
<img alt="source not found" src="immagini/negozio.png" width=150  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/negozio.png" width=150  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/negozio.png" width=150  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/negozio.png" width=150  height=100 >
</td>
</tr>
<tr>
<td>
<input type="submit" id="buttonNeg1" name="buttonNeg1" value="Negozio A">
</td>
<td>
<input type="submit" id="buttonNeg2" name="buttonNeg2" value="Negozio B">
</td>
<td>
<input type="submit" id="buttonNeg3" name="buttonNeg3" value="Negozio C">
</td>
<td>
<input type="submit" id="buttonNeg4" name="buttonNeg4" value="Negozio D">
</td>

</table>

<div>
${beanNeg.getMexB0() }
</div>

</form>
</body>
</html>