<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//ricerca.css">
<meta charset="UTF-8">
<title>ricerca page</title>
</head>
<body>

<h1> Benvenuto nella pagina per cercare oggetto </h1>

<h2> Scegliere in quale tipologia di oggetto cercare</h2>

<form action="RicercaServlet" method="post">
<div>
<input type="submit" id="buttonL" name="buttonL" value="libri" class="libro">
<input type="submit" id="buttonG" name="buttonG" value="giornali" class="giornale">
<input type="submit" id="buttonR" name="buttonR" value="riviste" class="rivista">
<p>
<input type="submit" id="buttonI" name="buttonI" value="indietro" class="annulla">
</p>

</div>
</form>

</body>
</html>