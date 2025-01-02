package web.ultimiTest;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Raccolta;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import web.bean.GiornaleBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestGiornaleBean {
    private static final ResourceBundle RBGIORNALE=ResourceBundle.getBundle("configurations/objects");
    private static final GiornaleBean gB=new GiornaleBean();

    @Test
     void testSettersGetters() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {


        String titoloB=RBGIORNALE.getString("titoloModG");
         String tipologiaB=RBGIORNALE.getString("categoriaModG");
         String linguaB=RBGIORNALE.getString("linguaModG");
         String editoreB=RBGIORNALE.getString("editoreModG");
         Date dataB=new Date(20250711);
         int copieRimanentiB=Integer.parseInt(RBGIORNALE.getString("nrCopieModG"));
         int disponibilitaB=Integer.parseInt(RBGIORNALE.getString("dispModG"));
         float prezzoB=Float.parseFloat(RBGIORNALE.getString("prezzoModGI"));

        PropertyUtils.setProperty(gB,"titoloB",titoloB);
        PropertyUtils.setProperty(gB,"tipologiaB",tipologiaB);
        PropertyUtils.setProperty(gB,"linguaB",linguaB);
        PropertyUtils.setProperty(gB,"editoreB",editoreB);
        PropertyUtils.setProperty(gB,"dataB",dataB);
        PropertyUtils.setProperty(gB,"copieRimanentiB",copieRimanentiB);
        PropertyUtils.setProperty(gB,"disponibilitaB",disponibilitaB);
        PropertyUtils.setProperty(gB,"prezzoB",prezzoB);

        assertNotEquals("",PropertyUtils.getProperty(gB,"titoloB"));

    }
}
