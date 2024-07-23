package laptop.database.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;
import laptop.utilities.ConnToDb;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvRivistaDao implements DaoInterface{
    private static final String LOCATION = "report/reportRiviste.csv";
    private static final int GETINDEXTITOLO = 0;
    private static final int GETINDEXTIPOLOGIA = 1;
    private static final int GETINDEXAUTORE = 2;
    private static final int GETINDEXLINGUA = 3;
    private static final int GETINDEXEDITORE = 4;
    private static final int GETINDEXDESCRIZIONE = 5;
    private static final int GETINDEXDATA = 6;
    private static final int GETINDEXDISP = 7;
    private static final int GETINDEXPREZZO = 8;
    private static final int GETINDEXCOPIE=9;
    private static final int GETINDEXID=10;
    private static final String QUERY = "select titolo,tipologia,autore,lingua,editore,descrizione,dataPubblicazione,disp,prezzo,copieRimanenti,idRivista from RIVISTA";
    private static final File fd = new File(LOCATION);

    private HashMap<String, Rivista> localCache;

    public CsvRivistaDao()
    {
        this.localCache=new HashMap<>();
    }
    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }
    @Override
    public void generaReport() throws IOException {
        report();
    }

    @Override
    public ObservableList<Raccolta> retrieveAllData(File fd) throws CsvValidationException, IOException, IdException {
        return retrieveData(fd);
    }

    private static synchronized void report() throws IOException {
        try {
            cleanUp(Path.of(fd.toURI()));
            Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATION + " deleted");
            throw new IOException(" file cancellato");
        } catch (IOException e) {
            Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATION + " file not exists");
            if (fd.createNewFile()) {
                Logger.getLogger("report rivista").log(Level.SEVERE, "\n" + LOCATION + " making file");
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(QUERY)) {
                    ResultSet rs = prepQ.executeQuery(QUERY);
                    CSVWriter writer = new CSVWriter(new FileWriter(LOCATION));
                    rs.getMetaData();
                    String[] data = new String[11];
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        data[3] = rs.getString(4);
                        data[4] = rs.getString(5);
                        data[5] = rs.getString(6);
                        data[6] = String.valueOf(rs.getDate(7));
                        data[7] = String.valueOf(rs.getInt(8));
                        data[8] = String.valueOf(rs.getFloat(9));
                        data[9]=String.valueOf(rs.getInt(10));
                        data[10]=String.valueOf(rs.getInt(11));
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


    private synchronized ObservableList<Raccolta> retrieveData(File fd) throws CsvValidationException, IOException, IdException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        while ((gVEctor = csvReader.readNext()) != null) {


            String titolo = gVEctor[GETINDEXTITOLO];
            String tipologia = gVEctor[GETINDEXTIPOLOGIA];
            String autore = gVEctor[GETINDEXAUTORE];
            String lingua = gVEctor[GETINDEXLINGUA];
            String editore = gVEctor[GETINDEXEDITORE];
            String desc = gVEctor[GETINDEXDESCRIZIONE];
            String data = gVEctor[GETINDEXDATA];
            String disp = gVEctor[GETINDEXDISP];
            String prezzo = gVEctor[GETINDEXPREZZO];
            String copie=gVEctor[GETINDEXCOPIE];
            String id=gVEctor[GETINDEXID];

            Rivista r=new Rivista();
            r.setTitolo(titolo);
            r.setTipologia(tipologia);
            r.setAutore(autore);
            r.setLingua(lingua);
            r.setEditore(editore);
            r.setDescrizione(desc);
            r.setDataPubb(LocalDate.parse(data));
            r.setDisp(Integer.parseInt(disp));
            r.setPrezzo(Float.parseFloat(prezzo));
            r.setCopieRim(Integer.parseInt(copie));
            r.setId(Integer.parseInt(id));


            gList.add(r);

        }
        csvReader.close();
        if (gList.isEmpty()) {
            throw new IdException(" user not found -> id null");
        }
        return  gList;
    }
}
