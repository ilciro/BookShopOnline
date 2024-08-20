package controller.dao;

import laptop.controller.ControllerVisualizzaOrdine;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerVisualizzaOrdineTest {
    private final ControllerVisualizzaOrdine cVO=new ControllerVisualizzaOrdine();

    @Test
    void getDati() throws SQLException {
        assertNotNull(cVO.getDati());
    }
}