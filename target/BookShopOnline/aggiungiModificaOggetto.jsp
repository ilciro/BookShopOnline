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

                    <c:choose>
                        <c:when test="${oggetto=='libro'}">
                            <div class="column">
                                <h1> Inserimento di un nuovo ${beanS.getTypeB()}</h1>
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
                            </div>
                        </c:when>
                        <c:when test="${oggetto=='giornale'}">
                            <div class="column">
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
                                    Categorie Disponibili : <textarea cols="30" rows="10" id="categorie" name="categorie">value="${beanMob.getListaCategorieB() }"</textarea>
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
                            </div>
                        </c:when>
                        <c:when test="${oggetto=='rivista'}">
                            <div class="column">
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
                            </div>
                        </c:when>
                    </c:choose>
               </div>
            </c:when>
            <c:when test="${ tipo=='modifica'}">
                <!-- modifica -->

                    <c:choose>
                        <c:when test="${oggetto=='libro'}">
                         <h1> Modifica di un nuovo ${beanS.getTypeB()}</h1>
                         <h2> Compilare il form </h2>
                            <div class="column">
                            <br>
                            Titolo  : <input type="text" readonly id="titoloL" name="titoloL" value="${beanL.getTitoloB()}"/>
                            <br>
                            <br>
                            Codice  : <input type="text" readonly  id="codiceL" name="codiceL" value="${beanL.getCodIsbnB()}"/>
                            <br>
                            <br>
                            Editore  : <input type="text" readonly  id="editoreL" name="editoreL" value="${beanL.getEditoreB()}"/>
                            <br>
                            <br>
                            Autore : <input type="text" readonly id="autoreL" name="autoreL" value="${beanL.getAutoreB()}"/>
                            <br>
                            <br>
                            Lingua : <input type="text" readonly id="linguaL" name="linguaL" value="${beanL.getLinguaB()}"/>
                            <br>
                            <br>
                            <p>
                            Categoria : <input type="text" readonly id="catL" name="catL" value="${beanL.getCategoriaB() }"/>
                            <br>
                            <br>
                            Descrizione : <input type="text"  readonly id="descL" name="descL" value="${beanL.getDescB()}"/>
                            <br>
                            <br>
                            Data di pubblicazione : <input type="text" readonly id="dataL"name="dataL" value="${beanL.getDateB()}"/>
                            <br>
                            <br>
                            Recensione da inserire : <textarea readonly cols="30" rows="10" id="rec" name="rec"> ${beanL.getRecensioneB()}</textarea>
                            <br>
                            <br>
                            Numero pagine da inserire : <input type="text" readonly id="pagineL" name="pagineL" value="${beanL.getNumeroPagineB()}"/>
                            <br>
                            <br>
                            Numero copie : <input type="text" readonly id="copieL" name="copieL" value="${beanL.getNrCopieB()}"/>
                            <br>
                            <br>
                            Disponibilit&agrave; 0-> no 1->si : <input type="text" readonly id="dispL" name="dispL" value="${beanL.getDisponibilitaB()}"/>
                            <br>
                            <br>
                            Prezzo da inserire : <input type="text" readonly id="prezzoL" name="prezzoL" value="${beanL.getPrezzoB()}"/>
                            </div>
                            <div class="column1">
                                <br>
                                <br>
                                Titolo da modificare  : <input type="text" id="titoloN" name="titoloN" />
                                <br>
                                <br>
                                Codice da modificare (almeno 8 caratteri) : <input type="text" id="codiceN" name="codiceN" />
                                <br>
                                <br>
                                Editore da modificare : <input type="text" id="editoreN" name="editoreN" />
                                <br>
                                <br>
                                Autore da modificare: <input type="text" id="autoreN" name="autoreN"/>
                                <br>
                                <br>
                                Lingua da modificare: <input type="text" id="linguaN" name="linguaN" />
                                <br>
                                <br>
                                <p>
                                Categorie Disponibili : <textarea cols="30" rows="10" id="categorieD" name="categorieD">${beanMob.getListaCategorieB() }</textarea>
                                <br>
                                Categorie Da modificare : <textarea cols="30" rows="5" id="categorieN" name="categorieN"></textarea>
                                </p>
                                <br>
                                Descrizione da modificare: <textarea cols="30" rows="10" id="descN" name="descN" ></textarea>
                                <br>
                                <br>
                                Data di pubblicazione da modificare : <input type="date" id="dataN"name="dataN"/>
                                <br>
                                <br>
                                Recensione da modificare : <textarea cols="30" rows="10" id="recN" name="recN"></textarea>
                                <br>
                                <br>
                                Numero pagine da modificare : <input type="text" id="pagineN" name="pagineN" />
                                <br>
                                <br>
                                Numero copie da modificare: <input type="text" id="copieN" name="copieN"/>
                                <br>
                                <br>
                                Disponibilit&agrave; da modificare 0-> no 1->si : <input type="text" id="dispN" name="dispN"/>
                                <br>
                                <br>
                                Prezzo da modificare : <input type="text" id="prezzoN" name="prezzoN"/>
                                <br>
                                <br>
                            <input type="submit" id="modifica" name="modifica" value="modifica" class="modifica"/>
                            <input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>
                        </div>
                        </c:when>
                        <c:when test="${oggetto=='giornale'}">

                            <h1> Inserimento di un nuovo ${beanS.getTypeB()}</h1>
                            <h2> Compilare il form</h2>
                            <div class="column">
                                    <br>
                                    <br>
                                    Titolo : <input type="text" readonly id="titoloG" name="titoloG" value="${beanG.getTitoloB()}"/>
                                    <br>
                                    <br>
                                    Editore  : <input type="text" readonly id="editoreG" name="editoreG" value="${beanG.getEditoreB()}"/>
                                    <br>
                                    <br>
                                    Lingua  : <input type="text" readonly id="linguaG" name="linguaG" value="${beanG.getLinguaB()}"/>
                                    <br>
                                    <br>
                                    Categorie  : <input type="text" readonly id="categoriaG" name="categoriaG" >${beanG.getTipologiaB()}/>
                                    <br>
                                    <br>
                                    Data di pubblicazione : <input type="text" readonly id="dataG" name="dataG">${beanG.getDataB()}/>
                                    <br>
                                    <br>
                                    Numero copie : <input type="text" id="copieG" name="copieG" value="${beanG.getCopieRimanentiB()}"/>
                                    <br>
                                    <br>
                                    Disponibilit&agrave; : <input type="text" id="dispG" name="dispG" value="${beanG.getDisponibilitaB()}"/>
                                    <br>
                                    <br>
                                    Prezzo  : <input type="text" id="prezzoG" name="prezzoG" value="${beanG.getPrezzoB()}"/>
                                   </div>
                                    <div class="column1">
                                        <br>
                                        <br>
                                        Titolo da modificare : <input type="text"  id="titoloGN" name="titoloGN" value="titolo da modificare"/>
                                        <br>
                                        <br>
                                        Editore da modificare  : <input type="text" id="editoreGN" name="editoreGN" value="editore da modificare"/>
                                        <br>
                                        <br>
                                        Lingua da modificare : <input type="text" id="linguaGN" name="linguaGN" value="lingua da modificare"/>
                                        <br>
                                        <br>
                                        Categoria da modificare  : <input type="text" readonly id="categoriaGN" name="categoriaGN" value="${beanG.getTipologiaB()}"/>
                                        <br>
                                        <br>
                                        Data di pubblicazione da modificare: <input type="date"  id="dataGN" name="dataGN"/>
                                        <br>
                                        <br>
                                        Numero copie da modificare : <input type="text" id="copieGN" name="copieGN" value="copie da modificare"/>
                                        <br>
                                        <br>
                                        Disponibilit&agrave; da modificare 0-> no 1->si : <input type="text" id="dispGN" name="dispGN" value="disp da modificare"/>
                                        <br>
                                        <br>
                                        Prezzo da modificare  : <input type="text" id="prezzoGN" name="prezzoGN" value="prezzo da modificare"/>
                                        <br>
                                        <br>
                                        <input type="submit" id="modifica" name="modifica" value="modifica" class="modifica"/>
                                        <input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>


                                    </div>


                                     </c:when>
                        <c:when test="${oggetto=='rivista'}">
                            <h1> Inserimento di una nuova ${beanS.getTypeB()}</h1>
                            <h2> Compilare il form</h2>
                             <div class="column">
                                    <br>
                                    <br>
                                    Titolo  : <input type="text" readonly id="titoloR" name="titoloR" value="${beanR.getTitoloB()}"/>
                                    <br>
                                    <br>
                                    Editore  : <input type="text" readonly id="editoreR" name="editoreR" value="${beanR.getEditoreB()}"/>
                                    <br>
                                    <br>
                                    Autore : <input type="text" readonly id="autoreR" name="autoreR" value="${beanR.getAutoreB()}"/>
                                    <br>
                                    <br>
                                    Lingua : <input type="text" readonly id="linguaR" name="linguaR" value="${beanR.getLinguaB()}"/>
                                    <br>
                                    <p>
                                    Categoria : <input type="text" readonly id="catR" name="catR" value="${beanR.getTipologiaB()}"/>
                                    </p>
                                    <br>
                                    <br>
                                    Descrizione  : <input type="text" readonly id="descR" name="descR" value="${beanR.getDescrizioneB()}"/>
                                    <br>
                                    <br>
                                    Data di pubblicazione : <input type="text" readonly id="dataR" name="dataR" value="${beanR.getDataB()}"/>
                                    <br>
                                    <br>
                                    Numero copie : <input type="text" readonly id="copieR" name="copieR" value="${beanR.getCopieRimB()}"/>
                                    <br>
                                    <br>
                                    Disponibilit&agrave; : <input type="text" readonly id="dispR" name="dispR" value="${beanR.getDispB()}"/>
                                    <br>
                                    <br>
                                    Prezzo  : <input type="text" readonly id="prezzoR" name="prezzoR" value="${beanR.getPrezzoB()}"/>
                                    <br>
                                    <br>
                             </div>
                             <div class="column1">
                             <br>
                             <br>
                             Titolo da modificare : <input type="text" id="titoloRN" name="titoloRN" value="titolo da modificare"/>
                             <br>
                             <br>
                             Editore da modificare  : <input type="text" id="editoreRN" name="editoreRN" value="editore da modificare"/>
                             <br>
                             <br>
                             Autore da modificare : <input type="text" id="autoreRN" name="autoreRN" value="autore da modificare"/>
                             <br>
                             <br>
                             Lingua da modificare : <input type="text" id="linguaRN" name="linguaRN" value="lingua da modificare"/>
                             <br>
                             <p>
                             Categorie Disponibili : <textarea cols="30" rows="10" id="categorie" name="categorie" >${beanMob.getListaCategorieB()}</textarea>
                             <br>
                             Categorie da modificare : <textarea cols="30" rows="5" id="categorieRN" name="categorieRN" value="categoria da modificare"></textarea>
                             </p>
                             <br>
                             <br>
                             Descrizione da modificare : <textarea cols="30" rows="10" id="descRN" name="descRN" value="descrizione da modificare"></textarea>
                             <br>
                             <br>
                             Data di pubblicazione da modificare: <input type="date" id="dataRN"name="dataRN"/>
                             <br>
                             <br>
                             Numero copie da modificare: <input type="text" id="copieRN" name="copieRN" value="copie da modificare"/>
                             <br>
                             <br>
                             Disponibilit&agrave; 0-> no 1->si : <input type="text" id="dispRN" name="dispRN" value="disponibilit&agrave; da modificare"/>
                             <br>
                             <br>
                             Prezzo da modificare : <input type="text" id="prezzoRN" name="prezzoRN" value="prezzo da modificare"/>
                             <br>
                             <br>
                             </div>
                                    <input type="submit" id="modifica" name="modifica" value="modifica" class="modifica"/>
                                    <input type="submit" id="indietro" name="indietro" value="indietro" class="indietro"/>

                        </c:when>
                    </c:choose>


            </c:when>

     </c:choose>


</form>

 </body>
 </html>