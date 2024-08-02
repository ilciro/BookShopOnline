package controller;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.database.csvOggetto.CsvOggettoDao;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestAcquistaCSVGiornale {
    private final ControllerScelta cScelta=new ControllerScelta();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerPagamentoCash cPcash=new ControllerPagamentoCash();
    private final CsvOggettoDao csv=new CsvOggettoDao();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerDownload cD=new ControllerDownload();
    private static final String LIBROP="src/main/resources/csvfiles/libro.csv";
    private static final String GIORNALEP="src/main/resources/csvfiles/giornale.csv";
    private static final String RIVISTAP="src/main/resources/csvfiles/rivista.csv";
    private static final String LIBROFINALE="report/reportLibro.csv";
    private static final String GIORNALEFINALE="report/reportGiornale.csv";
    private static final String RIVISTAFINALE="report/reportRivista.csv";
    private static final String UTENTEP="src/main/resources/csvfiles/utente.csv";
    private static final String UTENTEFINALE="report/reportUtente.csv";



    public TestAcquistaCSVGiornale() throws IOException {

    }

    @BeforeAll
    static void init() throws IOException {
        Files.copy(Path.of(LIBROP), Path.of(LIBROFINALE),REPLACE_EXISTING);
        Files.copy(Path.of(RIVISTAP), Path.of(RIVISTAFINALE),REPLACE_EXISTING);
        Files.copy(Path.of(GIORNALEP), Path.of(GIORNALEFINALE),REPLACE_EXISTING);
        Files.copy(Path.of(UTENTEP), Path.of(UTENTEFINALE),REPLACE_EXISTING);

    }
    @Test
    void testTotale1() throws CsvValidationException, SQLException, IOException, IdException, AcquistaException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(5);
        Giornale g=csv.retrieveAllGiornaleData(new File("report/reportGiornale.csv"),vis.getId(),"");
        assertNotEquals(0f,cA.totale1(vis.getType(),g.getTitolo(),g.getDisponibilita(),3));

    }
    @Test
    void testInserisciAmmontare() throws AcquistaException, CsvValidationException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(1);
        cA.inserisciAmmontare(vis.getType());
    }
    @Test
    void testGetNomeById() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(1);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void testGetCosto() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(1);
        assertNotEquals(0f,cA.getCosto(vis.getType()));
    }
    @Test
    void testGetDisp() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(1);
        assertNotEquals(0,cA.getDisp(vis.getType()));

    }
    @Test
    void testControlla() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(1);
        vis.setSpesaT(1.99f);
        vis.setMetodoP("cash");
        cPcash.controlla("franco","rossi","via blu 15"," dopo le 15");
    }
    @Test
    void testDownload() throws DocumentException, IOException, URISyntaxException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setId(1);
        cD.scarica(vis.getType());
    }



}
