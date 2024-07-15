package laptop.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
import laptop.model.User;
import laptop.model.raccolta.Giornale;
import laptop.utilities.ConnToDb;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvDaoGiornale implements DaoInterface{

    private static final String CSVFILENAMEGIORNALE="localDBFileGiornale.csv";
    private static final int GETINDEXTITOLO=0;
    private static final int GETINDEXTIPOLOGIA=1;
    private static final int GETINDEXLINGUA=2;
    private static final int GETINDEXEDITORE=3;
    private static final int GETINDEXDATA=4;
    private static final int GETINDEXCOPIER=5;
    private static final int GETINDEXDISP=6;
    private static final int GETINDEXPREZZO=7;
    private static final int GETINDEXID=8;
    private static final String QUERY="select titolo,tipologia,lingua,editore,dataPubblicazione,copieRimanenti,disp,prezzo,idGiornale from GIORNALE";
    private final File fd;

    public CsvDaoGiornale() {
        this.fd = new File(CSVFILENAMEGIORNALE);
    }
    public static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }
    @Override
    public void generaReport() throws IOException {
        try {
            cleanUp(Path.of(fd.toURI()));
        }catch (IOException e)
        {
            Logger.getLogger("genera report").log(Level.SEVERE, "\n file not ecists");
            if (fd.createNewFile())
            {
                Logger.getLogger("report giornale").log(Level.SEVERE, "\n making file");
                try (Connection conn= ConnToDb.connectionToDB();
                     PreparedStatement prepQ=conn.prepareStatement(QUERY)){
                    ResultSet rs = prepQ.executeQuery(QUERY);
                    CSVWriter writer = new CSVWriter(new FileWriter(CSVFILENAMEGIORNALE));
                    rs.getMetaData();
                    String[] data =new String[9];
                    while (rs.next())
                    {
                        data[0]= rs.getString(1);
                        data[1]=rs.getString(2);
                        data[2]=rs.getString(3);
                        data[3]=rs.getString(4);
                        data[4]=String.valueOf(rs.getDate(5));
                        data[5]=String.valueOf(rs.getInt(6));
                        data[6]= String.valueOf(rs.getInt(7));
                        data[7]=String.valueOf(rs.getFloat(8));
                        data[8]=String.valueOf(rs.getInt(9));
                        writer.writeNext(data);
                    }
                    writer.flush();
                    writer.close();
                } catch (SQLException ex) {Logger.getLogger("report giornale").log(Level.SEVERE, "\n eccezione ottenuta .", ex);
                }
            }
        }
    }



    public static synchronized void saveGiornale(File fd, Giornale g) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)));
        String[] gVector = new String[9];

       gVector[GETINDEXTITOLO]=g.getTitolo();
       gVector[GETINDEXTIPOLOGIA]=g.getTipologia();
       gVector[GETINDEXLINGUA]=g.getLingua();
       gVector[GETINDEXEDITORE]=g.getEditore();
       gVector[GETINDEXDATA]=String.valueOf(g.getDataPubb());
       gVector[GETINDEXCOPIER]= String.valueOf(g.getCopieRimanenti());
       gVector[GETINDEXDISP]=String.valueOf(g.getDisponibilita());
       gVector[GETINDEXPREZZO]=String.valueOf(g.getPrezzo());
       gVector[GETINDEXID]=String.valueOf(g.getId());
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();

    }
    public static synchronized List<Giornale> retrieveByIdTitoloEd(File fd,String titolo,String editore ) throws CsvValidationException, IOException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] userVector;
        List<Giornale> giornaleList = new ArrayList<>();
        while ((userVector = csvReader.readNext()) != null) {
            boolean userVectorFound = (userVector[GETINDEXTITOLO].equals(titolo))||(userVector[GETINDEXEDITORE].equals(editore));
            if (userVectorFound) {
                int id = Integer.parseInt(userVector[GETINDEXID]);
                String titoloG = userVector[GETINDEXTITOLO];
                String editoreG = userVector[GETINDEXEDITORE];
                Giornale g=new Giornale();
                g.setId(id);
                g.setTitolo(titoloG);
                g.setEditore(editoreG);
                giornaleList.add(g);
            }
        }
        csvReader.close();
        if (giornaleList.isEmpty()) {
            throw new NullPointerException(" user not found");
        }
        return giornaleList;
    }
    public static synchronized void removeGiornaleById(File fd, Giornale g) throws Exception {
        File tmpFD = File.createTempFile("dao", "tmp");
        if(SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
            Files.createTempFile("prefix", "suffix", attr); // Compliant
        }
        boolean found = false;
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] giornaleVector;
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tmpFD, true)));
        while ((giornaleVector = csvReader.readNext()) != null) {

            boolean userVectorFound = (giornaleVector[GETINDEXID].equals(String.valueOf(g.getId())));
            if (!userVectorFound) {
                csvWriter.writeNext(giornaleVector);
            } else {found = true;}
        }
        csvWriter.flush();
        csvReader.close();
        csvWriter.close();
        if (found) {Files.move(tmpFD.toPath(), fd.toPath(), REPLACE_EXISTING);
        } else {cleanUp(Path.of(tmpFD.toURI()));}
    }

    public static synchronized List<User> retreiveAllDataGiornali(File fd,int id) throws Exception {
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        List<User> gList = new ArrayList<>();
        while ((gVEctor = csvReader.readNext()) != null) {
            boolean userVectorFound = gVEctor[GETINDEXID].equals(String.valueOf(id));
            if (userVectorFound) {

                String titolo=gVEctor[GETINDEXTITOLO];
                String tipologia = gVEctor[GETINDEXTIPOLOGIA];
                String lingua=gVEctor[GETINDEXLINGUA];
                String editore = gVEctor[GETINDEXEDITORE];
                String data=gVEctor[GETINDEXDATA];
                String copie=gVEctor[GETINDEXCOPIER];
                String disp=gVEctor[GETINDEXID];
                String prezzo=gVEctor[GETINDEXPREZZO];
                Giornale g=new Giornale();
                g.setTitolo(titolo);
                g.setTipologia(tipologia);
                g.setLingua(lingua);
                g.setEditore(editore);
                g.setDataPubb(LocalDate.parse(data));
                g.setCopieRimanenti(Integer.parseInt(copie));
                g.setDisponibilita(Integer.parseInt(disp));
                g.setPrezzo(Float.parseFloat(prezzo));
                g.setId(Integer.parseInt(gVEctor[GETINDEXID]));



                gList.add(User.getInstance());
            }
        }
        csvReader.close();
        if (gList.isEmpty()) { throw new IdException(" user not found -> id null");    }
        return gList;    }

}
