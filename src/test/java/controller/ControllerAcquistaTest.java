package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAcquista;
import laptop.controller.ControllerSystemState;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAcquistaTest {
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerAcquistaTest() throws IOException {
        vis.setTypeOfDb("db");

    }


    @Test
    void totale1L() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeAsBook();
        vis.setId(6);
        assertNotEquals(0f,cA.totale1("libro","Erasgon Vol 1 ",0,2));
    }
    @Test
    void totale1R() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setTypeAsMagazine();
        vis.setId(2);
        assertNotEquals(0f,cA.totale1("rivista","Focus ",0,1));
    }
    @Test
    void totale1G() throws SQLException, IdException, CsvValidationException, IOException {

        vis.setTypeAsDaily();
        vis.setId(12);
        assertNotEquals(0f,cA.totale1("giornale","La gazzetta del profeta ",0,5));
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void inserisciAmmontare(String strings) throws AcquistaException, IdException, CsvValidationException, IOException {
        int id=1;
        cA.inserisciAmmontare(strings);
       assertEquals(1,id);
    }

    @Test
    void getNomeByIdL() throws SQLException, CsvValidationException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setId(2);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void getNomeByIdG() throws SQLException, CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setId(2);
        assertNotNull(cA.getNomeById());
    }
    @Test
    void getNomeByIdR() throws SQLException, CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setId(2);
        assertNotNull(cA.getNomeById());
    }

    @Test
    void getCostoL() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setId(6);
        assertNotEquals(0f,cA.getCosto("libro"));
    }
    @Test
    void getCostoG() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setId(4);
        assertNotEquals(0f,cA.getCosto("giornale"));
    }
    @Test
    void getCostoR() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setId(4);
        assertNotEquals(0f,cA.getCosto("rivista"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void getDisp(String strings) throws SQLException, IdException, CsvValidationException, IOException {
        vis.setId(4);
        assertNotEquals(0,cA.getDisp(strings));
    }
}