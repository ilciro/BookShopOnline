<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//admin.css">




<meta charset="UTF-8">
<title>admin page</title>
</head>
<body>

<form action="AdminServlet" method="post">
<div class="container">
        <!-- Sidebar -->
        <div class="sidebar">

            <a href="report.jsp" id="report" >Report</a>
            <a href="gestioneUtenti.jsp" id="gUtenti">Gestione utenti</a>
            <a href="gestioneOggetti.jsp" id="gOggetti">Gestione oggetti</a>
            <a href="ricerca.jsp?role=A" id="ricerca">Cerca</a>




        </div>

        <!-- Main Content -->
        <div class="content">
            <div id="home">
                <h1>Benvenuto nella page di admin </h1>
                <h2>
                   Qui &#232; possibile generare il report conseguito dalle vendite.
                   Inoltre &#232; possibile accedere alla gestione degli utenti
                   (inserire/modificare/eliminare). Analogamente per gli oggetti.
                   Inoltre &#232; possibile effettuale la ricerca di oggetti
                </h2>
                <br>


            </div>

        </div>

    </div>
    <div style="text-align:center">
    <input type="submit" name="logout" id="logout" class="logout" value="logout"/>
    </div>

</form>



</body>
</html>