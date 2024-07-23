/*package laptop.added;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.csv.CsvGiornaleDao;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestCsvDaoGiornale {
    private static final String LOCATION="report/reportGiornali.csv";
    private  final CsvGiornaleDao csv= new CsvGiornaleDao();
    private static final Giornale g=new Giornale();


    public TestCsvDaoGiornale() throws IOException {
    }

    @Test
    void testGiornaleByIdAutoreTitolo() throws CsvValidationException, IOException {
        g.setTitolo("La Republica1");
        g.setEditore("La Republica");
        g.setId(1);
        assertNotNull(csv.giornaliByIdTitoloEd(new File(LOCATION),g.getTitolo(),g.getEditore()));
    }
}


 */