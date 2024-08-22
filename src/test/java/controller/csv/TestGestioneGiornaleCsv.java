
package controller.csv;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.raccolta.Giornale;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.sql.SQLException;

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



