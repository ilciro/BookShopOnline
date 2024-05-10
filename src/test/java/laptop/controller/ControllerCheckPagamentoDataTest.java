package laptop.controller;

import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerCheckPagamentoDataTest {
    private final ControllerCheckPagamentoData cCPD=new ControllerCheckPagamentoData();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerCheckPagamentoDataTest() throws IOException {
    }

    @Test
    void checkPagamentoDataL() throws SQLException, IdException {
        vis.setTypeAsBook();
        vis.setSpesaT(12f);
        vis.setId(6);
        cCPD.checkPagamentoData("franco");

    }
    @Test
    void checkPagamentoDataG() throws SQLException, IdException {
        vis.setTypeAsDaily();
        vis.setSpesaT(2);
        vis.setId(1);
        cCPD.checkPagamentoData("marco");

    }
    @Test
    void checkPagamentoDataR() throws SQLException, IdException {
        vis.setTypeAsMagazine();
        vis.setSpesaT(5f);
        vis.setId(6);
        cCPD.checkPagamentoData("franco");

    }
}