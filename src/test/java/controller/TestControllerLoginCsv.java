package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerLogin;
import laptop.controller.ControllerScelta;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestControllerLoginCsv {
    private final ControllerScelta cs=new ControllerScelta();
    private final ControllerLogin cL=new ControllerLogin();
    private static final ResourceBundle RBOGG=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void testControlla() throws CsvValidationException, SQLException, IOException {
        //logged as admin
        cs.getTypeDb("file");
        assertTrue(cL.controlla(RBOGG.getString("email1"),RBOGG.getString("pwd1")));
    }
    @Test
    void testGetRuolo() throws CsvValidationException, SQLException, IOException {
        cs.getTypeDb("file");
        assertEquals("ADMIN",cL.getRuoloTempUSer(RBOGG.getString("email1")));
    }

}
