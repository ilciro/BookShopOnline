package controller.csv;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestOggettoCSV {
    private static CsvOggettoDao csv = null;



    private  final ControllerSystemState vis=ControllerSystemState.getInstance();

    public TestOggettoCSV() throws IOException {
         csv=new CsvOggettoDao();
    }


    @Test
    void insertLibro() throws CsvValidationException, IOException, IdException {
        ControllerSystemState.getInstance().setTypeOfDb("file");
        ControllerSystemState.getInstance().setTypeAsBook();
        Libro l = new Libro();
        l.setTitolo("guida al c");
        l.setCodIsbn("152663aa");
        l.setEditore("hoepli");
        l.setAutore("hoepli");
        l.setLingua("italiano");
        l.setCategoria("INFORMATICA");
        l.setNrPagine(850);
        l.setNrCopie(500);
        l.setDisponibilita(1);
        l.setPrezzo(45f);
        l.setDataPubb(LocalDate.of(2025,4,6));
        l.setRecensione("manuale completo");
        l.setDesc("manuale c");
        csv.inserisciLibro(l);

    }

    @Test
    void insertGiornale() throws CsvValidationException, IOException, IdException {
        Giornale g=new Giornale();
        vis.setTypeOfDb("file");
        vis.setTypeAsDaily();
        g.setTitolo("il corriere");
        g.setTipologia("QUOTIDIANO");
        g.setLingua("italiano");
        g.setEditore("corriere della sera");
        g.setDataPubb(LocalDate.now());
        g.setCopieRimanenti(150);
        g.setDisponibilita(1);
        g.setPrezzo(2f);
        csv.inserisciGiornale(g);

    }

    @Test
    void inserisciRivista() throws CsvValidationException, IOException, IdException {
        Rivista r=new Rivista();
        vis.setTypeOfDb("file");
        vis.setTypeAsMagazine();
        r.setTitolo("giallo");
        r.setTipologia("SETTIMANALE");
        r.setAutore("biavardi");
        r.setLingua("italiano");
        r.setEditore("giallo");
        r.setDescrizione("cronaca dei fatti..");
        r.setDataPubb(LocalDate.now());
        r.setDisp(1);
        r.setPrezzo(1.85f);
        r.setCopieRim(50);

        csv.inserisciRivista(r);

    }
    @Test
    void ObsLibro() throws CsvValidationException, IOException, IdException {
        Libro l=new Libro();
        l.setId(6);
        assertEquals(1,csv.getLibroByIdTitoloAutore(new File("report/reportLibro.csv"),l).size());
    }
    @Test
    void ObsGiornale() throws CsvValidationException, IOException, IdException {
       Giornale g=new Giornale();
        g.setTitolo("Il Fatto Quotidiano3");
        assertEquals(1,csv.getGiornaleByIdTitoloEditore(new File("report/reportGiornale.csv"),g).size());
    }
    @Test
    void ObsRivista() throws CsvValidationException, IOException, IdException {
       Rivista r=new Rivista();
        r.setTitolo("Rivista A");
        assertEquals(1,csv.getRivistaByIdTitoloEditore(new File("report/reportRivista.csv"),r).size());
    }








}
