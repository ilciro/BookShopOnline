<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//scrittoreEditore.css">




<meta charset="UTF-8">
<title>scrittoreEditore page</title>
</head>
<body>

<form action="ScrittoreEditoreServlet" method="post">
<div class="container">
        <!-- Sidebar -->
        <div class="sidebar">

            <a href="libri.jsp" id="libri" >Libri</a>
            <a href="giornali.jsp" id="giornali">Giornali</a>
            <a href="riviste.jsp" id="riviste">Riviste</a>





        </div>

        <!-- Main Content -->
        <div class="content">
            <div id="home">
                <h1>Benvenuto nella page di scrittore/editore </h1>
                <h2>
                   Qui &#232; possibile acquistare oggetto.
                   Inoltre &#232; possibile cercare nel catalogo.
                   Inoltre &#232; possibile effettuale la gestione degli ordini
                   Ed infine &#232; possibile consultare il profilo
                </h2>
                <br>


            </div>

        </div>

    </div>

<div>
<input type="submit" id="cerca" name="cerca" value="cerca" class="cerca"/>
<input type="submit" id="ordini" name="ordini" value="ordini" class="ordini"/>
<input type="submit" id="gestione" name="gestione" value="gestione" class="gestione"/>
<input type="submit" id="logout" name="logout" value="logout" class="logout"/>

</div>



</form>
</body>
</html>