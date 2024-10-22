package laptop.database.csvusers;

import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
import laptop.model.TempUser;
import laptop.model.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UserInterface {
    boolean inserisciUtente(User u) throws IOException, CsvValidationException, IdException;
    List<User> userList( User u) throws CsvValidationException, IOException, IdException;
    boolean removeUserByIdEmailPwd(User u) throws CsvValidationException, IOException;
}
