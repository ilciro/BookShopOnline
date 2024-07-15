package laptop.controller;

import laptop.database.CsvGiornaleDao;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;
import laptop.utilities.ConnToDb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAcquistaTest {
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerAcquistaTest() throws IOException {

    }

    @BeforeAll
    static void init() throws IOException {
        ConnToDb.creaPopolaDb();
    }

    @Test
    void totale1L() throws SQLException, IdException {
        vis.setId(6);
        assertNotEquals(0f,cA.totale1("libro","Erasgon Vol 1 ",0,2));
    }
    @Test
    void totale1R() throws SQLException, IdException {
        vis.setId(2);
        assertNotEquals(0f,cA.totale1("rivista","Focus ",0,1));
    }
    @Test
    void totale1G() throws SQLException, IdException {
        vis.setId(12);
        assertNotEquals(0f,cA.totale1("giornale","La gazzetta del profeta ",0,5));
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void inserisciAmmontare(String strings) throws AcquistaException, IdException {
        int id=1;
        cA.inserisciAmmontare(strings,id);
       assertEquals(1,id);
    }

    @Test
    void getNomeByIdL() throws SQLException {
        vis.setTypeAsBook();
        vis.setId(2);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void getNomeByIdG() throws SQLException {
        vis.setTypeAsDaily();
        vis.setId(2);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void getNomeByIdR() throws SQLException {
        vis.setTypeAsMagazine();
        vis.setId(2);
        assertNotNull(cA.getNomeById());
    }

    @Test
    void getCostoL() throws SQLException, IdException {
        vis.setId(6);
        assertNotEquals(0f,cA.getCosto("libro"));
    }
    @Test
    void getCostoG() throws SQLException, IdException {
        vis.setId(4);
        assertNotEquals(0f,cA.getCosto("giornale"));
    }
    @Test
    void getCostoR() throws SQLException, IdException {
        vis.setId(4);
        assertNotEquals(0f,cA.getCosto("rivista"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void getDisp(String strings) throws SQLException, IdException {
        vis.setId(4);
        assertNotEquals(0,cA.getDisp(strings));
    }
}