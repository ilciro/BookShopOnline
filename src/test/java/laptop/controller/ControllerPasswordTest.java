package laptop.controller;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerPasswordTest {
    private final ControllerPassword cP=new ControllerPassword();

    @Test
    void aggiornaPass() throws SQLException {
        assertFalse(cP.aggiornaPass("editoreMod@libero.it","EdiPM154","EditorM152M"));
    }
}