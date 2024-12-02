package laptop.primoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerPagamentoCC;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerPagamentoCC {
    private static final ControllerPagamentoCC cPcc;
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ResourceBundle RBOGGETTI=ResourceBundle.getBundle("configurations/objects");
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCCREDITO=ResourceBundle.getBundle("configurations/cartaCredito");

    static {
        try {
            cPcc = new ControllerPagamentoCC();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
     void testPagamentoLDB() throws CsvValidationException, SQLException, IOException, IdException {
       vis.setTypeAsBook();
        vis.setTypeOfDb("db");
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoL")));
        cPcc.pagamentoCC(RBUTENTE.getString("nome"));
        assertNotEquals(0f,vis.getSpesaT());
    }
    @Test
    void testPagamentoLF() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb("file");
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoL")));
        cPcc.pagamentoCC(RBUTENTE.getString("nome"));
        assertNotEquals(0f,vis.getSpesaT());
    }

    @Test
    void testPagamentoGDB() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoG")));
        cPcc.pagamentoCC(RBUTENTE.getString("nome"));
        assertNotEquals(0f,vis.getSpesaT());
    }
    @Test
    void testPagamentoGF() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoG")));
        cPcc.pagamentoCC(RBUTENTE.getString("nome"));
        assertNotEquals(0f,vis.getSpesaT());
    }

    @Test
    void testPagamentoRDB() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoR")));
        cPcc.pagamentoCC(RBUTENTE.getString("nome"));
        assertNotEquals(0f,vis.getSpesaT());
    }
    @Test
    void testPagamentoRF() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoR")));
        cPcc.pagamentoCC(RBUTENTE.getString("nome"));
        assertNotEquals(0f,vis.getSpesaT());
    }

    @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void testAggiungiCartaG(String strings)
    {
        vis.setTypeOfDb(strings);
        vis.setMetodoP("cCredito");
        String data=RBCCREDITO.getString("data");
        vis.setTypeAsDaily();
        Giornale g=new Giornale();
        g.setId(Integer.parseInt(RBOGGETTI.getString("idG")));
        vis.setId(g.getId());
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoG")));
        assertDoesNotThrow(()->cPcc.aggiungiCartaDB(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBCCREDITO.getString("codice"), Date.valueOf(data),RBCCREDITO.getString("civ"), Float.parseFloat(RBCCREDITO.getString("prezzo"))) );
    }

    @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void testAggiungiCartaR(String strings)
    {
        vis.setTypeOfDb(strings);
        vis.setMetodoP("cCredito");
        String data1=RBCCREDITO.getString("data1");
        vis.setTypeAsMagazine();
        Rivista r=new Rivista();
        r.setId(Integer.parseInt(RBOGGETTI.getString("idR")));
        vis.setId(r.getId());
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoR")));
        assertDoesNotThrow(()->cPcc.aggiungiCartaDB(RBUTENTE.getString("nome1"),RBUTENTE.getString("cognome1"),RBCCREDITO.getString("codice1"), Date.valueOf(data1),RBCCREDITO.getString("civ1"), Float.parseFloat(RBCCREDITO.getString("prezzo"))) );

    }

    @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void testAggiungiCartaL(String strings)
    {
        vis.setTypeOfDb(strings);
        vis.setMetodoP("cCredito");
        String data1=RBCCREDITO.getString("data1");
        vis.setTypeAsBook();
        Libro l=new Libro();
        l.setId(Integer.parseInt(RBOGGETTI.getString("idL")));
        vis.setId(l.getId());
        vis.setSpesaT(Float.parseFloat(RBOGGETTI.getString("prezzoL")));
        assertDoesNotThrow(()->cPcc.aggiungiCartaDB(RBUTENTE.getString("nome1"),RBUTENTE.getString("cognome1"),RBCCREDITO.getString("codice"), Date.valueOf(data1),RBCCREDITO.getString("civ1"), Float.parseFloat(RBCCREDITO.getString("prezzo1"))) );
    }
    @Test
    void testElencoCCDB() throws CsvValidationException, IOException {
        vis.setTypeOfDb("db");
        assertNotNull(cPcc.ritornaElencoCC(RBUTENTE.getString("nome")));
    }

    @Test
    void testElencoCCF() throws CsvValidationException, IOException {
        vis.setTypeOfDb("file");
        assertNotNull(cPcc.ritornaElencoCC(RBUTENTE.getString("nome1")));
    }


}
