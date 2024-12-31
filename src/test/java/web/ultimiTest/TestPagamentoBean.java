package web.ultimiTest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.bean.PagamentoBean;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestPagamentoBean {

     private static final PagamentoBean pB=new PagamentoBean();

     @ParameterizedTest
     @ValueSource(strings = {"cash","cCredito"})
     void testSetterGetters(String strings) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

         float ammontareB=14.62f;
         int esitoB=1;
         String nomeUtenteB="alfredo";
         String tipoB="INFORMATICA";
         String emailB="alfredoIndaco@gmail.com";

         PropertyUtils.setProperty(pB,"metodoB",strings);
         PropertyUtils.setProperty(pB,"ammontareB",ammontareB);
         PropertyUtils.setProperty(pB,"esitoB",esitoB);
         PropertyUtils.setProperty(pB,"nomeUtenteB",nomeUtenteB);
         PropertyUtils.setProperty(pB,"tipoB",tipoB);
         PropertyUtils.setProperty(pB,"emailB",emailB);

         assertNotEquals("",PropertyUtils.getProperty(pB,"emailB"));




     }


}
