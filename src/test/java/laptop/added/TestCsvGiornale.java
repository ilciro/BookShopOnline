package laptop.added;

import laptop.database.CsvGiornaleDao;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class TestCsvGiornale {
    private static CsvGiornaleDao csv = null;
    private static final Giornale g=new Giornale();
    private static final String LOCATION="report/reportGiornali.csv";
    private static final ResourceBundle CVSBUNDLE= ResourceBundle.getBundle("configurations/infoObjects");

    public TestCsvGiornale() throws IOException {
         csv=new CsvGiornaleDao();
    }
    @Test
     void report() throws IOException {
        csv.generaReport();
    }
    @Test
    void testRicercaById() throws Exception {
        g.setId(5);
        csv.giornaleById(new File(LOCATION),g.getId());
    }
    @Test
    void testRicercaByIdTitoloEditore() throws Exception {
        g.setEditore(CVSBUNDLE.getString("editoreDaPrendere"));
        g.setTitolo(CVSBUNDLE.getString("titoloDaPrendere"));
        csv.giornaliByIdTitoloEd(new File(LOCATION),g.getTitolo(),g.getEditore());
    }
    @Test
    void testInsert() throws Exception {
        g.setTitolo(CVSBUNDLE.getString("titoloG"));
        g.setTipologia(CVSBUNDLE.getString("tipoG"));
        g.setLingua(CVSBUNDLE.getString("lingua"));
        g.setEditore(CVSBUNDLE.getString("editoreG"));
        LocalDate ld=LocalDate.of(2024,5,2);
        g.setDataPubb(ld);
        g.setCopieRimanenti(Integer.parseInt(CVSBUNDLE.getString("copieG")));
        g.setDisponibilita(Integer.parseInt(CVSBUNDLE.getString("dispG")));

        g.setId(13);
        csv.checkDuplicate(g);
    }

    @Test
    void testRemove() throws Exception {
        g.setId(Integer.parseInt(CVSBUNDLE.getString("idDaPrendere")));
        csv.removeGiornale(new File(LOCATION),g);
    }



}
