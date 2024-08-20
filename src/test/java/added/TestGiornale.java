package added;

import laptop.database.GiornaleDao;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.Test;
import web.bean.GiornaleBean;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestGiornale {
    private final Giornale g=new Giornale();
    private final GiornaleBean gB=new GiornaleBean();
    private final GiornaleDao gD=new GiornaleDao();

    @Test
    void setterList()
    {
        gB.setListaGiornaliB(gD.getGiornali());
        assertNotNull(gB.getListaGiornaliB());

    }
    @Test
    void setters()
    {
        g.setId(2);
        Giornale g2=gD.getData(g);
        gB.setIdB(g2.getId());
        gB.setTipologiaB(g2.getTipologia());
        gB.setTitoloB(g2.getTitolo());
        gB.setDataB(Date.valueOf(g2.getDataPubb()));
        assertNotEquals(0,gB.getIdB());
    }
}
