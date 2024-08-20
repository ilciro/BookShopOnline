
package controller.csv;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.raccolta.Giornale;
import laptop.utilities.ConnToDb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class TestGestioneGiornaleCsv {
    private final ControllerSystemState vis = ControllerSystemState.getInstance();
    private final ControllerScelta cS = new ControllerScelta();
    private final ControllerCompravendita cCV = new ControllerCompravendita();
    private final ControllerVisualizza cV = new ControllerVisualizza();
    private final ControllerAcquista cA = new ControllerAcquista();
    private final ControllerPagamentoCash cPcash = new ControllerPagamentoCash();
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();



    private  final Giornale g=new Giornale();

    public TestGestioneGiornaleCsv() throws IOException {
    }


    @BeforeAll
    static void init() throws IOException {
        File fg=new File("report/reportGiornale.csv");
        File fl=new File("report/reportLibro.csv");
        File fr=new File("report/reportRivista.csv");
        Files.copy(Path.of("src/main/resources/csvfiles/libro.csv"), Path.of(fl.toURI()), REPLACE_EXISTING);
        Files.copy(Path.of("src/main/resources/csvfiles/giornale.csv"), Path.of(fg.toURI()), REPLACE_EXISTING);
        Files.copy(Path.of("src/main/resources/csvfiles/rivista.csv"), Path.of(fr.toURI()), REPLACE_EXISTING);
        Files.copy(Path.of("src/main/resources/csvfiles/utente.csv"), Path.of("report/reportUtente.csv"), REPLACE_EXISTING);
        ConnToDb.creaPopolaDb();
    }

    @Test
    void testGetListaGiornaleCash() throws CsvValidationException, IOException, IdException, SQLException {
        cS.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setMetodoP("cash");
        cCV.getLista(vis.getType());
        //controllo e mostro
        g.setId(4);
        cCV.disponibilita(vis.getType(), String.valueOf(g.getId()));
        //mostro oggetto
        cV.getDataG(g.getId());
        //prendo tutti i dati
        vis.setSpesaT(cA.totale1(vis.getType(), cA.getNomeById(), cA.getDisp(vis.getType()), 5));
        //pagamento fattura
        Fattura f = new Fattura();
        f.setNome("alex");
        f.setCognome("verdi");
        f.setVia("piazza rossi snc");
        f.setCom("chiamare il 362552008415");

        cPcash.controlla(f.getNome(), f.getCognome(), f.getVia(), f.getCom());


        //funziona
    }


    @Test
    void testGetListaGiornaleCC() throws CsvValidationException, IOException, IdException, SQLException {
        cS.getTypeDb("file");
        vis.setTypeAsDaily();
        vis.setMetodoP("cCredito");
        cCV.getLista(vis.getType());
        //controllo e mostro
        g.setId(1);
        cCV.disponibilita(vis.getType(), String.valueOf(g.getId()));
        //mostro oggetto
        cV.getDataG(g.getId());
        //prendo tutti i dati
        vis.setSpesaT(cA.totale1(vis.getType(), cA.getNomeById(), cA.getDisp(vis.getType()), 1));
        //pagamento cc
        cPCC.pagamentoCC("alessia");

        //funziona
    }

}



