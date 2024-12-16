package laptop.terzoUcAdmin.giornale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.database.GiornaleDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRemoveGiornale {
    private static final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final ControllerRaccolta cR;
    private static final GiornaleDao gD=new GiornaleDao();
    private static final Giornale g=new Giornale();
    private static final CsvOggettoDao csv;
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");


    static {
        try {
            cR = new ControllerRaccolta();
            csv=new CsvOggettoDao();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init()
    {
        vis.setId(0);
    }


    @Test
    void testRemoveGiornaleDB() throws CsvValidationException, IOException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        g.setTitolo(RBOGGETTO.getString("titoloModG"));
        int id=gD.getGiornaleIdTitoloAutore(g).get(0).getId();
        vis.setId(id);
        assertTrue(cR.elimina());


    }

    @Test
    void testRemoveGiornaleF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        g.setTitolo(RBOGGETTO.getString("titoloModG"));
        int id=csv.getGiornaleByIdTitoloEditore(g).get(0).getId();
        vis.setId(id);
        assertTrue(cR.elimina());


    }



}
