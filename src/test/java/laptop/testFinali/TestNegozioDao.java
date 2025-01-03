package laptop.testFinali;

import laptop.database.NegozioDao;
import laptop.model.Negozio;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestNegozioDao {
     private static final NegozioDao nD=new NegozioDao();

     @Test
    void testNegozio() throws SQLException {
         Negozio n=nD.getNegozi().get(3);
         nD.setOpen(n,true);
         nD.setRitiro(n,true);
         assertTrue(nD.checkOpen(n));

     }
}
