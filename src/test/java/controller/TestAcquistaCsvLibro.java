package controller;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.database.csvOggetto.CsvOggettoDao;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;

import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAcquistaCsvLibro {
    private final ControllerScelta cScelta=new ControllerScelta();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerPagamentoCash cPcash=new ControllerPagamentoCash();
    private final CsvOggettoDao csv=new CsvOggettoDao();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerDownload cD=new ControllerDownload();
    public TestAcquistaCsvLibro() throws IOException {
    }



    @Test
    void testTotale1() throws CsvValidationException, SQLException, IOException, IdException, AcquistaException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        Libro l=csv.retrieveAllLibroData(new File("report/reportLibro.csv"),vis.getId(),"");
        assertNotEquals(1f,cA.totale1(vis.getType(),l.getTitolo(),l.getDisponibilita(),5));

    }
    @Test
    void testInserisciAmmontare() throws AcquistaException, CsvValidationException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        cA.inserisciAmmontare(vis.getType());
    }
    @Test
    void testGetNomeById() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void testGetCosto() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        assertNotEquals(0f,cA.getCosto(vis.getType()));
    }
    @Test
    void testGetDisp() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        assertNotEquals(0,cA.getDisp(vis.getType()));

    }
    @Test
    void testControlla() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        vis.setSpesaT(7.99f);
        vis.setMetodoP("cash");
        cPcash.controlla("marco","arancioni","via blu 15"," dopo le 15");
    }
    @Test
    void testDownload() throws DocumentException, IOException, URISyntaxException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setId(6);
        cD.scarica(vis.getType());
    }





}
