package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerAdmin {
    private final CsvUtente csv;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    public boolean logout(String type) throws SQLException, CsvValidationException, IOException {
        boolean stastus = false;
        if(type.equals("db"))
        {
            UsersDao.pickData(User.getInstance());


                if(User.getInstance().annullaUtente())
                {
                    stastus=true;
                    vis.setIsLogged(false);

                }
        }
        else {
            if(!csv.userList(User.getInstance()).get(0).getEmail().isEmpty())
            {
               if( User.getInstance().annullaUtente())
                   stastus=true;
               vis.setIsLogged(false);
            }

        }
        return stastus;
    }

    public ControllerAdmin()
    {
        csv=new CsvUtente();
    }
}
