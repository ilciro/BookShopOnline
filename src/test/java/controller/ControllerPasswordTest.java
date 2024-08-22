package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerPassword;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerPasswordTest {
    private final ControllerPassword cP=new ControllerPassword();

    @Test
    void aggiornaPass() throws SQLException, CsvValidationException, IOException, IdException {
        assertFalse(cP.aggiornaPass("editoreMod@libero.it","EdiPM154","EditorM152M"));
    }
}