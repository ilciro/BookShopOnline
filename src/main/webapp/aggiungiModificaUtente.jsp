<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

 <!DOCTYPE html>
 <html lang=it>
 <head>
 <link rel="stylesheet" href="css//aggiungiModificaUtente.css">
 <meta charset="UTF-8">
 <title>modif object page</title>
 </head>
 <body>

 <c:set var = "tipo" scope = "session" value = "${beanS.getTypeOfModif() }"/>

 <form action="AggiungiModificaUtenteServlet" method="post">

 <c:choose>
 <c:when test="${ tipo=='inserisci'}">
 <h1> Benvenuto nella schermata per aggiungere utente </h1>
 <br>
 <br>
 <h2> Possibili ruoli : A,W,S,U,E </h2>
 <div class="column">
 <input type="text" id="ruolo" name="ruolo" value="ruolo"/>
 <br>
 <br>
  <input type="text" id="nome" name="nome" value="nome"/>
  <br>
  <br>
   <input type="text" id="cognome" name="cognome" value="cognome"/>
   <br>
   <br>
    <input type="text" id="email" name="email" value="email"/>
    <br>
    <br>
     <input type="password" id="pass" name="pass" value="pass"</>
     <br>
     <br>
     <textarea rows="2" cols="50" id="desc" name="desc" value="desc">
     </textarea>
      <br>
      <br>
       <input type="date" id="data" name="data"/>
       <br>
       <br>
       ${tempUb.getMex()}
       <br>
      <br>
      <input type="submit" class="invia" id="inserisci" name="inserisci" value="inserisci"/>
      <input type="submit" class="indietro" id="indietro" name="indietro" value="indietro"/>

</div>
 </c:when>
 <c:when test="${ tipo=='modifica'}">
  <h1> Benvenuto nella schermata per modificare utente </h1>

   <br>
   <br>
   <h2> Possibili ruoli : A,W,S,U,E </h2>
   <br>
<div class="column">
ruolo :
  <input type="text" id="ruolo" name="ruolo" value="${tempUb.getRuoloB()}"/>
   <br>
   <br>
   nome:
    <input type="text" id="nome" name="nome" value="${tempUb.getNomeB()}"/>
    <br>
    <br>
    cognome:
     <input type="text" id="cognome" name="cognome" value="${tempUb.getCognomeB()}"/>
     <br>
     <br>
     email:
      <input type="text" id="email" name="email" value="${tempUb.getEmailB()}"/>
      <br>
      <br>
      password:
       <input type="password" id="pass" name="pass" value="${tempUb.getPassB()}"/>
       <br>
       <br>
       descrizione:
       <textarea rows="2" cols="50" id="desc" name="desc">${tempUb.getDescrizioneB()}</textarea>
        <br>
        <br>
        data:
         <input type="text" id="data" name="data" value="${tempUb.getDataDiNascitaB()}"/>
         <br>
         <br>
         </div>

         <div class="column1">
         ruolo da modificare :
           <input type="text" id="ruoloN" name="ruoloN" value="${tempUb.getRuoloB()}"/>
            <br>
            <br>
            nome da modificare:
             <input type="text" id="nomeN" name="nomeN" value="${tempUb.getNomeB()}"/>
             <br>
             <br>
             cognome da modificare:
              <input type="text" id="cognomeN" name="cognomeN" value="${tempUb.getCognomeB()}"/>
              <br>
              <br>
              email da modificare:
               <input type="text" id="emailN" name="emailN" value="${tempUb.getEmailB()}"/>
               <br>
               <br>
               password da modificare:
                <input type="password" id="passN" name="passN" value="${tempUb.getPassB()}"/>
                <br>
                <br>
                descrizione da modificare:
                <textarea rows="2" cols="50" id="descN" name="descN" value="descrizione"></textarea>
                 <br>
                 <br>
                 data da modificare:
                  <input type="date" id="dataN" name="dataN" />
                  <br>
                  <br>
                  </div>
                  <br>
                  <br>
                    <br>
                    <br>

                  <input type="submit" id="modifica" name="modifica" class="modifica" value="modifica"/>
                  <input type="submit" id="indietro" name="indietro" class="indietro" value="indietro"/>


 </c:when>
 </c:choose>
 </form>

 </body>
 </html>