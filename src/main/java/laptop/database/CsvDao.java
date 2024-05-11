package laptop.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import laptop.exception.IdException;
import laptop.model.User;
import laptop.utilities.ConnToDb;
import org.apache.commons.lang.SystemUtils;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.*;
public class CsvDao implements DaoInterface {
    private static final String CSVFILENAME="localDBFile.csv";

    private static final int GETINDEXID=0;
    private static final int GETINDEXRUOLO=1;
    private static final int GETINDEXNOME=2;
    private static final int GETINDEXCOGNOME=3;
    private static final int GETINDEXEMAIL=4;
    private static final int GETINDEXDESCRIZIONE=5;
    private static final int GETINDEXDATA=6;
    private static final String query=" select idUser,idRuolo,nome,cognome,email,pwd,descrizione,dataNascita from USERS";






    private final File fd;



    public CsvDao()  {
        this.fd = new File(CSVFILENAME);



    }



    @Override
    public void generaReport() throws IOException {

        try {
            if (!fd.exists()) {
                throw new IOException();
            }
        }catch (IOException e)
        {
            Logger.getLogger("genera report").log(Level.SEVERE, "\n file not ecists");



                if (fd.createNewFile())
                {


                    Logger.getLogger("report users").log(Level.SEVERE, "\n making file");




                    try (Connection conn= ConnToDb.connectionToDB();
                         PreparedStatement prepQ=conn.prepareStatement(query)){

                        ResultSet rs = prepQ.executeQuery(query);
                        CSVWriter writer = new CSVWriter(new FileWriter(CSVFILENAME));
                        rs.getMetaData();


                        String[] data =new String[7];
                        while (rs.next())
                        {
                            data[0]= String.valueOf((rs.getInt(1)));
                            data[1]=rs.getString(2);
                            data[2]=rs.getString(3);
                            data[3]=rs.getString(4);
                            data[4]=rs.getString(5);
                            data[5]=rs.getString(7);
                            data[6]= String.valueOf(rs.getDate(8));
                            writer.writeNext(data);
                        }
                        writer.flush();
                        writer.close();


                    } catch (SQLException ex) {
                        Logger.getLogger("report libro").log(Level.SEVERE, "\n eccezione ottenuta .", ex);

                    }


                }

        }

    }
    public static synchronized void saveUser(File fd, User instance) throws Exception {
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)));
        String[] userVector = new String[8];

        userVector[GETINDEXID] = String.valueOf(instance.getId());
        userVector[GETINDEXRUOLO] = instance.getIdRuolo().substring(0,1);
        userVector[GETINDEXNOME] = instance.getNome();
        userVector[GETINDEXCOGNOME] = String.valueOf(instance.getCognome());
        userVector[GETINDEXEMAIL] = String.valueOf(instance.getEmail());
        userVector[GETINDEXDESCRIZIONE] = String.valueOf(instance.getDescrizione());
        userVector[GETINDEXDATA] = String.valueOf(instance.getDataDiNascita());

        csvWriter.writeNext(userVector);
        csvWriter.flush();
        csvWriter.close();


    }
    public static synchronized List<User> retreiveByNomeEmail(File fd, String nome,String email) throws Exception {
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] userVector;

        List<User> userList = new ArrayList<>();

        while ((userVector = csvReader.readNext()) != null) {

            boolean userVectorFound = (userVector[GETINDEXNOME].equals(nome))||(userVector[GETINDEXEMAIL].equals(email));
            if (userVectorFound) {
                int id = Integer.parseInt(userVector[GETINDEXID]);
                String nomeA = userVector[GETINDEXNOME];
                String emailA = userVector[GETINDEXEMAIL];


                User.getInstance().setId(id);
                User.getInstance().setNome(nomeA);
                User.getInstance().setEmail(emailA);
                userList.add(User.getInstance());
            }
        }

        csvReader.close();

        if (userList.isEmpty()) {
            throw new NullPointerException(" user not found");
        }

        return userList;
    }
    private static synchronized void removeUserById(File fd, User instance) throws Exception {
        File tmpFD = File.createTempFile("dao", "tmp",new File(CSVFILENAME));


if(SystemUtils.IS_OS_UNIX) {
  FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
  Files.createTempFile("prefix", "suffix", attr); // Compliant
}


        boolean found = false;

        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] userVector;

        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tmpFD, true)));

        while ((userVector = csvReader.readNext()) != null) {

            boolean userVectorFound = (userVector[GETINDEXID].equals(String.valueOf(instance.getId()))||(userVector[GETINDEXEMAIL].equals(String.valueOf(instance.getEmail()))));
            if (!userVectorFound) {
                csvWriter.writeNext(userVector);
            } else {
                found = true;
            }
        }
        csvWriter.flush();

        csvReader.close();
        csvWriter.close();

        if (found) {
            Files.move(tmpFD.toPath(), fd.toPath(), REPLACE_EXISTING);
        } else {
            cleanUp(Path.of(tmpFD.toURI()));
        }
    }
    public static synchronized void modifPassUser(File fd, User instance,User instanceA) throws Exception {
        //modified only email because pass not showed

        //instance for delete
        // instance agg fro insert

        removeUserById(fd,instance);
        saveUser(fd,instanceA);


    }
    public static synchronized List<User> retreiveAllDataUser(File fd,String email) throws Exception {
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] userVector;

        List<User> userList = new ArrayList<>();

        while ((userVector = csvReader.readNext()) != null) {

            boolean userVectorFound = userVector[GETINDEXEMAIL].equals(email);
            if (userVectorFound) {
                int id = Integer.parseInt(userVector[GETINDEXID]);
                String ruolo=userVector[GETINDEXRUOLO];
                String nome = userVector[GETINDEXNOME];
                String cognome=userVector[GETINDEXCOGNOME];
                String emailA = userVector[GETINDEXEMAIL];
                String desc=userVector[GETINDEXDESCRIZIONE];
                String data=userVector[GETINDEXDATA];


                User.getInstance().setId(id);
                User.getInstance().setIdRuolo(ruolo);
                User.getInstance().setNome(nome);
                User.getInstance().setCognome(cognome);
                User.getInstance().setEmail(emailA);
                User.getInstance().setDescrizione(desc);
                User.getInstance().setDataDiNascita(LocalDate.parse(data));
                userList.add(User.getInstance());
            }
        }

        csvReader.close();

        if (userList.isEmpty()) {
            throw new IdException(" user not found -> id null");
        }

        return userList;
    }




    public static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }


}
