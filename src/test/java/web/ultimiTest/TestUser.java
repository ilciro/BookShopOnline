package web.ultimiTest;

import laptop.database.UsersDao;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import web.bean.TempUserBean;
import web.bean.UserBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestUser {
    private static final TempUserBean tUB=new TempUserBean();
    private static final UserBean uB=UserBean.getInstance();

    //user bean si e np sing

    @Test
    void testSetterGetters() throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

         PropertyUtils.setProperty(tUB,"lista",UsersDao.getUserList());





         String emailBNOS=UsersDao.getUserList().get(0).getEmailT();
         String passBNOS=UsersDao.getUserList().get(0).getPasswordT();
         String nomeBNOS=UsersDao.getUserList().get(0).getNomeT();
         String cognomeBNOS=UsersDao.getUserList().get(0).getCognomeT();
         LocalDate dataDiNascitaBNOS=UsersDao.getUserList().get(0).getDataDiNascitaT();

         PropertyUtils.setProperty(tUB,"emailBNOS",emailBNOS);
         PropertyUtils.setProperty(tUB,"passBNOS",passBNOS);
         PropertyUtils.setProperty(tUB,"nomeBNOS",nomeBNOS);
         PropertyUtils.setProperty(tUB,"cognomeBNOS",cognomeBNOS);
         PropertyUtils.setProperty(tUB,"dataDiNascitaBNOS",dataDiNascitaBNOS);

         PropertyUtils.setProperty(uB,"emailB",PropertyUtils.getProperty(tUB,"emailBNOS"));
         PropertyUtils.setProperty(uB,"passB",PropertyUtils.getProperty(tUB,"passBNOS"));
         PropertyUtils.setProperty(uB,"nomeB",PropertyUtils.getProperty(tUB,"nomeBNOS"));
         PropertyUtils.setProperty(uB,"cognomeB",PropertyUtils.getProperty(tUB,"cognomeBNOS"));
         PropertyUtils.setProperty(uB,"dataDiNascitaB",PropertyUtils.getProperty(tUB,"dataDiNascitaBNOS"));

        assertNotNull(PropertyUtils.getProperty(tUB,"lista"));




    }

}
