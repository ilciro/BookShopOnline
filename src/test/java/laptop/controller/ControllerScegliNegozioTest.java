package laptop.controller;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerScegliNegozioTest {
    private final ControllerScegliNegozio cSN=new ControllerScegliNegozio();

    @Test
    void getNegozi() throws SQLException {
        assertNotNull(cSN.getNegozi());
    }
}