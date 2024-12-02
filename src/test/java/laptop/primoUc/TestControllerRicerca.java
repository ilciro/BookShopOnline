package laptop.primoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRicerca;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.apache.ibatis.javassist.bytecode.CodeIterator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerRicerca {
    private static final ControllerRicerca cR;
    private static final ControllerSystemState vis = ControllerSystemState.getInstance();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final Libro l=new Libro();
    private static final Giornale g=new Giornale();
    private static final Rivista r=new Rivista();
    static {
        try {
            cR = new ControllerRicerca();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerTitoloL(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsBook();
        l.setTitolo(RBOGGETTO.getString("titoloL"));
        assertNotEquals(0,cR.getIdOggetto(l.getTitolo()));
    }
    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerEditoreL(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsBook();
        l.setEditore(RBOGGETTO.getString("editoreL"));
        assertNotEquals(0,cR.getIdOggetto(l.getEditore()));
    }
    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerAutoreL(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsBook();
        l.setAutore(RBOGGETTO.getString("autoreL"));
        assertNotEquals(0,cR.getIdOggetto(l.getAutore()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerTitoloG(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsDaily();
        assertNotEquals(0,cR.getIdOggetto(RBOGGETTO.getString("titoloG")));
    }
    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerEditoreG(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsDaily();
        assertNotEquals(0,cR.getIdOggetto(RBOGGETTO.getString("editoreG")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerTitoloR(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsMagazine();
        r.setTitolo(RBOGGETTO.getString("titoloR"));
        assertNotEquals(0,cR.getIdOggetto(r.getTitolo()));
    }
    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerEditoreR(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsMagazine();
        r.setEditore(RBOGGETTO.getString("editoreR"));
        assertNotEquals(0,cR.getIdOggetto(r.getEditore()));
    }
    @ParameterizedTest
    @ValueSource(strings = {"db", "file"})
    void testRicercaPerAutoreR(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsMagazine();
        assertNotEquals(0,cR.getIdOggetto(RBOGGETTO.getString("autoreR")));
    }





}
