package added;

import laptop.database.LibroDao;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.api.Test;
import web.bean.LibroBean;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestLibro {
    private final LibroDao lD=new LibroDao();
    private final Libro l=new Libro();
    private final LibroBean lB=new LibroBean();
    private final ResourceBundle rBCategories=ResourceBundle.getBundle("configurations/bookCategories");

    public TestLibro() throws IOException {
    }

    @Test
    void testSetter()
    {
        l.setId(19);
        Libro l1=lD.getData(l);

        lB.setTitoloB(l1.getTitolo());
        lB.setNumeroPagineB(l1.getNrPagine());
        lB.setCodIsbnB(l1.getCodIsbn());
        lB.setEditoreB(l1.getEditore());
        lB.setAutoreB(l1.getAutore());
        lB.setCategoriaB(l1.getCategoria());
        lB.setDateB(Date.valueOf(l1.getDataPubb()));
        lB.setRecensioneB(l1.getRecensione());
        lB.setNrCopieB(l1.getNrCopie());
        lB.setDescB(l1.getDesc());
        lB.setDisponibilitaB(l1.getDisponibilita());
        lB.setPrezzoB(l1.getPrezzo());
        lB.setLinguaB(l1.getLingua());
        lB.setIdB(l1.getId());

        assertNotEquals(0,lB.getIdB());



    }

    @Test
    void setCategories()
    {
        Enumeration<String> systemKeys = rBCategories.getKeys();

        while (systemKeys.hasMoreElements()) {
            String key =  systemKeys.nextElement();
            l.setCategoria(key);

            lB.setCategoriaB(l.getCategoria());
        }

        assertNotNull(systemKeys);
    }
    @Test
     void testIncrementaDisp() throws SQLException {
        Date data=new Date(2024,1,2);
        l.setId(1);
        lD.aggiornaData(l,data);
        assertNotEquals(0,l.getId());
    }




}
