package controller.csv;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;

import laptop.model.Fattura;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import java.sql.SQLException;


public class TestGestioneLibroCsv {
    private final ControllerSystemState vis = ControllerSystemState.getInstance();
    private final ControllerScelta cS = new ControllerScelta();
    private final ControllerCompravendita cCV = new ControllerCompravendita();
    private final ControllerVisualizza cV = new ControllerVisualizza();
    private final ControllerAcquista cA = new ControllerAcquista();
    private final ControllerPagamentoCash cPcash = new ControllerPagamentoCash();
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();



    private  final Libro l = new Libro();

    public TestGestioneLibroCsv() throws IOException {

    }

    @Test
    void testGetListaLibroCash() throws CsvValidationException, IOException, IdException, SQLException {
        cS.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setMetodoP("cash");
        cCV.getLista(vis.getType());
        //controllo e mostro
        l.setId(6);
        cCV.disponibilita(vis.getType(), String.valueOf(l.getId()));
        //mostro oggetto
        cV.getDataL(l.getId());
        //prendo tutti i dati
        vis.setSpesaT(cA.totale1(vis.getType(), cA.getNomeById(), cA.getDisp(vis.getType()), 5));
        //pagamento fattura
        Fattura f = new Fattura();
        f.setNome("francesco");
        f.setCognome("marroni");
        f.setVia("via delle ginestre 524/b");

        cPcash.controlla(f.getNome(), f.getCognome(), f.getVia(), f.getCom());

        //funziona
    }
    @Test
    void testGetListaLibroCC() throws CsvValidationException, IOException, IdException, SQLException {
        cS.getTypeDb("file");
        vis.setTypeAsBook();
        vis.setMetodoP("cCredito");
        cCV.getLista(vis.getType());
        //controllo e mostro
        l.setId(2);
        cCV.disponibilita(vis.getType(), String.valueOf(l.getId()));
        //mostro oggetto
        cV.getDataL(l.getId());
        //prendo tutti i dati
        vis.setSpesaT(cA.totale1(vis.getType(), cA.getNomeById(), cA.getDisp(vis.getType()), 1));
        //pagamento cc
        cPCC.pagamentoCC("mirko");

        //funziona
    }






}
