package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRaccoltaInserisciModifica {
    //seconda parte dei test sul controller
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerGestione cG;
     private static final Libro l=new Libro();
     private static final LibroDao lD=new LibroDao();
     private static final Giornale g=new Giornale();
     private static final GiornaleDao gD=new GiornaleDao();
     private static final Rivista r=new Rivista();
     private static final RivistaDao rD=new RivistaDao();

    static {
        try {
            cG = new ControllerGestione();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertLibro(String strings) throws CsvValidationException, SQLException, IOException, IdException {
         vis.setTypeAsBook();
         vis.setTypeOfDb(strings);
        String []infoL=new String[13];
        infoL[0]=RBOGGETTO.getString("titoloLI");
        infoL[1]=RBOGGETTO.getString("isbnL");
        infoL[2]=RBOGGETTO.getString("editoreLI");
        infoL[3]=RBOGGETTO.getString("autoreLI");
        infoL[4]=RBOGGETTO.getString("linguaLI");
        infoL[5]=RBOGGETTO.getString("categoriaL");
        infoL[6]=RBOGGETTO.getString("descrizioneL");
        infoL[7]=RBOGGETTO.getString("dataPubbL");
        infoL[8]=RBOGGETTO.getString("recensioneL");
        infoL[9]=RBOGGETTO.getString("numPagL");
        infoL[10]=RBOGGETTO.getString("nrCopieL");
        infoL[11]=RBOGGETTO.getString("dispL");
        infoL[12]=RBOGGETTO.getString("prezzoLI");

        assertTrue(cG.inserisci(infoL));

     }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertGiornale(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb(strings);
        String []infoG=new String[13];
        infoG[0]=RBOGGETTO.getString("titoloGI");
        infoG[2]=RBOGGETTO.getString("editoreGI");
        infoG[4]=RBOGGETTO.getString("linguaGI");
        infoG[5]=RBOGGETTO.getString("categoriaG");
        infoG[7]=RBOGGETTO.getString("dataPubbG");
        infoG[10]=RBOGGETTO.getString("nrCopieG");
        infoG[11]=RBOGGETTO.getString("dispG");
        infoG[12]=RBOGGETTO.getString("prezzoGI");

        assertTrue(cG.inserisci(infoG));

    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertRivista(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        String []infoR=new String[13];
        infoR[0]=RBOGGETTO.getString("titoloRI");
        infoR[2]=RBOGGETTO.getString("editoreRI");
        infoR[3]=RBOGGETTO.getString("autoreRI");
        infoR[4]=RBOGGETTO.getString("linguaRI");
        infoR[5]=RBOGGETTO.getString("categoriaR");
        infoR[6]=RBOGGETTO.getString("descrizioneR");
        infoR[7]=RBOGGETTO.getString("dataPubbR");
        infoR[10]=RBOGGETTO.getString("nrCopieR");
        infoR[11]=RBOGGETTO.getString("dispR");
        infoR[12]=RBOGGETTO.getString("prezzoRI");

        assertTrue(cG.inserisci(infoR));

    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModifLibro(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb(strings);
        l.setTitolo(RBOGGETTO.getString("titoloLI"));
        l.setId(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getId());
        vis.setId(l.getId());




        String []infoL=new String[14];
        infoL[0]=RBOGGETTO.getString("titoloModL");
        infoL[1]=RBOGGETTO.getString("isbnModL");
        infoL[2]=RBOGGETTO.getString("editoreModL");
        infoL[3]=RBOGGETTO.getString("autoreModL");
        infoL[4]=RBOGGETTO.getString("linguaModL");
        infoL[5]=RBOGGETTO.getString("categoriaModL");
        infoL[6]=RBOGGETTO.getString("descrizioneModL");
        infoL[7]=RBOGGETTO.getString("dataPubbModL");
        infoL[8]=RBOGGETTO.getString("recensioneModL");
        infoL[9]=RBOGGETTO.getString("numPagModL");
        infoL[10]=RBOGGETTO.getString("nrCopieModL");
        infoL[11]=RBOGGETTO.getString("dispModL");
        infoL[12]=RBOGGETTO.getString("prezzoModL");
        infoL[13]= String.valueOf(vis.getId());

        cG.modifica(infoL);

        assertNotEquals(0,vis.getId());





    }



    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModifGiornale(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb(strings);
        g.setTitolo(RBOGGETTO.getString("titoloGI"));
        g.setId(gD.getGiornaleIdTitoloAutore(g).get(0).getId());
        vis.setId(g.getId());

        String []infoG=new String[13];
        infoG[0]=RBOGGETTO.getString("titoloModG");
        infoG[2]=RBOGGETTO.getString("editoreModG");
        infoG[4]=RBOGGETTO.getString("linguaModG");
        infoG[5]=RBOGGETTO.getString("categoriaModG");
        infoG[7]=RBOGGETTO.getString("dataPubbModG");
        infoG[10]=RBOGGETTO.getString("nrCopieModG");
        infoG[11]=RBOGGETTO.getString("dispModG");
        infoG[12]=RBOGGETTO.getString("prezzoModGI");

        assertTrue(cG.modifica(infoG));

    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModifRivista(String strings) throws CsvValidationException, SQLException, IOException, IdException {

        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        r.setTitolo(RBOGGETTO.getString("titoloRI"));
        r.setId(rD.getRivistaIdTitoloAutore(r).get(0).getId());
        vis.setId(r.getId());
        String []infoR=new String[13];
        infoR[0]=RBOGGETTO.getString("titoloModR");
        infoR[2]=RBOGGETTO.getString("editoreModR");
        infoR[3]=RBOGGETTO.getString("autoreModR");
        infoR[4]=RBOGGETTO.getString("linguaModR");
        infoR[5]=RBOGGETTO.getString("categoriaModR");
        infoR[6]=RBOGGETTO.getString("descrizioneModR");
        infoR[7]=RBOGGETTO.getString("dataPubbModR");
        infoR[10]=RBOGGETTO.getString("nrCopieModR");
        infoR[11]=RBOGGETTO.getString("dispModR");
        infoR[12]=RBOGGETTO.getString("prezzoModR");

        assertTrue(cG.modifica(infoR));

    }







}
