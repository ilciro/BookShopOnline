<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang=it>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css//annulla.css">

<title>Annulla page</title>
</head>
<body>
<h1> Benvenuto nella schermata per annullare il pagamento</h1>
<br>
<br>

<form action="AnnullaServlet" method="post">
    <div class="annulla">

    <p>metodo : ${bean1.getMetodoPB()}</p>



    <c:choose>
       <c:when test="${bean1.getMetodoPB().equals('cash')}">
        <p> elenco fatture <p>
               <textarea readonly row="1" cols="100" id="taF" name="taF"> ${fBean.toString()} </textarea>
        <p> leggi fattura da cancellare</p>
              <input type="text" id="idF" name="idF"/>
               <br>
               <br>
               <p> elenco pagamento <p>
               <textarea row="1" cols="100" id="taP" name="taP"> ${pBean.toString()} </textarea>
               <p> leggi pagamento da cancellare</p>
               <input type="text" id="idP" name="idP"/>
                <br>
                <br>
                 <input type="submit"  class="genera" id="buttonG" name="buttonG" value="genera lista"/>
                 <input type="submit"  class="fattura" id="buttonF" name="buttonF" value="annulla fattura"/>
                 <input type="submit" class="pagamento" id="buttonP" name="buttonP" value="annulla pagamento"/>


        </c:when>
       <c:otherwise>

       <p> elenco pagamento <p>
       <textarea row="1" cols="65" id="taP" name="taP"> ${pBean.toString()} </textarea>
       <p> leggi pagamento da cancellare</p>
       <input type="text" id="idP" name="idP"/>
       <br>
       <br>
       <input type="submit"  class="genera" id="buttonG" name="buttonG" value="genera lista"/>
       <input type="submit" class="pagamento" id="buttonP" name="buttonP" value="annulla pagamento "/>


       </c:otherwise>
    </c:choose>


        <br>
        <br>




</form>
</body>
</hmtl>
