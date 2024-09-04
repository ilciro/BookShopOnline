package controller;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerDownload;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ControllerDownloadTest {
    private final ControllerDownload cD=new ControllerDownload();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerDownloadTest() throws IOException {
    }


    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    void scaricaL(int ints) throws DocumentException, IOException, URISyntaxException {
        vis.setTypeAsBook();
        vis.setId(ints);
        cD.scarica(vis.getType());
        assertEquals(ints,vis.getId());
    }
    @Test
    void scaricaG() throws DocumentException, IOException, URISyntaxException {
        vis.setTypeAsDaily();
        vis.setId(1);
        cD.scarica(vis.getType());
        assertEquals(1,vis.getId());
    }
    @Test
    void scaricaR() throws DocumentException, IOException, URISyntaxException {
        vis.setTypeAsMagazine();
        vis.setId(1);
        cD.scarica(vis.getType());
        assertEquals(1,vis.getId());
    }
    @Test
    void annullaOrdineL() throws SQLException, CsvValidationException, IOException, IdException {
        vis.setMetodoP("cash");
        vis.setTypeAsBook();
        cD.annullaOrdine();
        assertEquals("libro",vis.getType());
    }
    @Test
    void annullaOrdineG() throws SQLException, CsvValidationException, IOException, IdException {
        vis.setMetodoP("cash");
        vis.setTypeAsDaily();
        cD.annullaOrdine();
        assertEquals("giornale",vis.getType());
    }
    @Test
    void annullaOrdineR() throws SQLException, CsvValidationException, IOException, IdException {
        vis.setMetodoP("cCredito");
        vis.setTypeAsMagazine();
        cD.annullaOrdine();
        assertEquals("rivista",vis.getType());
    }

}