 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<!DOCTYPE html>
<html lang=en-it>
<head>
<meta charset="ISO-8859-1">
<title>insert new object page</title>
</head>
<link rel="stylesheet" href="css//aggiungiOggetto.css">

<body>
<h1>Inserire un nuovo ${beanS.getTypeB()}</h1>

<h2> compilare il form</h2>

<form action ="InserisciOggettoServlet" method="post">

<c:set var="tipo" scope="session" value="${beanS.getTypeB()}"/>

<c:choose>
<c:when test="${tipo=='libro'}">
<div>
titolo :
<input type="text" id="titoloL" name="titoloL"/>
<br>
<br>
numero pagine :
<input type="text" id="nrPagL" name="nrPagL"/>
<br>
<br>
codice isbn :
<input type="text" id="codL" name="codL"/>
<br>
<br>
autore :
<input type="text" id="autoreL" name="autoreL"/>
<br>
<br>
editore :
<input type="text" id="editoreL" name="editoreL"/>
<br>
<br>
lingua :
<input type="text" id="linguaL" name="linguaL"/>
<br>
<br>
<p>
categoria
<textarea  cols=25 rows=5 id="catL" name="catL" readonly/>
ADOLESCENTI_RAGAZZI
ARTE
CINEMA_FOTOGRAFIA
BIOGRAFIE
DIARI_MEMORIE
CALENDARI_AGENDE
DIRITTO
DIZINARI_OPERE
ECONOMIA
FAMIGLIA
SALUTE_BENESSERE
FANTASCIENZA_FANTASY
FUMETTI_MANGA
GIALLI_THRILLER
COMPUTER_GIOCHI
HUMOR
INFORMATICA
WEB_DIGITAL_MEDIA
LETTERATURA_NARRATIVA
LIBRI_BAMBINI
LIBRI_SCOLASTICI
LIBRI_UNIVERSITARI
RICETTARI_GENERALI
LINGUISTICA_SCRITTURA
POLITICA
RELIGIONE
ROMANZI_ROSA
SCIENZE
TECNOLOGIA_MEDICINA
NON_VALIDO
</textarea>

<input type="text" id="catS" name="catS"/>
</p>
<br>
<br>
data pubb (yyyy/mm/dd) :
<input type="text" id="dataL" name="dataL"/>
<br>
<br>
recensione :
<input type="text" id="recensioneL" name="recensioneL"/>
<br>
<br>
descrizione :
<input type="text" id="descL" name="descL"/>
<br>
<br>
disponibilita (flaggare per renderlo disponibile) :
<input type="checkbox" id="checkL" name="checkL"/>
<br>
<br>
prezzo :
<input type="text" id="prezzoL" name="prezzoL"/>
<br>
<br>
copie rimanenti :
<input type="text" id="copieL" name="copieL"/>
<br>
<br>
</div>
</c:when>

<c:when test="${tipo=='giornale'}">
<div>
titolo :
<input type="text" id="titoloG" name="titoloG"/>
<br>
<br>
tipologia :
<input type="text" id="tipoG" name="tipoG" value="QUOTIDIANO"/>
<br>
<br>
lingua :
<input type="text" id="linguaG" name="linguaG"/>
<br>
<br>
editore :
<input type="text" id="editoreG" name="editoreG"/>
<br>
<br>
data pubb (yyyy/mm/dd) :
<input type="text" id="dataG" name="dataG"/>
<br>
<br>
<br>
copie rimanenti :
<input type="text" id="copieG" name="copieG"/>
<br>
<br>
disponibilita (0->no 1->si):
<input type="text" id="dispG" name="dispG"/>
<br>
<br>
prezzo:
<input type="text" id="prezzoG" name="prezzoG"/>
<br>
<br>
</div>
</c:when>

<c:when test="${tipo=='rivista'}">
<div>
titolo :
<input type="text" id="titoloL" name="titoloL"/>
<br>
<br>
tipologia :
<textarea  cols=25 rows=5 id="catR" name="catR" disabled/>
SETTIMANALE
BISETTIMANALE
MENSILE
BIMESTRALE
TRIMESTRALE
ANNUALE
ESTIVO
INVERNALE
SPORTIVO
CINEMATOGRAFICA
GOSSIP
TELEVISIVO
MILITARE
INFORMATICA
NON_VALIDO
</textarea>

<input type="text" id="catS" name="catS"/>
<br>
<br>
autore :
<input type="text" id="autL" name="autL"/>
<br>
<br>
lingua :
<input type="text" id="linguaL" name="linguaL"/>
<br>
<br>
editore :
<input type="text" id="editoreL" name="editoreL"/>
<br>
<br>
descrizione :
<input type="text" id="descL" name="descL"/>
<br>
<br>
data pubb (yyyy/mm/dd) :
<input type="text" id="dataL" name="dataL"/>
<br>
<br>
disponibilita (flaggare per renderlo disponibile) :
<input type="checkbox" id="checkL" name="checkL"/>
<br>
<br>
prezzo :
<input type="text" id="prezzoL" name="prezzoL"/>
<br>
<br>
copie rimanenti :
<input type="text" id="copieL" name="copieL"/>
<br>
<br>
</div>
</c:when>
</c:choose>

<input type="submit" id="confermaB" name="confermaB" value="conferma" class="invia">
<input type="submit" id="annullaB" name="annullaB" value="indietro" class="annulla">


</div>
</form>

</body>
</html>