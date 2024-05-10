<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css/scrittore.css">
<meta charset="UTF-8">
<title>scrittore page</title>
</head>
<body>

<h1> Benvenuto!! Loggato come editore</h1>

<h2> Scegli cosa vuoi fare </h2>

<form action="ServletEditore" method="post">
<div>
<table>
<caption>
scegliere tra libri , giornali e riviste, gestione,cerca,logout e visualizza profilo con
privilegi di editore
</caption>
<tr>

<th scope="col">
libro editore
</th>
<th>
giornale editore
</th>
<th>
rivista editore
</th>
<th>
gestione editore
</th>
<th>
ricerca editore
</th>
<th>
logout come editore
</th>
<th>
visualizza profilo editore
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
<td>
<img alt="source not found" src="immagini/icons8-logout-rounded-48.png" width=100  height=100 >
</td>
<td>
<img alt="source not found" src="immagini/Profile-PNG-High-Quality-Image-180x180.png" width=100  height=100>
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
<input type="submit" name="buttonGestione" id="buttonGestione" value="gestione" class="gestione">
</td>
<td>
<input type="submit" name="buttonRic" id="buttonRic" value="ricerca" class="ricerca">
</td>
<td>
<input type="submit" name="logoutB" id="logoutB" value="logout" class="logout">
</td>
<td>
<input type="submit" name="profiloB" id="profiloB" value="vai al profilo" class="profilo">
</td>
</tr>
</table>
</div>
</form>

</body>
</html>