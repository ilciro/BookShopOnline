package laptop.terzoUcAdmin.libro;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.database.LibroDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerModifLibro {
    private static final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final ControllerGestione cG;

    static {
        try {
            cG = new ControllerGestione();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final LibroDao lD=new LibroDao();
    private static final Libro l=new Libro();
    private static final CsvOggettoDao csv;

    static {
        try {
            csv = new CsvOggettoDao();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");

    @BeforeEach
     void init()
    {
        vis.setId(0);
    }

    @Test
    void testModifLibroDB() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb("db");
        vis.setTipoModifica("modifica");
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
        assertTrue(cG.modifica(infoL));
    }

    @Test
    void testModifLibroF() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb("file");
        vis.setTipoModifica("modifica");
        l.setTitolo(RBOGGETTO.getString("titoloLI"));
        int id=csv.getLibroByIdTitoloAutore(l).get(0).getId();
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
        assertTrue(cG.modifica(infoL));
    }
}
