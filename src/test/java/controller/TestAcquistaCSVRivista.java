package controller;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.database.csvOggetto.CsvOggettoDao;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestAcquistaCSVRivista {
    private final ControllerScelta cScelta=new ControllerScelta();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerPagamentoCC cPcc=new ControllerPagamentoCC();
    private final CsvOggettoDao csv=new CsvOggettoDao();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerDownload cD=new ControllerDownload();

    public TestAcquistaCSVRivista() throws IOException {
    }
    @Test
    void testTotale1() throws CsvValidationException, SQLException, IOException, IdException, AcquistaException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(1);
        Rivista r=csv.retrieveAllRivistaData(new File("report/reportRivista.csv"),vis.getId(),"","");
        assertNotEquals(0f,cA.totale1(vis.getType(),r.getTitolo(),r.getDisp(),5));

    }
    @Test
    void testInserisciAmmontare() throws AcquistaException, CsvValidationException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(1);
        cA.inserisciAmmontare(vis.getType());
    }
    @Test
    void testGetNomeById() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(1);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void testGetCosto() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(1);
        assertNotEquals(0f,cA.getCosto(vis.getType()));
    }
    @Test
    void testGetDisp() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(1);
        assertNotEquals(0f,cA.getDisp(vis.getType()));

    }
    @Test
    void testControlla() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(5);
        vis.setSpesaT(5.99f);
        vis.setMetodoP("cCredito");
        cPcc.controllaPag("2028/08/08","1552-9666-7485-1422","855");
        cPcc.pagamentoCC("luca");
    }
    @Test
    void testDownload() throws DocumentException, IOException, URISyntaxException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setId(1);
        cD.scarica(vis.getType());
    }

}


