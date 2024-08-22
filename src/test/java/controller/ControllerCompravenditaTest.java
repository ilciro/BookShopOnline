package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerCompravendita;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerCompravenditaTest {
    private final ControllerCompravendita cC=new ControllerCompravendita();

    ControllerCompravenditaTest() throws IOException {
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void getLista(String strings) throws IOException, CsvValidationException, IdException {
        assertNotNull(cC.getLista(strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista"})
    void disponibilita(String strings) throws SQLException, IdException, CsvValidationException, IOException {
        assertTrue(cC.disponibilita(strings,"4"));
    }
}