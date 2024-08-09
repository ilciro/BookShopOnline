package laptop.database.csvUsers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.model.User;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvUtente implements UserInterface{

    private static final int GETINDEXIDUSER=0;
    private static final int GETINDEXRUOLO=1;
    private static final int GETINDEXNOME=2;
    private static final int GETINDEXCOGNOME=3;
    private static final int GETINDEXEMAIL=4;
    private static final int GETINDEXPWD=5;
    private static final int GETINDEXDESC=6;
    private static final int GETINDEXDATAN=7;
    private static final String LOCATIONU="report/reportUtente.csv";

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public boolean inserisciUtente(User u) throws IOException, CsvValidationException {
       return insertUser(u);
    }

    @Override
    public User getUserList(File fd, int id, String email,String pwd) throws CsvValidationException, IOException {

      return getUserData(fd, id, email, pwd);


    }

    @Override
    public void modificaUser(File fd,User u1,User u2) throws CsvValidationException, IOException {


                cancellaUserById(fd,u1);



                inserisciUtente(u2);



    }

    @Override
    public int getUserListNum(File fd, String mail, String pwd) throws CsvValidationException, IOException {
       AtomicInteger i= new AtomicInteger();
        new Thread(()->{
            try {
                i.set(getNumOfUser(fd, mail, pwd));
            }catch (IOException | CsvValidationException e)
            {
                e.getMessage();
            }
        }).start();

        return i.get();
    }


    private static synchronized boolean insertUser(User u) throws IOException, CsvValidationException {
        CSVWriter writer=new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONU,true)));
        String[] gVector =new String[8];
        boolean state=false;

        gVector[GETINDEXIDUSER]= String.valueOf(getIdMax()+1);
        gVector[GETINDEXRUOLO]=u.getIdRuolo();
        gVector[GETINDEXNOME]=u.getNome();
        gVector[GETINDEXCOGNOME]=u.getCognome();
        gVector[GETINDEXEMAIL]=u.getEmail();
        gVector[GETINDEXPWD]=u.getPassword();
        gVector[GETINDEXDESC]=u.getDescrizione();
        gVector[GETINDEXDATAN]= String.valueOf(u.getDataDiNascita());
        writer.writeNext(gVector);
        writer.flush();
        writer.close();


        if(u.getEmail()!=null)
            state=true;
        return state;


    }

    private static synchronized User getUserData(File fd,int id,String mail,String pass) throws CsvValidationException,IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fd)));
        String []gVector ;
        boolean recordFound = false;

        while ((gVector = reader.readNext()) != null) {
            if(id>0)
                recordFound=gVector[GETINDEXIDUSER].equals(String.valueOf(id));
            if(mail!=null)
                if(pass!=null)
                    recordFound=gVector[GETINDEXEMAIL].equals(mail) && gVector[GETINDEXPWD].equals(pass);
                else recordFound=gVector[GETINDEXEMAIL].equals(mail);
            if(recordFound)
            {
                String idU=gVector[GETINDEXIDUSER];
                String ruoloU=gVector[GETINDEXRUOLO];
                String nome=gVector[GETINDEXNOME];
                String cognome=gVector[GETINDEXCOGNOME];
                String email=gVector[GETINDEXEMAIL];
                String pwd=gVector[GETINDEXPWD];
                String desc=gVector[GETINDEXDESC];
                String data=gVector[GETINDEXDATAN];

                User.getInstance().setId(Integer.parseInt(idU));
                User.getInstance().setIdRuolo(ruoloU);
                User.getInstance().setNome(nome);
                User.getInstance().setCognome(cognome);
                User.getInstance().setEmail(email);
                User.getInstance().setPassword(pwd);
                User.getInstance().setDescrizione(desc);
                User.getInstance().setDataDiNascita(LocalDate.parse(data));

            }

        }
        return User.getInstance();
    }

    private static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader = new CSVReader(new FileReader(LOCATIONU));
        String[] gVector;
        int id = 0;
        while ((gVector = reader.readNext()) != null)
            id = Integer.parseInt(gVector[GETINDEXIDUSER]);

        return id;

    }
    private static synchronized  void cancellaUserById(File fd,User u1) throws IOException, CsvValidationException {
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
            Files.createTempFile("prefix", "suffix", attr); // Compliant
        }
        File tmpFD = new File("report/appoggioUser.csv");
        boolean found = false;
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] giornaleVector;
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tmpFD, true)));
        //check on path
        boolean userVectorFound = false;


        while ((giornaleVector = csvReader.readNext()) != null) {

                userVectorFound = giornaleVector[GETINDEXIDUSER].equals(String.valueOf(u1.getId())) || giornaleVector[GETINDEXEMAIL].equals(u1.getEmail());

            }
            if (!userVectorFound) {
                csvWriter.writeNext(giornaleVector);
            } else {
                found = userVectorFound;
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
    private static synchronized int getNumOfUser(File fd,String mail,String pass) throws CsvValidationException,IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fd)));
        String []gVector ;
        boolean recordFound;
        int found=0;
        while ((gVector = reader.readNext()) != null) {


             recordFound=gVector[GETINDEXEMAIL].equals(mail) && gVector[GETINDEXPWD].equals(pass);
            if(recordFound)
            {
                String idU=gVector[GETINDEXIDUSER];
                String ruoloU=gVector[GETINDEXRUOLO];
                String nome=gVector[GETINDEXNOME];
                String cognome=gVector[GETINDEXCOGNOME];
                String email=gVector[GETINDEXEMAIL];
                String pwd=gVector[GETINDEXPWD];
                String desc=gVector[GETINDEXDESC];
                String data=gVector[GETINDEXDATAN];

                User.getInstance().setId(getIdMax()+1);
                User.getInstance().setIdRuolo(ruoloU);
                User.getInstance().setNome(nome);
                User.getInstance().setCognome(cognome);
                User.getInstance().setEmail(email);
                User.getInstance().setPassword(pwd);
                User.getInstance().setDescrizione(desc);
                User.getInstance().setDataDiNascita(LocalDate.parse(data));

                found=1;
            }

        }
        return found;
    }


}
