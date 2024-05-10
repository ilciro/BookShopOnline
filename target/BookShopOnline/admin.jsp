<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<link rel="stylesheet"  href="css/admin.css">
<head>
<meta charset="UTF-8">
<title>admin page</title>
</head>
<body>
<h1> Benvenuto nell'admin page</h1>

<h2>Scegliere cosa fare</h2>

<p> parametri:</p>
<p> email : ${beanUb.getEmailB()}</p>
<p> ruolo : ${beanUb.getRuoloB()}</p>
<p> pass: ${beanUb.getPassB()}</p>



<form action="AdminServlet" method="post">
<div>
<input type="submit" id="reportB" name="reportB" value="report" class="report">
<input type="submit" id="raccoltaB" name="raccoltaB" value="raccolta" class="raccolta">
<input type="submit" id="utentiB" name="utentiB" value="utenti" class="utenti">
<input type="submit" id="logoutB" name="logoutB" value="logout" class="annulla">

</div>
</form>



</body>
</html>