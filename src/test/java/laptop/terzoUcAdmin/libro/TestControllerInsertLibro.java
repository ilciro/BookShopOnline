package laptop.terzoUcAdmin.libro;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerInsertLibro {

    private static final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final ControllerGestione cG ;


    static {
        try {
            cG=new ControllerGestione();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");


    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertLibro(String strings) throws CsvValidationException, SQLException, IOException {
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
}
