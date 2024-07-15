package laptop.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvGiornaleDao implements DaoInterface {

    private static final String LOCATION = "report/reportGiornali.csv";
    private static final int GETINDEXTITOLO = 0;
    private static final int GETINDEXTIPOLOGIA = 1;
    private static final int GETINDEXLINGUA = 2;
    private static final int GETINDEXEDITORE = 3;
    private static final int GETINDEXDATA = 4;
    private static final int GETINDEXCOPIER = 5;
    private static final int GETINDEXDISP = 6;
    private static final int GETINDEXPREZZO = 7;
    private static final int GETINDEXID = 8;
    private static final String QUERY = "select titolo,tipologia,lingua,editore,dataPubblicazione,copieRimanenti,disp,prezzo,idGiornale from GIORNALE";
    private static File fd;

    private final HashMap<String, Giornale> localCache;


    public CsvGiornaleDao() throws IOException {
        fd = new File(LOCATION);
        this.localCache = new HashMap<>();
    }


    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public List<Giornale> giornaliByIdTitoloEd(File fd, String titolo, String editore) throws Exception {
        return retrieveByIdTitoloEd(fd, titolo, editore);
    }

    @Override
    public void insertGiornale(File fd, Giornale g) throws Exception {
        saveGiornale(fd, g);

    }

    @Override
    public void removeGiornale(File fd, Giornale g) throws Exception {
        removeGiornaleById(fd, g);
    }

    @Override
    public List<Giornale> giornaleById(File fd, int id) throws Exception {
        return retreiveById(fd, id);
    }

    @Override
    public void generaReport() throws IOException {
        //prende tutto il db
        report();
    }

    private static synchronized void report() throws IOException {
        try {
            cleanUp(Path.of(fd.toURI()));
            Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATION + " deleted");
            throw new IOException(" file cancellato");
        } catch (IOException e) {
            Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATION + " file not exists");
            if (fd.createNewFile()) {
                Logger.getLogger("report giornale").log(Level.SEVERE, "\n" + LOCATION + " making file");
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(QUERY)) {
                    ResultSet rs = prepQ.executeQuery(QUERY);
                    CSVWriter writer = new CSVWriter(new FileWriter(LOCATION));
                    rs.getMetaData();
                    String[] data = new String[9];
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        data[3] = rs.getString(4);
                        data[4] = String.valueOf(rs.getDate(5));
                        data[5] = String.valueOf(rs.getInt(6));
                        data[6] = String.valueOf(rs.getInt(7));
                        data[7] = String.valueOf(rs.getFloat(8));
                        data[8] = String.valueOf(rs.getInt(9));
                        writer.writeNext(data);
                    }
                    writer.flush();
                    writer.close();
                } catch (SQLException ex) {
                    Logger.getLogger("report giornale").log(Level.SEVERE, "\n eccezione ottenuta .", ex);
                }
            }
        }
    }

    private static synchronized List<Giornale> retreiveById(File fd, int id) throws Exception {
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        List<Giornale> gList = new ArrayList<>();
        while ((gVEctor = csvReader.readNext()) != null) {
            boolean userVectorFound = gVEctor[GETINDEXID].equals(String.valueOf(id));
            if (userVectorFound) {

                String titolo = gVEctor[GETINDEXTITOLO];
                String tipologia = gVEctor[GETINDEXTIPOLOGIA];
                String lingua = gVEctor[GETINDEXLINGUA];
                String editore = gVEctor[GETINDEXEDITORE];
                String data = gVEctor[GETINDEXDATA];
                String copie = gVEctor[GETINDEXCOPIER];
                String disp = gVEctor[GETINDEXID];
                String prezzo = gVEctor[GETINDEXPREZZO];
                Giornale g = new Giornale();
                g.setTitolo(titolo);
                g.setTipologia(tipologia);
                g.setLingua(lingua);
                g.setEditore(editore);
                g.setDataPubb(LocalDate.parse(data));
                g.setCopieRimanenti(Integer.parseInt(copie));
                g.setDisponibilita(Integer.parseInt(disp));
                g.setPrezzo(Float.parseFloat(prezzo));
                g.setId(Integer.parseInt(gVEctor[GETINDEXID]));


                gList.add(g);
            }
        }
        csvReader.close();
        if (gList.isEmpty()) {
            throw new IdException(" user not found -> id null");
        }
        return gList;
    }

    private static synchronized List<Giornale> retrieveByIdTitoloEd(File fd, String titolo, String editore) throws Exception {

        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVector;
        List<Giornale> giornaleList = new ArrayList<>();
        while ((gVector = csvReader.readNext()) != null) {
            boolean gVectorFound = (gVector[GETINDEXTITOLO].equals(titolo)) || (gVector[GETINDEXEDITORE].equals(editore));
            if (gVectorFound) {
                int id = Integer.parseInt(gVector[GETINDEXID]);
                String titoloG = gVector[GETINDEXTITOLO];
                String editoreG = gVector[GETINDEXEDITORE];
                Giornale g = new Giornale();
                g.setId(id);
                g.setTitolo(titoloG);
                g.setEditore(editoreG);
                giornaleList.add(g);
            }
        }
        csvReader.close();
        if (giornaleList.isEmpty()) {
            throw new NullPointerException(" giornale not found");
        }
        return giornaleList;
    }

    private static synchronized void saveGiornale(File fd, Giornale g) throws Exception {


        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)));
        String[] gVector = new String[9];

        gVector[GETINDEXTITOLO] = g.getTitolo();
        gVector[GETINDEXTIPOLOGIA] = g.getTipologia();
        gVector[GETINDEXLINGUA] = g.getLingua();
        gVector[GETINDEXEDITORE] = g.getEditore();
        gVector[GETINDEXDATA] = String.valueOf(g.getDataPubb());
        gVector[GETINDEXCOPIER] = String.valueOf(g.getCopieRimanenti());
        gVector[GETINDEXDISP] = String.valueOf(g.getDisponibilita());
        gVector[GETINDEXPREZZO] = String.valueOf(g.getPrezzo());
        gVector[GETINDEXID] = String.valueOf(g.getId());
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();

    }

    private static synchronized void removeGiornaleById(File fd, Giornale g) throws Exception {


        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
            Files.createTempFile("prefix", "suffix", attr); // Compliant
        }
        File tmpFD = Files.createTempFile("prefix", "suffix").toFile();
        boolean found = false;
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] giornaleVector;
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tmpFD, true)));

        while ((giornaleVector = csvReader.readNext()) != null) {

            boolean userVectorFound = giornaleVector[GETINDEXID].equals(String.valueOf(g.getId()));
            if (!userVectorFound) {
                csvWriter.writeNext(giornaleVector);
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

    public synchronized void checkDuplicate(Giornale g) throws Exception {
        boolean duplicatedRecordId=false;

        synchronized (this.localCache) {
            duplicatedRecordId = (this.localCache.get(String.valueOf(g.getId())) != null);
        }
        if (!duplicatedRecordId) {
            try {
                List<Giornale> albumList = retreiveById(fd, g.getId());
                duplicatedRecordId = !albumList.isEmpty();
            } catch (Exception e) {
                duplicatedRecordId = false;
            }
        }

        if (duplicatedRecordId) {
            try {
                throw new IdException(
                        "Duplicated Instance ID. Id " + g.getId() + " was already assigned");
            }catch (IdException e)
            {
                removeGiornale(fd,g);
            }
        }
        insertGiornale(fd,g);





    }

}
