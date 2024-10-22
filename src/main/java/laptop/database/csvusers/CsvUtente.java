package laptop.database.csvusers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.TempUser;
import laptop.model.User;
import org.apache.commons.lang.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        }
        if(duplicated) {
            try {
                Logger.getLogger("try user").log(Level.INFO, "id sbagliato !!");
                throw new IdException(" id user sbagliato or duplicated");

            } catch (IdException e) {
                Logger.getLogger("catch utente").log(Level.SEVERE, "remove utente...");
                //rimuovo e se lista vuota
                removeUserByIdEmailPwd(u);
            }
        }

        return insertUser(u);


    }

    @Override
    public List<User> userList(User u) throws CsvValidationException, IOException {
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
    public boolean removeUserByIdEmailPwd(User u) throws CsvValidationException, IOException {
        synchronized (this.cacheU)
        {
            this.cacheU.remove(u.getEmail());
        }
        return cancellaUserById(this.fdU,u);
    }




    private static synchronized boolean insertUser(User u) throws IOException, CsvValidationException {
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

        return getIdMax()!=0;



    }

    private static synchronized List<User> getUserData( File fd,int id, String mail, String pass) throws IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fd)));
        String []gVector ;
        boolean recordFound ;
        List<User> list=new ArrayList<>();


        while ((gVector = reader.readNext()) != null) {



                recordFound = gVector[GETINDEXIDUSER].equals(String.valueOf(id)) || gVector[GETINDEXEMAIL].equals(mail) || gVector[GETINDEXPWD].equals(pass);

                if (recordFound) {

                    TempUser tu = getTempUser(gVector);

                    User.getInstance().setId(tu.getId());
                    User.getInstance().setIdRuolo(tu.getIdRuolo());
                    User.getInstance().setNome(tu.getNomeT());
                    User.getInstance().setCognome(tu.getCognomeT());
                    User.getInstance().setEmail(tu.getEmailT());
                    User.getInstance().setPassword(tu.getPasswordT());
                    User.getInstance().setDescrizione(tu.getDescrizioneT());
                    User.getInstance().setDataDiNascita(tu.getDataDiNascitaT());


                    list.add(User.getInstance());
                }

            }


        reader.close();
        return list;
    }

    private static @NotNull TempUser getTempUser(String[] gVector) {
        TempUser tu=new TempUser();

        tu.setId(Integer.parseInt(gVector[GETINDEXIDUSER]));
        tu.setIdRuolo(gVector[GETINDEXRUOLO]);
        tu.setNomeT(gVector[GETINDEXNOME]);
        tu.setCognomeT(gVector[GETINDEXCOGNOME]);
        tu.setEmailT(gVector[GETINDEXEMAIL]);
        tu.setPasswordT(gVector[GETINDEXPWD]);
        tu.setDescrizioneT(gVector[GETINDEXDESC]);
        tu.setDataDiNascitaT(LocalDate.parse(gVector[GETINDEXDATAN]));
        return tu;
    }


    private static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader = new CSVReader(new FileReader(LOCATIONU));
        String[] gVector;
        int id = 0;
        while ((gVector = reader.readNext()) != null)
            id = Integer.parseInt(gVector[GETINDEXIDUSER]);
        reader.close();

        return id;

    }
    private static synchronized  boolean cancellaUserById(File fd,User u1) throws IOException, CsvValidationException {
        boolean status=false;
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
            status=true;
        } else {
            cleanUp(Path.of(tmpFD.toURI()));
        }
        return status;

    }

    public  synchronized ObservableList<TempUser> getUserData() throws IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(this.fdU)));
        String []gVector ;

        ObservableList<TempUser> list= FXCollections.observableArrayList();

      

        while ((gVector = reader.readNext()) != null) {

            TempUser tu = getUser(gVector);




                list.add(  tu);


        }


        reader.close();
        return list;
    }

    private static @NotNull TempUser getUser(String[] gVector) {
        TempUser tu=new TempUser();


        // usoil temp user che non e sing
        tu.setId(Integer.parseInt(gVector[GETINDEXIDUSER]));
        tu.setIdRuolo(gVector[GETINDEXRUOLO]);
        tu.setNomeT(gVector[GETINDEXNOME]);
        tu.setCognomeT(gVector[GETINDEXCOGNOME]);
        tu.setEmailT(gVector[GETINDEXEMAIL]);
        tu.setPasswordT(gVector[GETINDEXPWD]);
        tu.setDescrizioneT(gVector[GETINDEXDESC]);
        tu.setDataDiNascitaT(LocalDate.parse(gVector[GETINDEXDATAN]));
        return tu;
    }
}
