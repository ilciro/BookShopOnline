package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.database.csvOggetto.CsvOggettoDao;
import laptop.exception.IdException;

import laptop.model.raccolta.Libro;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGestioneLibroCsv {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerScelta cS=new ControllerScelta();
    private final ControllerGestionePage cGP=new ControllerGestionePage();
    private final ControllerAggiungiPage cAP=new ControllerAggiungiPage();
    private static final ResourceBundle RBLIBRO=ResourceBundle.getBundle("configurations/books");
    private final ControllerModifPage cMP=new ControllerModifPage();
    private final CsvOggettoDao csv=new CsvOggettoDao();
    public TestGestioneLibroCsv() throws IOException {
    }
    /*
    @Test

    void testGetListaLibro() throws CsvValidationException, IOException, IdException {
        cS.getTypeDb("file");
        vis.setTypeAsBook();
        assertNotNull(cGP.getLista(vis.getType()));

    }

     */
    @Test
    void InsertLibro() throws CsvValidationException, IOException, IdException {
        Files.copy(Path.of("src/main/resources/csvfiles/libro.csv"), Path.of("report/reportLibro.csv"),REPLACE_EXISTING);
        cS.getTypeDb("file");
        vis.setTypeAsBook();
        Libro l=new Libro();
        //data for inserted book
        /*
        String []info=new String[6];
        String []infoGen=new String[6];
        info[0]=RBLIBRO.getString("titolo");
        infoGen[0]=RBLIBRO.getString("numPag");
        infoGen[1]= RBLIBRO.getString("isbn");
        info[4]= RBLIBRO.getString("editore");
        info[2]= RBLIBRO.getString("autore");
        info[3]= RBLIBRO.getString("lingua");
        info[5]= RBLIBRO.getString("categoria");
        LocalDate data= LocalDate.of(2022,3,8);
        String recensione= RBLIBRO.getString("recensione");
        String descrizione= RBLIBRO.getString("descrizione");
        infoGen[3]= RBLIBRO.getString("disp");
        infoGen[4]=RBLIBRO.getString("prezzo");
        infoGen[5]= RBLIBRO.getString("nrCopie");
         */
        l.setTitolo(RBLIBRO.getString("titolo"));
        l.setNrPagine(Integer.parseInt(RBLIBRO.getString("numPag")));
        l.setCodIsbn(RBLIBRO.getString("isbn"));
        l.setEditore(RBLIBRO.getString("editore"));
        l.setAutore(RBLIBRO.getString("autore"));
        l.setLingua(RBLIBRO.getString("lingua"));
        l.setCategoria(RBLIBRO.getString("categoria"));
        l.setDataPubb(LocalDate.of(2022,3,8));
        l.setRecensione(RBLIBRO.getString("recensione"));
        l.setDesc(RBLIBRO.getString("descrizione"));
        l.setDisponibilita(Integer.parseInt(RBLIBRO.getString("disp")));
        l.setPrezzo(Float.parseFloat(RBLIBRO.getString("prezzo")));
        l.setNrCopie(Integer.parseInt(RBLIBRO.getString("nrCopie")));

        csv.inserisciLibro(l);

        // se metto qui funziona


    }


    @Test
    void RimuoviLibro() throws CsvValidationException, IOException {
        Libro l=new Libro();
        l.setId(20);
        csv.removeLibroById(l);

    }








    // non va
    //se li rifaccio girare si




}
