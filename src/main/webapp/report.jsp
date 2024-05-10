<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=en-it>
<head>
<meta charset="ISO-8859-1">
<title>report page</title>
</head>
<link href="css//report.css" rel="stylesheet" type="text/css">
<body>

<h1> Scegliere una tra le seguenti checkbox</h1>

<form action="ReportServlet" method="post">


<div>


<input type="submit" id="buttonT" name="buttonT" value="totale" class="totale">
<input type="submit" id="buttonL" name="buttonL" value="libro" class="libri">
<input type="submit" id="buttonG" name="buttonG" value="giornale" class="giornali">
<input type="submit" id="buttonR" name="buttonR" value="rivista" class="riviste">
<input type="submit" id="raccoltaB" name="raccoltaB" value="raccolta" class="raccolta">
<input type="submit" id="buttonI" name="buttonI" value="indietro" class="annulla">



<br>
<br>

<label for="tArea"></label>
<textarea rows="100" cols="145" name="tArea" id="tArea" placeholder="tArea" class="tArea">
${beanTA.getScriviB()}
</textarea>

</div>


</form>
</body>
</html>