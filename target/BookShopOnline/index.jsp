<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<meta charset="UTF-8">
<title>Index page</title>
<link rel="stylesheet" href="css//homePage.css">
</head>
<body>



<h1>  Benvenuti nella Libreria </h1>

<h2>
Scegliere quale oggetto si desidera
</h2>



<form action="IndexServlet" method="post">
<table>
<caption>
scegliere tra libri , giornali e riviste
</caption>
<tr>
<th scope="col">
libro
</th>
<th>
giornale
</th>
<th>
rivista
</th>
<th>
login
</th>
<th>
ricerca
</th>
</tr>
<tr>
<td>
<img alt="source not found" src="immagini/libro.png_860.png" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/newspaper-284-542534.png" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/una-rivista_318-1607.jpg" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/vector-users-icon-png_302626.jpg" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/Search-icon-13.png" width=100  height=100 >
</td>
</tr>
<tr>
<td>
<input type="submit" name="buttonL" id="buttonL" value="libri" class="libri">
</td>
<td>
<input type="submit" name="buttonG" id="buttonG" value="giornali" class="giornali">
</td>
<td>
<input type="submit" name="buttonR" id="buttonR" value="riviste" class="riviste">
</td>
<td>
<input type="submit" name="buttonLogin" id="buttonLogin" value="login" class="login">
</td>
<td>
<input type="submit" name="buttonRic" id="buttonRic" value="ricerca" class="ricerca">
</td>


</tr>
</table>




</form>

</body>
</html>