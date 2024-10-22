package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.model.TempUser;
import laptop.model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerUtenti {

    private final CsvUtente csv;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private TempUser tu;
    public ObservableList<TempUser> getList() throws SQLException, CsvValidationException, IOException {
        ObservableList<TempUser> list= FXCollections.observableArrayList();
        if(vis.getTypeOfDb().equals("db"))
            list= UsersDao.getUserList();
        else list=csv.getUserData();
        return list;
    }

    public ControllerUtenti()
    {
        csv=new CsvUtente();
        tu=new TempUser();
    }

    public boolean elimina(String emailT) throws SQLException, CsvValidationException, IOException {
        boolean status = false;


            if(vis.getTypeOfDb().equals("db"))
            {

                tu.setId(vis.getId());
                User.getInstance().setId(tu.getId());
               status=UsersDao.deleteTempUser(tu);
            }
           else
            {
                tu.setId(vis.getId());
                System.out.println("id nel file :" + tu.getId());
                User.getInstance().setId(tu.getId());
                User.getInstance().setEmail(emailT);
                System.out.println("id id user :" + User.getInstance().getId());
                System.out.println("email :" + User.getInstance().getEmail());

                status=csv.removeUserByIdEmailPwd(User.getInstance());
            }

        return status;
    }


}
