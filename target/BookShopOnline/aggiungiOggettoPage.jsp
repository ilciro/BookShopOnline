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


<p> id oggetto </p>
<p>libro bean : ${beanL.getIdB()}</p>
<p> sysbean :${beanS.getIdB()}</p>
<p> type: ${beanS.getTypeB() }</p>



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
<label>
<input type="text" id="codL" name="codL"/>
</label>
<br>
<br>
autore :
<label>
<input type="text" id="autoreL" name="autoreL"/>
</label>
<br>
<br>
editore :
<label>
<input type="text" id="editoreL" name="editoreL"/>
</label>
<br>
<br>
lingua :
<label>
<input type="text" id="linguaL" name="linguaL"/>
</label>
<br>
<br>
<p>
categoria
<label>
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
</label>

<label>
<input type="text" id="catS" name="catS"/>
</label>
</p>
<br>
<br>
data pubb (yyyy/mm/dd) :
<label>
<input type="text" id="dataL" name="dataL"/>
</label>
<br>
<br>
recensione :
<label>
<input type="text" id="recensioneL" name="recensioneL"/>
</label>
<br>
<br>
descrizione :
<label>
<input type="text" id="descL" name="descL"/>
</label>
<br>
<br>
disponibilita (flaggare per renderlo disponibile) :
<label>
<input type="checkbox" id="checkL" name="checkL"/>
</label>
<br>
<br>
prezzo :
<label>
<input type="text" id="prezzoL" name="prezzoL"/>
</label>
<br>
<br>
copie rimanenti :
<label>
<input type="text" id="copieL" name="copieL"/>
</label>
<br>
<br>
</div>
</c:when>

<c:when test="${tipo=='giornale'}">
<div>
titolo :
<label>
<input type="text" id="titoloG" name="titoloG"/>
</label>
<br>
<br>
tipologia :
<label>
<input type="text" id="tipoG" name="tipoG" value="QUOTIDIANO"/>
</label>
<br>
<br>
lingua :
<label>
<input type="text" id="linguaG" name="linguaG"/>
</label>
<br>
<br>
editore :
<label>
<input type="text" id="editoreG" name="editoreG"/>
</label>
<br>
<br>
data pubb (yyyy/mm/dd) :
<label>
<input type="text" id="dataG" name="dataG"/>
</label>
<br>
<br>
<br>
copie rimanenti :
<label>
<input type="text" id="copieG" name="copieG"/>
</label>
<br>
<br>
disponibilita (0->no 1->si):
<label>
<input type="text" id="dispG" name="dispG"/>
</label>
<br>
<br>
prezzo:
<label>
<input type="text" id="prezzoG" name="prezzoG"/>
</label>
<br>
<br>
</div>
</c:when>

<c:when test="${tipo=='rivista'}">
<div>
titolo :
<label>
<input type="text" id="titoloL" name="titoloL"/>
</label>
<br>
<br>
tipologia :
<label>
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
</label>

<label>
<input type="text" id="catS" name="catS"/>
</label>
<br>
<br>
autore :
<label>
<input type="text" id="autL" name="autL"/>
</label>
<br>
<br>
lingua :
<label>
<input type="text" id="linguaL" name="linguaL"/>
</label>
<br>
<br>
editore :
<label>
<input type="text" id="editoreL" name="editoreL"/>
</label>
<br>
<br>
descrizione :
<label>
<input type="text" id="descL" name="descL"/>
</label>
<br>
<br>
data pubb (yyyy/mm/dd) :
<label>
<input type="text" id="dataL" name="dataL"/>
</label>
<br>
<br>
disponibilita (flaggare per renderlo disponibile) :
<label>
<input type="checkbox" id="checkL" name="checkL"/>
</label>
<br>
<br>
prezzo :
<label>
<input type="text" id="prezzoL" name="prezzoL"/>
</label>
<br>
<br>
copie rimanenti :
<label>
<input type="text" id="copieL" name="copieL"/>
</label>
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