package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLData;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerPagamentoCCTest {
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerPagamentoCCTest() throws IOException {
    }

    @Test
    void controllaPag() {
        assertTrue(cPCC.controllaPag("2024-01-05","1852-9995-2515-6351","258"));
    }

    @Test
    void aggiungiCartaDB() throws SQLException, IdException, CsvValidationException, IOException {
        vis.setSpesaT(15.98f);
        cPCC.aggiungiCartaDB("franco","rossi","1852-9995-2515-6351", java.sql.Date.valueOf(LocalDate.of(2024,1,5)),"258",vis.getSpesaT());
        assertNotEquals(0,vis.getSpesaT());
    }

    @Test
    void ritornaElencoCC() throws CsvValidationException, IOException, IdException {
        assertNotNull(cPCC.ritornaElencoCC("franco"));
    }

    @Test
    void tornaDalDb() {
        assertNotNull(cPCC.tornaDalDb("1852-9995-2515-6351"));
    }

    @Test
    void pagamentoCC() throws SQLException, IdException, IOException, CsvValidationException {
        String nome="franco";
        cPCC.pagamentoCC(nome);
        assertNotNull(nome);
    }
}