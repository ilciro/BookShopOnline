package web.ultimiTest;

import laptop.database.CartaCreditoDao;
import laptop.model.CartaDiCredito;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import web.bean.CartaCreditoBean;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestCartaCreditoBean {
     private static final ResourceBundle RBCARTA=ResourceBundle.getBundle("configurations/cartaCredito");
     private static final CartaCreditoBean cCB=new CartaCreditoBean();

     @Test
    void testSettersGetters() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
         PropertyUtils.setProperty(cCB,"nomeB",RBCARTA.getString("nome"));
         PropertyUtils.setProperty(cCB,"cognomeB",RBCARTA.getString("cognome"));
         PropertyUtils.setProperty(cCB,"civB",RBCARTA.getString("civ1"));
         PropertyUtils.setProperty(cCB,"numeroCCB",RBCARTA.getString("codice1"));
         PropertyUtils.setProperty(cCB,"prezzoTransazioneB",Float.parseFloat(RBCARTA.getString("prezzo1")));
         java.util.Date date=new Date();
         PropertyUtils.setProperty(cCB,"dataScadB",date);
         assertNotEquals("",PropertyUtils.getProperty(cCB,"numeroCCB"));
     }

}
