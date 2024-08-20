package added;

import laptop.database.UsersDao;
import laptop.model.TempUser;
import laptop.model.User;
import org.junit.jupiter.api.Test;
import web.bean.UserBean;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestTempUser {
    private final TempUser tU= TempUser.getInstance();
    private final User u=User.getInstance();
    private final UserBean uB=UserBean.getInstance();


    @Test
    void testPickData() throws SQLException {
        u.setId(1);
        tU.setId(UsersDao.pickData(u).getId());
        tU.setNomeT(UsersDao.pickData(u).getNome());
        tU.setCognomeT(UsersDao.pickData(u).getCognome());
        tU.setPasswordT(UsersDao.pickData(u).getPassword());
        tU.setDescrizioneT(UsersDao.pickData(u).getDescrizione());
        tU.setDataDiNascitaT(UsersDao.pickData(u).getDataDiNascita());

        uB.setIdB(tU.getId());
        uB.setNomeB(tU.getNomeT());
        uB.setCognomeB(tU.getCognomeT());
        uB.setDescrizioneB(tU.getDescrizioneT());
        uB.setDataDiNascitaB(tU.getDataDiNascitaT());

        assertNotNull(tU);


    }
    @Test
    void testGetLista() throws SQLException {
        assertNotNull(UsersDao.getUserList());
    }

    @Test
    void testGetTempUser() throws SQLException {
        tU.setId(1);
        assertNotNull(UsersDao.getTempUserSingolo(tU));
    }


}
