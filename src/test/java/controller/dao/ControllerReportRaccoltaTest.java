package controller.dao;

import laptop.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerReportRaccoltaTest {
    private  final User u=User.getInstance();


    @Test
    void getTipo() {
        u.setIdRuolo("A");
        assertEquals("ADMIN",u.getIdRuolo());
    }
}