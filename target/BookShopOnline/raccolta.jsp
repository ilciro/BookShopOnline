<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//raccolta.css">
<meta charset="UTF-8">
<title>raccolta Page</title>
</head>
<body>
<h1> Benevenuto nella raccolta page</h1>

<h2>Scegliere tipologia da modificare</h2>

<form action="RaccoltaServlet" method="post">
<div>
<input type="submit" name="buttonL" id="buttonL" value="libri" class="libro">
<input type="submit" name="buttonG" id="buttonG" value="giornali" class="giornale">
<input type="submit" name="buttonR" id="buttonR" value="riviste" class="rivista">
<input type="submit" name="buttonLog" id="buttonLog" value="logout" class="annulla">
</div>
</form>

</body>
</html>