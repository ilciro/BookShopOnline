package web.ultimiTest;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.bean.LibroBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestLibroBean {
     private static final ResourceBundle RBLIBRO=ResourceBundle.getBundle("configurations/objects");
     private static final LibroBean lB=new LibroBean();

     @ParameterizedTest
     @ValueSource(strings = {"ADOLESCENTI_RAGAZZI","ARTE","CINEMA_FOTOGRAFIA","BIOGRAFIE","DIARI_MEMORIE","CALENDARI_AGENDE","DIRITTO","DIZINARI_OPERE","ECONOMIA","FAMIGLIA","FANTASCIENZA_FANTASY","FUMETTI_MANGA","GIALLI_THRILLER","COMPUTER_GIOCHI","HUMOR","INFORMATICA","WEB_DIGITAL_MEDIA","LETTERATURA_NARRATIVA","LIBRI_BAMBINI","LIBRI_SCOLASTICI","LIBRI_UNIVERSITARI","RICETTARI_GENERALI","LINGUISTICA_SCRITTURA","POLITICA","RELIGIONE","ROMANZI_ROSA","SCIENZE","TECNOLOGIA_MEDICINA"})
     void testSetterGetter(String strings) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
         String titoloB=RBLIBRO.getString("titoloModL");
         String codIsbnB=RBLIBRO.getString("isbnModL");
         int numeroPagineB=Integer.parseInt(RBLIBRO.getString("numPagModL"));
         String autoreB=RBLIBRO.getString("autoreModL");
         String editoreB=RBLIBRO.getString("editoreModL");
         String linguaB=RBLIBRO.getString("linguaModL");
         java.sql.Date dateB=new Date(20280101);
         String recensioneB=RBLIBRO.getString("recensioneModL");
         String descB=RBLIBRO.getString("descrizioneModL");
         int disponibilitaB=Integer.parseInt(RBLIBRO.getString("dispModL"));
         float prezzoB=Float.parseFloat(RBLIBRO.getString("prezzoModL"));
         int nrCopieB=Integer.parseInt(RBLIBRO.getString("nrCopieModL"));

         PropertyUtils.setProperty(lB,"titoloB",titoloB);
         PropertyUtils.setProperty(lB,"codIsbnB",codIsbnB);
         PropertyUtils.setProperty(lB,"numeroPagineB",numeroPagineB);
         PropertyUtils.setProperty(lB,"autoreB",autoreB);
         PropertyUtils.setProperty(lB,"editoreB",editoreB);
         PropertyUtils.setProperty(lB,"linguaB",linguaB);
         PropertyUtils.setProperty(lB,"categoriaB",strings);
         PropertyUtils.setProperty(lB,"dateB",dateB);
         PropertyUtils.setProperty(lB,"recensioneB",recensioneB);
         PropertyUtils.setProperty(lB,"descB",descB);
         PropertyUtils.setProperty(lB,"disponibilitaB",disponibilitaB);
         PropertyUtils.setProperty(lB,"prezzoB",prezzoB);
         PropertyUtils.setProperty(lB,"nrCopieB",nrCopieB);
         assertEquals(RBLIBRO.getString("titoloModL"),PropertyUtils.getProperty(lB,"titoloB"));


     }


}
