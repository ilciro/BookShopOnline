package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestionePage;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerGestionePageTest {
    private final ControllerGestionePage cGP=new ControllerGestionePage();
    private final ControllerSystemState vis= ControllerSystemState.getInstance();

    ControllerGestionePageTest() throws IOException {
    }

    @Test
    void cancellaL() throws SQLException, CsvValidationException, IOException {
        vis.setId(20);
        vis.setTypeAsBook();
        cGP.cancella(vis.getId());
        assertEquals(20,vis.getId());
    }
    @Test
    void cancellaG() throws SQLException, CsvValidationException, IOException {
        vis.setId(13);
        vis.setTypeAsDaily();
        cGP.cancella(vis.getId());
        assertEquals(13,vis.getId());
    }
    @Test
    void cancellaR() throws SQLException, CsvValidationException, IOException {
        vis.setId(6);
        vis.setTypeAsMagazine();
        cGP.cancella(vis.getId());
        assertEquals(6,vis.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void getLista(String strings) throws CsvValidationException, IOException, IdException {
        assertNotNull(cGP.getLista(strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void settaHeader(String strings) {
        assertNotNull(cGP.settaHeader(strings));
    }
}