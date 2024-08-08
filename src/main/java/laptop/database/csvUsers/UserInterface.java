package laptop.database.csvUsers;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.User;

import java.io.File;
import java.io.IOException;

public interface UserInterface {
    boolean inserisciUtente(User u) throws IOException, CsvValidationException;
    //idUSer is id ;
    //id is from vis
    User getUserList(File fd,int id,String mail,String pwd) throws CsvValidationException, IOException;
    void modificaUser(File fd,User u1,User u2) throws CsvValidationException, IOException;
    int getUserListNum(File fd,String mail,String pwd) throws CsvValidationException,IOException;
}
