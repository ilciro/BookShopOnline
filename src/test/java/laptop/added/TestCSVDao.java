package laptop.added;

import laptop.database.CsvDao;
import laptop.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestCSVDao {
    private static final CsvDao csv=new CsvDao();
    private static final String LOCATION="localDBFile.csv";
    private static final ResourceBundle CVSBUNDLE=ResourceBundle.getBundle("configurations/users");

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
    @Test
     void testSaveUser() throws Exception {
        LocalDate ld=LocalDate.of(1998,5,2);
        User.getInstance().setId(Integer.parseInt(CVSBUNDLE.getString("id")));
        User.getInstance().setIdRuolo(CVSBUNDLE.getString("ruolo"));
        User.getInstance().setNome(CVSBUNDLE.getString("nome"));
        User.getInstance().setCognome(CVSBUNDLE.getString("cognome"));
        User.getInstance().setEmail(CVSBUNDLE.getString("email"));
        User.getInstance().setDataDiNascita(ld);
        User.getInstance().setDescrizione(CVSBUNDLE.getString("desc"));
        CsvDao.saveUser(new File(LOCATION),User.getInstance());
    }


}
