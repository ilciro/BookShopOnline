package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerCheckPagamentoData;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;


class ControllerCheckPagamentoDataTest {
    private final ControllerCheckPagamentoData cCPD=new ControllerCheckPagamentoData();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerCheckPagamentoDataTest() throws IOException {
    }


    @Test
    void checkPagamentoDataL() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeAsBook();
        vis.setSpesaT(12f);
        vis.setId(6);
        vis.setMetodoP("cash");
        cCPD.checkPagamentoData("franco");

    }
    @Test
    void checkPagamentoDataG() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        vis.setSpesaT(2);
        vis.setId(1);
        vis.setMetodoP("cash");
        cCPD.checkPagamentoData("marco");
    }
    @Test
    void checkPagamentoDataR() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeOfDb("db");
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        vis.setSpesaT(5f);
        vis.setId(6);
        vis.setMetodoP("cCredito");
        cCPD.checkPagamentoData("franco");

    }
}