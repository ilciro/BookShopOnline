<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//login.css">

<meta charset="UTF-8">
<title>login page</title>
</head>
<body>


<form action="LoginServlet" method="post">



<div class="container">
        <!-- Sidebar -->
        <div class="sidebar">


            <a href="registra.jsp" id="registra">Registra</a>
            <a href="index.jsp" id="index">Indietro</a>

        </div>
         <div class="content">
            <div id="home">
            <h1> Benvenuto nella pagina di login</h1>

            <h2> Accedere con le credenziali oppure registrarsi</h2>
            <br>
            inserire email :
            <input type="text"  id="emailL" name="emailL" />
            <br>
            <br>
            inserire password:
            <input type="password" id="passL" name="passL" />
            <br>
            <br>
            <input type="submit" class="login" name="login" id="login" value ="login"></input>
            <br>
            <br>
            <input type="submit" class="reset" name="reset" id="reset" value ="reset"></input>

            <p class="exception">${beanUb.getMexB()}</p>

        </div>



</form>
</body>
</html>