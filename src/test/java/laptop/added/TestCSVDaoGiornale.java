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
    private static final String LOCATION="localDBFileGiornale.csv";
    private static final ResourceBundle CVSBUNDLE=ResourceBundle.getBundle("configurations/infoObjects");




    @Test
    void testRetrieveDataGiornale() throws Exception {
        File file=new File(LOCATION);
        assertNotNull(CsvDaoGiornale.retreiveAllDataGiornali(file, Integer.parseInt("5")));
    }
    @Test
    void testRetrieveGiornaleByIdTitoloEd() throws CsvValidationException, IOException {
        File file=new File(LOCATION);
        assertNotNull(CsvDaoGiornale.retrieveByIdTitoloEd(file,CVSBUNDLE.getString("titoloDaPrendere"),CVSBUNDLE.getString("editoreDaPrendere")));
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



}
