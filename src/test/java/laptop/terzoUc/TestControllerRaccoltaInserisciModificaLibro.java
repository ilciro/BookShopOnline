package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.database.LibroDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



class TestControllerRaccoltaInserisciModificaLibro {
    //seconda parte dei test sul controller
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerGestione cG;
     private static final Libro l=new Libro();
     private static final LibroDao lD=new LibroDao();


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
         vis.setTipoModifica("insert");
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
    void testModifLibro(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb(strings);
        vis.setTipoModifica("modifica");
        //azzero e faccio match su titolo
        vis.setId(0);
        l.setId(0);

        l.setTitolo(RBOGGETTO.getString("titoloLI"));
       int id=lD.getLibroByIdTitoloAutoreLibro(l).get(0).getId();

        vis.setId(id);


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

      assertTrue(cG.modifica(infoL));







    }








    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testReturnListL(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb(strings);

        l.setTitolo(RBOGGETTO.getString("titoloLI"));
        int id=lD.getLibroByIdTitoloAutoreLibro(l).get(0).getId();
        l.setId(id);

        vis.setId(l.getId());
        assertNotEquals(0,cG.libroById().get(0).getId());
    }









}
