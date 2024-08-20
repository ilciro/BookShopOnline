package added;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.Test;
import web.bean.RicercaBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestRicerca {
    private final LibroDao lD=new LibroDao();
    private final RicercaBean rB=new RicercaBean();

    public TestRicerca() throws IOException {
    }
    private final GiornaleDao gD=new GiornaleDao();
    private final RivistaDao rD=new RivistaDao();

    @Test
    void setListaLibri(){
        rB.setListaB(lD.getLibri());
        assertNotNull(rB.getListaB());
    }
    @Test
    void setListaGiornali(){
        rB.setListaB(gD.getGiornali());
        assertNotNull(rB.getListaB());
    }
    @Test
    void setListaRivista(){
        rB.setListaB(rD.getRiviste());
        assertNotNull(rB.getListaB());
    }

    @Test
    void setTitolo()
    {
        Libro l=new Libro();
        l.setId(5);
        rB.setTitoloB(lD.getData(l).getTitolo());
        assertNotNull(rB.getTitoloB());
    }
    @Test
    void setEditore()
    {
        Giornale g=new Giornale();
        g.setId(1);
        rB.setEditoreB(gD.getData(g).getEditore());
        assertNotNull(rB.getEditoreB());
    }
    @Test
    void setAutore()
    {
        Rivista r=new Rivista();
        r.setId(1);
        rB.setAutoreB(rD.getData(r).getAutore());
        assertNotNull(rB.getAutoreB());
    }


}
