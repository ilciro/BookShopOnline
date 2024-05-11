<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=it>
<head>
<link rel="stylesheet" href="css//modificaUtente.css">
<meta charset="UTF-8">
<title>modif user page</title>
</head>
<body>

<h1>Benvenuto nella schermata per modificare utente</h1>

<h2>Scegliere quali campi modificare</h2>


<p> ruolo : ${beanUb.getRuoloB()}</p>
<p>  nome: ${beanUb.getNomeB()} </p>
<p>  cognome: ${beanUb.getCognomeB()}</p>
<p>    email: ${beanUb.getEmailB()} </p>
<p>     pass: ${beanUb.getPassB()}  </p>
<p>     desc: ${beanUb.getDescrizioneB()}  </p>

<p> id dal bean modifica: ${beanUb.getIdB()}</p>

<form action="ModificaUtenteServlet" method="post">
<div class="column">
Ruolo :
<input type="text" id="ruoloL" name="ruoloL" value="${beanUb.getRuoloB() }">
<br>
<br>
Nome:
<input type="text" id="nomeL" name="nomeL" value="${beanUb.getNomeB() }">
<br>
<br>
Cognome:
<input type="text" id="cognomeL" name="cognomeL" value="${beanUb.getCognomeB() }">
<br>
<br>
Email:
<input type="text" id="emailL"  name="emailL" value="${beanUb.getEmailB() }">
<br>
<br>
Password:
<input type="text" id="passL" name="passL" value="${beanUb.getPassB() }">
<br>
<br>
Descrizione :
<input type="text" id="descL" name="descL" value="${beanUb.getDescrizioneB() }">
<br>
<br>
Data di nascita:
<input type="text" id="dataL" name="dataL"value="${beanUb.getDataDiNascitaB() }">
<br>
<br>
<input type="submit" id="generaB" name="generaB" value="prendi valori" class="prendi">
</div>
<div class="column">
<!--  textarea 4 valori e basta -->
<label for="tipiU"> Scegliere "quelle in maiuscolo"</label>
<textarea rows="4" cols="20">
U-->u/utente
A-->a/admin
W-->w/scrittore
E-->e/editore
</textarea>
</div>
<div class="column">
Ruolo da modificare:
<input type="text" id="ruoloNL" name="ruoloNL" >
<br>
<br>
Nome da modificare:
<input type="text" id="nomeNL" name="nomeNL" >
<br>
<br>
Cognome da modificare:
<input type="text" id="cognomeNL" name="cognomeNL" >
<br>
<br>
Email da modificare:
<input type="text" id="emailNL"  name="emailNL" >
<br>
<br>
Password da modificare:
<input type="password" id="passNL" name="passNL" >
<br>
<br>
Descrizione da modificare :
<input type="text" id="descNL" name="descNL" >
<br>
<br>
Data di nascita (yyyy/mm/dd):
<input type="text" id="dataNL" name="dataNL" value="${beanUb.getDataDiNascitaB() }">
<br>
<br>
<input type="submit" id="buttonI" name="buttonI" value="aggiorna" class="invia">
<input type="submit" id="buttonA" name="buttonA" value="indietro" class="annulla">
</div>

<p class="exception">
${beanUb.getMexB() }
</p>
</form>
</body>
</html>