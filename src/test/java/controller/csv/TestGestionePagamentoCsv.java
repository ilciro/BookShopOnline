package controller.csv;

import com.itextpdf.xmp.XMPException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerPagamentoCC;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestGestionePagamentoCsv {

    private final ControllerPagamentoCC cPCC = new ControllerPagamentoCC();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();


    public TestGestionePagamentoCsv() throws IOException {
    }

    @Test
    void insertCC() throws CsvValidationException, SQLException, IOException, IdException {

        vis.setTypeOfDb("file");
        vis.setMetodoP("cCredito");
        vis.setSpesaT(36.52f);
        cPCC.aggiungiCartaDB(" arnaldo","indaco","8521-9632-5551-1111", new Date(System.currentTimeMillis()),"852",vis.getSpesaT());
    }
    @Test
    void getLista() throws  CsvValidationException, IOException, IdException {
        vis.setTypeOfDb("file");
        vis.setMetodoP("cCredito");
        assertNotNull(cPCC.ritornaElencoCC("arnaldo"));
    }



}
