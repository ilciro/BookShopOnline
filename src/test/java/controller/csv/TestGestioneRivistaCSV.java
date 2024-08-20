
package controller.csv;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.sql.SQLException;


public class TestGestioneRivistaCSV {
    private final ControllerSystemState vis = ControllerSystemState.getInstance();
    private final ControllerScelta cS = new ControllerScelta();
    private final ControllerCompravendita cCV = new ControllerCompravendita();
    private final ControllerVisualizza cV = new ControllerVisualizza();
    private final ControllerAcquista cA = new ControllerAcquista();
    private final ControllerPagamentoCash cPcash = new ControllerPagamentoCash();
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();

    private static final Rivista r=new Rivista();

    public TestGestioneRivistaCSV() throws IOException {


    }

    @Test
    void testGetListaRivistaCash() throws CsvValidationException, IOException, IdException, SQLException {
        cS.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setMetodoP("cash");
        cCV.getLista(vis.getType());
        //controllo e mostro
        r.setId(1);
        cCV.disponibilita(vis.getType(), String.valueOf(r.getId()));
        //mostro oggetto
        cV.getDataR(r.getId());
        //prendo tutti i dati
        vis.setSpesaT(cA.totale1(vis.getType(), cA.getNomeById(), cA.getDisp(vis.getType()), 5));
        //pagamento fattura
        Fattura f = new Fattura();
        f.setNome("giorgio");
        f.setCognome("violi");
        f.setVia("via delle petunie snc");
        f.setCom("dopo le 12");

        cPcash.controlla(f.getNome(), f.getCognome(), f.getVia(), f.getCom());


        //funziona
    }


    @Test
    void testGetListaRivistaCC() throws CsvValidationException, IOException, IdException, SQLException {
        cS.getTypeDb("file");
        vis.setTypeAsMagazine();
        vis.setMetodoP("cCredito");
        cCV.getLista(vis.getType());
        //controllo e mostro
        r.setId(2);
        cCV.disponibilita(vis.getType(), String.valueOf(r.getId()));
        //mostro oggetto
        cV.getDataR(r.getId());
        //prendo tutti i dati
        vis.setSpesaT(cA.totale1(vis.getType(), cA.getNomeById(), cA.getDisp(vis.getType()), 1));
        //pagamento cc
        cPCC.pagamentoCC("alex");

        //funziona
    }


}


