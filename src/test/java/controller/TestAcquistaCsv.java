package controller;

import com.itextpdf.xmp.XMPException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.database.csv.CsvOggettoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.utilities.ConnToDb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAcquistaCsv {
    private final ControllerScelta cScelta=new ControllerScelta();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerPagamentoCash cPcash=new ControllerPagamentoCash();
    private final ControllerPagamentoCC cPcc=new ControllerPagamentoCC();
    private static final String LIBROP="src/main/resources/csvfiles/libro.csv";
    private static final String GIORNALEP="src/main/resources/csvfiles/giornale.csv";
    private static final String RIVISTAP="src/main/resources/csvfiles/rivista.csv";
    private static final String LIBROFINALE="report/reportLibro.csv";
    private static final String GIORNALEFINALE="report/reportGiornale.csv";
    private static final String RIVISTAFINALE="report/reportRivista.csv";
    private final CsvOggettoDao csv=new CsvOggettoDao();
    private final FatturaPagamentoCCredito csvF=new FatturaPagamentoCCredito();
    private final ControllerCompravendita cComp=new ControllerCompravendita();
    private final Libro l=new Libro();
    private final Rivista r=new Rivista();
    private final Giornale g=new Giornale();
    public TestAcquistaCsv() throws IOException {
    }

    @BeforeAll
    static void init() throws IOException {
        ConnToDb.creaPopolaDb();
        Files.copy(Path.of(LIBROP), Path.of(LIBROFINALE),REPLACE_EXISTING);
        Files.copy(Path.of(RIVISTAP), Path.of(RIVISTAFINALE),REPLACE_EXISTING);
        Files.copy(Path.of(GIORNALEP), Path.of(GIORNALEFINALE),REPLACE_EXISTING);
    }
    @Test
    void testControllerCompravenditaGetListaL() throws CsvValidationException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        assertNotNull(cComp.getLista(vis.getType()));
    }
    @Test
    void testControllerCompravenditaGetListaG() throws CsvValidationException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsDaily();
        assertNotNull(cComp.getLista(vis.getType()));
    }
    @Test
    void testControllerCompravenditaGetListaR() throws CsvValidationException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        assertNotNull(cComp.getLista(vis.getType()));
    }
/*

    @Test
    void testInsertGiornale() throws CsvValidationException, IOException, IdException {

        cScelta.getTypeDb("file");

        g.setTitolo("dentalpet");
        g.setTipologia("QUOTIDIANO");
        g.setEditore("candioli");
        g.setLingua("italiano");


        g.setDataPubb(LocalDate.of(2024,3,5));
        g.setDisponibilita(1);
        g.setPrezzo(2.65f);
        g.setCopieRimanenti(100);

        csv.inserisciGiornale(g);



    }


 */
    @Test
    void testRimuoviGiornale() throws CsvValidationException, IOException {
        vis.setId(13);
        csv.eliminaOggetto(new File(GIORNALEFINALE));
    }






/*
    @Test
    void testPagamentoCashLibro() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setMetodoP("cash");
        vis.setSpesaT(25.66f);
        vis.setId(5);
        cPcash.controlla("pippo","pluto","cia paperopoli 5", "dopo le 17");
    }


    @Test
    void testPagamentoCcRivista() throws CsvValidationException, SQLException, IOException, IdException {
        cScelta.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setMetodoP("cCredito");
        vis.setId(3);

        cPcc.aggiungiCartaDB("franco","rossi","9596-5888-1111-0000", Date.valueOf(LocalDate.of(2028,8,8)),"962",25.6f);

          }

    @Test
    void testInsertLibro() throws  CsvValidationException, IOException {


        cScelta.getTypeDb("file");




        l.setTitolo("titolo modificato");
        l.setAutore("autore modificato");
        l.setLingua("italiano");
        l.setEditore("editore modificato");
        l.setCategoria("FANTASCIENZA_FANTASY");
        l.setNrPagine(163);
        l.setCodIsbn("884263152");
        l.setDisponibilita(1);
        l.setPrezzo(3.87f);
        l.setNrCopie(200);
        l.setRecensione("provo a modificare un libro");
        l.setDesc("sto modificando un libro");
        l.setDataPubb(LocalDate.of(2024,2,4));

        csv.inserisciLibro(l);
    }

    @Test
    void testInsertRivista() throws  CsvValidationException, IOException {

        cScelta.getTypeDb("file");


        r.setTitolo("titolo rivista aggiornato");
        r.setTipologia("BIMENSILE");
        r.setAutore("autore modificato");
        r.setLingua("italiano");
        r.setEditore("editore modificato");
        r.setDescrizione("inserisco rivista");
        r.setDataPubb(LocalDate.of(2024,6,6));
        r.setDisp(1);
        r.setPrezzo(4.50f);
        r.setCopieRim(50);
        csv.inserisciRivista(r);


    }





    @Test
    void testInsertGiornale() throws CsvValidationException, IOException, IdException {

        cScelta.getTypeDb("file");

        g.setTitolo("titolo gionale inserito");
        g.setTipologia("QUOTIDIANO");
        g.setEditore("editore inserito");
        g.setLingua("italiano");


        g.setDataPubb(LocalDate.of(2024,3,5));
        g.setDisponibilita(0);
        g.setPrezzo(2.65f);
        g.setCopieRimanenti(0);

        csv.inserisciGiornale(g);


    }


    @Test
    void getListLibri() throws CsvValidationException, IOException, IdException, XMPException {
        vis.setTypeAsBook();
        assertNotNull(csv.retrieveAllData(new File(LIBROFINALE)));
    }
    @Test
    void getListGiornali() throws CsvValidationException, IOException, IdException, XMPException {
        vis.setTypeAsDaily();
        assertNotNull(csv.retrieveAllData(new File(GIORNALEFINALE)));
    }
    @Test
    void getListRiviste() throws CsvValidationException, IOException, IdException, XMPException {
        vis.setTypeAsMagazine();
        assertNotNull(csv.retrieveAllData(new File(RIVISTAFINALE)));
    }

   /* @Test
    void testGetCarteCredito() throws CsvValidationException, IOException, IdException {
        assertNotNull(csvF.getAllDataCredito("franco"));
    }

    @Test
    void testCancellaLibro() throws CsvValidationException, IOException {
        vis.setId(20);
        csv.eliminaOggetto(new File(LIBROFINALE));
    }



    @Test
    void testCancellaRivista() throws CsvValidationException, IOException {
        vis.setId(6);
        csv.eliminaOggetto(new File(RIVISTAFINALE));
    }



    @Test
    void testCancellaGiornale() throws CsvValidationException, IOException {
        System.out.println("sto nel cancella pdd");
        vis.setId(13);
       csv.eliminaOggetto(new File(GIORNALEFINALE));
    }


 */












}
