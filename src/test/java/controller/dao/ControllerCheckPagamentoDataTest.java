package controller.dao;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerCheckPagamentoData;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
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
        cCPD.checkPagamentoData("franco");

    }
    @Test
    void checkPagamentoDataG() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeAsDaily();
        vis.setSpesaT(2);
        vis.setId(1);
        cCPD.checkPagamentoData("marco");

    }
    @Test
    void checkPagamentoDataR() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeAsMagazine();
        vis.setSpesaT(5f);
        vis.setId(6);
        cCPD.checkPagamentoData("franco");

    }
}