<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

 <!DOCTYPE html>
 <html lang=it>
 <head>
 <link rel="stylesheet" href="css//aggiungiModificaOggetto.css">
 <meta charset="UTF-8">
 <title>modif object page</title>
 </head>
 <body>

 <c:set var = "tipo" scope = "session" value = "${beanS.getTypeOfModif() }"/>
 <c:set var = "oggetto" scope = "session" value = "${beanS.getTypeB() }"/>

 <form action="AggiungiModificaOggettoServlet" method="post">



 <c:choose>
 <c:when test="${ tipo=='inserisci'}">
    <!-- vedo il tipo di oggetto -->
    <div class="column">
    <c:choose>
        <c:when test="${oggetto=='libro'}">
        <h1> Inserimento di un nuovo "${beanS.getTypeB()}"</h1>
        <br>
        <br>
        Titolo da inserire : <input type="text" id="titoloL" name="titoloL" value="inserisci titolo"/>
        <br>
        <br>
        Codice isbn da inserire : <input type="text" id="codiceL" name="codiceL" value="inserisci codiIsbn"/>
        <br>
        <br>
        Editore da inserire : <input type="text" id="editoreL" name="editoreL" value="inserisci editore"/>
        <br>
        <br>
        Autore da inserire : <input type="text" id="autoreL" name="autoreL" value="inserisci autore"/>
        <br>
        <br>
        Lingua da inserire : <input type="text" id="linguaL" name="linguaL" value="inserisci lingua"/>
        <br>
        <br>
        <p>
        Categorie Disponibili : <textarea cols="30" rows="5" id="categorie" name="categorie" >"${beanMob.getListaCategorieB() }"</textarea>
        <br>
        Categorie Da inserire : <textarea cols="30" rows="5" id="categorieI" name="categorieI"></textarea>
        </p>
        <br>
        Descrizione da inserire : <textarea cols="30" rows="10" id="desc" name="desc"></textarea>
        <br>
        <br>
        Data di pubblicazione : <input type="date" id="dataL"name="dataL"/>
        <br>
        <br>
        Recensione da inserire : <textarea cols="30" rows="10" id="rec" name="rec"></textarea>
        <br>
        <br>
        Numero pagine da inserire : <input type="text" id="pagineL" name="pagineL" value="inserisci pagine"/>
        <br>
        <br>
        Numero copie : <input type="text" id="copieL" name="copieL" value="inserisci copie"/>
        <br>
        <br>
        Disponibilit&agrave; 0-> no 1->si : <input type="text" id="dispL" name="dispL" value="inserisci disponibilit&agrave;"/>
        <br>
        <br>
        Prezzo da inserire : <input type="text" id="prezzoL" name="prezzoL" value="inserisci prezzo"/>
        <br>
        <br>
        <input type="submit" id="inserisci" name="inserisci" value="inserisci" class="invia"/>
        <input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>
        </c:when>
        <c:when test="${oggetto=='giornale'}">
        <h1> Inserimento di un nuovo "${beanS.getTypeB()}"</h1>
                <br>
                <br>
                Titolo da inserire : <input type="text" id="titoloG" name="titoloG" value="inserisci titolo"/>
                <br>
                <br>
                Editore da inserire : <input type="text" id="editoreG" name="editoreG" value="inserisci editore"/>
                <br>
                <br>
                Lingua da inserire : <input type="text" id="linguaG" name="linguaG" value="inserisci lingua"/>
                <br>
                <br>
                <p>
                Categorie Disponibili : <textarea cols="30" rows="10" id="categorie" name="categorie" value="QUOTIDIANO"></textarea>
                <br>
                Categorie Da inserire : <textarea cols="30" rows="10" id="categorieG" name="categorieG"></textarea>
                </p>
                <br>
                <br>
                Data di pubblicazione : <input type="date" id="dataG"name="dataG"/>
                <br>
                <br>
                Numero copie : <input type="text" id="copieG" name="copieG" value="inserisci copie"/>
                <br>
                <br>
                Disponibilit&agrave; 0-> no 1->si : <input type="text" id="dispG" name="dispG" value="inserisci disponibilit&agrave;"/>
                <br>
                <br>
                Prezzo da inserire : <input type="text" id="prezzoG" name="prezzoG" value="inserisci prezzo"/>
                <br>
                <br>
                <input type="submit" id="inserisci" name="inserisci" value="inserisci" class="invia"/>
                <input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>
        </c:when>
        <c:when test="${oggetto=='rivista'}">
        <h1> Inserimento di una nuova "${beanS.getTypeB()}"</h1>
                <br>
                <br>
                Titolo da inserire : <input type="text" id="titoloR" name="titoloR" value="inserisci titolo" />
                <br>
                <br>
                Editore da inserire : <input type="text" id="editoreR" name="editoreR" value="inserisci editore"/>
                <br>
                <br>
                Autore da inserire : <input type="text" id="autoreR" name="autoreR" value="inserisci autore"/>
                <br>
                <br>
                Lingua da inserire : <input type="text" id="linguaR" name="linguaR" value="inserisci lingua"/>
                <br>
                <p>
                Categorie Disponibili : <textarea cols="30" rows="10" id="categorie" name="categorie" >value="${beanMob.getListaCategorieB() }"</textarea>
                <br>
                Categorie Da inserire : <textarea cols="30" rows="10" id="categorieR" name="categorieR"></textarea>
                </p>
                <br>
                <br>
                Descrizione da inserire : <textarea cols="30" rows="10" id="descR" name="descR"></textarea>
                <br>
                <br>
                Data di pubblicazione : <input type="date" id="dataR"name="dataR"/>
                <br>
                <br>
                Numero copie : <input type="text" id="copieR" name="copieR" value="inserisci copie"/>
                <br>
                <br>
                Disponibilit&agrave; 0-> no 1->si : <input type="text" id="dispR" name="dispR" value="inserisci disponibilit&agrave;"/>
                <br>
                <br>
                Prezzo da inserire : <input type="text" id="prezzoR" name="prezzoR" value="inserisci prezzo"/>
                <br>
                <br>
                <input type="submit" id="inserisci" name="inserisci" value="inserisci" class="invia"/>
                <input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>

        </c:when>
    </c:choose>
    </div>
 </c:when>








 <c:when test="${ tipo=='modifica'}">
  <h1> Benvenuto nella schermata per modificare utente </h1>



                  <input type="submit" id="modifica" name="modifica" class="modifica" value="modifica"/>
                  <input type="submit" id="indietro" name="indietro" class="indietro" value="indietro"/>


 </c:when>
 </c:choose>
 </form>

 </body>
 </html>