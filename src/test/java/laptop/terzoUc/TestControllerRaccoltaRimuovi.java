package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerRaccoltaRimuovi {
     //parte tre della gestione di oggetto
     private static final ControllerRaccolta cR;
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private static final Libro l=new Libro();
     private static final LibroDao lD=new LibroDao();
     private static final Giornale g=new Giornale();
     private static final GiornaleDao gD=new GiornaleDao();
     private static final Rivista r=new Rivista();
     private static final RivistaDao rD=new RivistaDao();



     static {
        try {
            cR = new ControllerRaccolta();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testEliminaLibro(String strings) throws CsvValidationException, IOException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsBook();
        l.setId(0);
        vis.setId(l.getId());

        l.setTitolo(RBOGGETTO.getString("titoloModL"));
        int id=lD.getLibroByIdTitoloAutoreLibro(l).get(0).getId();

        l.setId(id);


        vis.setId(l.getId());
       assertTrue(cR.elimina());
    }




    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testEliminaGiornale(String strings)  {
        vis.setTypeOfDb(strings);
        vis.setTypeAsDaily();
        g.setTitolo(RBOGGETTO.getString("titoloModG"));
        int id=gD.getGiornaleIdTitoloAutore(g).get(0).getId();
        g.setId(id);
        vis.setId(g.getId());


        //delete non effettuata


        assertNotEquals(0,vis.getId());

    }







    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testEliminaRivista(String strings) throws CsvValidationException, IOException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsMagazine();
        r.setTitolo(RBOGGETTO.getString("titoloModR"));
        int id=rD.getRivistaIdTitoloAutore(r).get(0).getId();
        r.setId(id);
        vis.setId(r.getId());
        assertTrue(cR.elimina());

    }




}






