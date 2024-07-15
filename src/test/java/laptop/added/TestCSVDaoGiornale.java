package laptop.added;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.CsvDaoGiornale;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class TestCSVDaoGiornale {

    private static final String LOCATION="report/localDBFileGiornale.csv";
    private static final ResourceBundle CVSBUNDLE=ResourceBundle.getBundle("configurations/infoObjects");




    @Test
    void testRetrieveDataGiornale() throws Exception {
        assertNotNull(CsvDaoGiornale.retreiveAllDataGiornali(new File(LOCATION), Integer.parseInt("5")));
    }
    @Test
    void testRetrieveGiornaleByIdTitoloEd() throws CsvValidationException, IOException {
        assertNotNull(CsvDaoGiornale.retrieveByIdTitoloEd(new File(LOCATION),CVSBUNDLE.getString("titoloDaPrendere"),CVSBUNDLE.getString("editoreDaPrendere")));
    }
    @Test
    void testSaveGiornale() throws IOException {
        Giornale g=new Giornale();
        g.setTitolo(CVSBUNDLE.getString("titoloG"));
        g.setTipologia(CVSBUNDLE.getString("tipoG"));
        g.setLingua(CVSBUNDLE.getString("lingua"));
        g.setEditore(CVSBUNDLE.getString("editoreG"));
        LocalDate ld=LocalDate.of(2024,5,2);
        g.setDataPubb(ld);
        g.setCopieRimanenti(Integer.parseInt(CVSBUNDLE.getString("copieG")));
        g.setDisponibilita(Integer.parseInt(CVSBUNDLE.getString("dispG")));
        g.setId(Integer.parseInt(CVSBUNDLE.getString("idG")));
         CsvDaoGiornale.saveGiornale(new File(LOCATION),g);
    }
    @Test
    void testRemoveGiornaleByid() throws Exception {


        Giornale g1=new Giornale();

        g1.setId(5);
        g1.setTitolo(CVSBUNDLE.getString("titoloG"));
        g1.setTipologia(CVSBUNDLE.getString("tipoG"));
        g1.setLingua(CVSBUNDLE.getString("lingua"));
        g1.setEditore(CVSBUNDLE.getString("editoreG"));
        LocalDate ld=LocalDate.of(2024,6,15);
        g1.setDataPubb(ld);
        g1.setCopieRimanenti(Integer.parseInt(CVSBUNDLE.getString("copieG")));
        g1.setDisponibilita(Integer.parseInt(CVSBUNDLE.getString("dispG")));
        CsvDaoGiornale.removeGiornaleById(new File(LOCATION),g1);
    }



}
