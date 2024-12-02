package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerAggiornaPassword {

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final User u= User.getInstance();
    private final CsvUtente csv;
    public String getEmail() throws SQLException, CsvValidationException, IOException {
        String mail;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {
            mail= UsersDao.pickData(u).getEmail();
        }
        else{
            mail=csv.userList(u).get(0).getEmail();
        }
        return mail;
    }
    public String getPass() throws SQLException, CsvValidationException, IOException {
        String pass;

        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {
            pass= UsersDao.pickData(u).getPassword();
        }
        else{
            pass=csv.userList(u).get(0).getPassword();
        }
        return pass;
    }
    public ControllerAggiornaPassword()
    {
        csv=new CsvUtente();
    }

    public boolean aggiorna( String nuovaP) throws SQLException, CsvValidationException, IOException, IdException {
        boolean status;
        u.setPassword(nuovaP);

        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {
            status=UsersDao.checkResetpass(u);
        }
        else {
            User user=csv.userList(u).get(0);
            csv.removeUserByIdEmailPwd(user);
            u.setPassword(nuovaP);
            status=csv.inserisciUtente(u);
        }
        return status;
    }
}
