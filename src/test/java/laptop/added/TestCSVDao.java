package laptop.added;

import laptop.database.CsvDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestCSVDao {
    private static final CsvDao csv=new CsvDao();
    private static final String LOCATION="localDBFile.csv";

    @BeforeAll
     static void testGeneraReport() throws IOException {
        csv.generaReport();
    }

    @Test
    void testRetrieveDataUser() throws Exception {
        File file=new File(LOCATION);
        assertNotNull(CsvDao.retreiveAllDataUser(file,"admin@admin.com"));
    }

    @Test
    void testRetrieveDataUserNomeEmail() throws Exception {
        File file=new File(LOCATION);
        assertNotNull(CsvDao.retreiveByNomeEmail(file,"admin","admin@admin.com"));
    }


}
