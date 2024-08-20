package controller.dao;

import laptop.controller.ControllerReportPage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerReportPageTest {
    private final ControllerReportPage cRP=new ControllerReportPage();

    ControllerReportPageTest() throws IOException {
    }

    @Test
    void leggiLibro() throws IOException {
        assertNotNull(cRP.leggiLibro());
    }

    @Test
    void leggiGiornale() throws IOException {
        assertNotNull(cRP.leggiGiornale());
    }

    @Test
    void leggiRivista() throws IOException {
        assertNotNull(cRP.leggiRivista());
    }

    @Test
    void reportTotale() throws IOException {
        assertNotNull(cRP.reportTotale());
    }

    @Test
    void reportRaccolta() throws IOException {
        assertNotNull(cRP.reportRaccolta());
    }

}