package added;

import laptop.database.NegozioDao;
import laptop.model.Negozio;
import org.junit.jupiter.api.Test;
import web.bean.NegozioBean;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

 class TestNegozio {

    private final Negozio n=new Negozio();
    private final NegozioBean nB=new NegozioBean();

    private final NegozioDao nD=new NegozioDao();


    //here is mixed between desk and laptop
    // this shop is shop A

    @Test
    void setters() throws SQLException {
        n.setNome("Negozio A");
        nD.setOpen(n,false);
        nD.setRitiro(n,true);

        nB.setOpenB(nD.checkOpen(n));
        nB.setValidB(nD.checkRitiro(n));

        assertTrue(nB.isValidB());



    }


}
