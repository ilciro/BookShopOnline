package laptop.database.csvusers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
import laptop.model.User;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.util.*;

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
    private final HashMap<String, User> cacheU;
    private final File fdU=new File(LOCATIONU);




    public CsvUtente()
    {
        this.cacheU=new HashMap<>();
    }

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public boolean inserisciUtente(User u) throws IOException, CsvValidationException, IdException {

        boolean state=false;

        boolean duplicated;
        synchronized (this.cacheU) {
            boolean duplicatedM = (this.cacheU.get(u.getEmail()) != null);
            boolean duplicatedP = (this.cacheU.get(u.getPassword()) != null);
            duplicated = duplicatedM && duplicatedP;

        }
        if(!duplicated)
        {
            List<User> list=getUserData(this.fdU,u.getId(), u.getEmail(), u.getPassword());
            duplicated=(!list.isEmpty());
            state=true;
        }
        if(duplicated)

            throw new IdException(" user is in db!!");

        insertUser(u);
        return state;

    }

    @Override
    public List<User> userList(File fd,User u) throws CsvValidationException, IOException {
        List<User> list=new ArrayList<>();
        synchronized (this.cacheU)
        {
            for(Map.Entry <String,User> entry:this.cacheU.entrySet())
            {
                User recordInCache=this.cacheU.get(entry.getKey());
                boolean recordP=recordInCache.getEmail().equals(u.getEmail());
                boolean recordM=recordInCache.getPassword().equals(u.getPassword());
                boolean recordFound=recordP&&recordM;
                if(recordFound)
                    list.add(recordInCache);
            }
        }
        if(list.isEmpty())
        {
            list=getUserData(this.fdU,u.getId(),u.getEmail(),u.getPassword());
            synchronized (this.cacheU)
            {
                for(User user : list)
                    this.cacheU.put(String.valueOf(u.getId()),user);
            }

        }
        return list;
    }

    @Override
    public void removeUserByIdEmailPwd(User u) throws CsvValidationException, IOException {
        synchronized (this.cacheU)
        {
            this.cacheU.remove(u.getEmail());
        }
        cancellaUserById(this.fdU,u);
    }




    private static synchronized void insertUser(User u) throws IOException, CsvValidationException {
        CSVWriter writer=new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONU,true)));
        String[] gVector =new String[8];

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




    }

    private static synchronized List<User> getUserData(File fd, int id, String mail, String pass) throws CsvValidationException,IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fd)));
        String []gVector ;
        boolean recordFound = false;
        List<User> list=new ArrayList<>();


        while ((gVector = reader.readNext()) != null) {
            if(id>0)
                recordFound=gVector[GETINDEXIDUSER].equals(String.valueOf(id));
            if(mail!=null) {
                if (pass != null)
                    recordFound = gVector[GETINDEXEMAIL].equals(mail) && gVector[GETINDEXPWD].equals(pass);
                else recordFound = gVector[GETINDEXEMAIL].equals(mail);
            }
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

                list.add(User.getInstance());
            }

        }
        return list;
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
        boolean userVectorFound ;


        while ((giornaleVector = csvReader.readNext()) != null) {

            userVectorFound = giornaleVector[GETINDEXIDUSER].equals(String.valueOf(u1.getId())) || giornaleVector[GETINDEXEMAIL].equals(u1.getEmail());


            if (!userVectorFound) {
                csvWriter.writeNext(giornaleVector);
            } else {
                found = userVectorFound;
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
}
