package web.ultimiTest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.bean.RivistaBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestRivistaBean {

    private static final RivistaBean rB=new RivistaBean();
    private static final ResourceBundle RBRIVISTA=ResourceBundle.getBundle("configurations/objects");

    @ParameterizedTest
    @ValueSource(strings = {"SETTIMANALE","BISETTIMANALE","MENSILE","BIMESTRALE","TRIMESTRALE","ANNUALE","ESTIVO","INVERNALE","SPORTIVO","CINEMATOGRAFICA","GOSSIP","TELEVISIVO","MILITARE","INFORMATICA"})
    void testSettersGetters(String strings) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String titoloB=RBRIVISTA.getString("titoloModR");
        String autore=RBRIVISTA.getString("autoreModR");
        String linguaB=RBRIVISTA.getString("linguaModR");
        String editoreB=RBRIVISTA.getString("editoreModR");
        String descrizioneB=RBRIVISTA.getString("descrizioneModR");
        Date dataB=new Date(20251118);
        float prezzoB=Float.parseFloat(RBRIVISTA.getString("prezzoModR"));
        int copieRimB=Integer.parseInt(RBRIVISTA.getString("nrCopieModR"));
        int dispB=Integer.parseInt(RBRIVISTA.getString("dispModR"));

        PropertyUtils.setProperty(rB,"titoloB",titoloB);
        PropertyUtils.setProperty(rB,"tipologiaB", strings);
        PropertyUtils.setProperty(rB,"autoreB", autore);
        PropertyUtils.setProperty(rB,"linguaB", linguaB);
        PropertyUtils.setProperty(rB,"editoreB", editoreB);
        PropertyUtils.setProperty(rB,"descrizioneB", descrizioneB);
        PropertyUtils.setProperty(rB,"dataB", dataB);
        PropertyUtils.setProperty(rB,"prezzoB", prezzoB);
        PropertyUtils.setProperty(rB,"copieRimB", copieRimB);
        PropertyUtils.setProperty(rB,"dispB", dispB);
        assertNotEquals("",PropertyUtils.getProperty(rB,"titoloB"));
    }

}
