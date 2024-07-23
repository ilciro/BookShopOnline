package laptop.database.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
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

public class CsvLibroDao implements DaoInterface {
    private static final String LOCATION = "report/reportLibri.csv";
    private static final int GETINDEXTITOLO = 0;
    private static final int GETINDEXNRP = 1;
    private static final int GETINDEXISBN = 2;
    private static final int GETINDEXEDITORE = 3;
    private static final int GETINDEXAUTORE = 4;
    private static final int GETINDEXLINGUA = 5;
    private static final int GETINDEXCATEGORIA = 6;
    private static final int GETINDEXDATA = 7;
    private static final int GETINDEXRECENSIONE = 8;
    private static final int GETINDEXCOPIE = 9;
    private static final int GETINDEXDESC = 10;
    private static final int GETINDEXDISP = 11;
    private static final int GETINDEXPREZZO = 12;
    private static final int GETINDEXID = 13;
    private static final String QUERY=" select titolo,numeroPagine,codIsbn,editore,autore,lingua,categoria,dataPubblicazione,recensione,copieRimanenti,breveDescrizione,disp,prezzo,idLibro from LIBRO";
    private static final File fd=new File(LOCATION);
    private HashMap<String, Libro> localCache ;

    public CsvLibroDao() {
        this.localCache = new HashMap<>();
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
                Logger.getLogger("report libro").log(Level.SEVERE, "\n" + LOCATION + " making file");
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(QUERY)) {
                    ResultSet rs = prepQ.executeQuery(QUERY);
                    CSVWriter writer = new CSVWriter(new FileWriter(LOCATION));
                    rs.getMetaData();
                    String[] data = new String[14];
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = String.valueOf(rs.getInt(2));
                        data[2] = rs.getString(3);
                        data[3] = rs.getString(4);
                        data[4] = rs.getString(5);
                        data[5] = rs.getString(6);
                        data[6] = rs.getString(7);
                        data[7] = String.valueOf(rs.getDate(8).toLocalDate());
                        data[8] = rs.getString(9);
                        data[9]=String.valueOf(rs.getInt(10));
                        data[10] = rs.getString(11);
                        data[11]=String.valueOf(rs.getInt(12));
                        data[12]=String.valueOf(rs.getFloat(13));
                        data[13]=String.valueOf(rs.getInt(14));

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
            String numeroPagine=gVEctor[GETINDEXNRP];
            String isbn=gVEctor[GETINDEXISBN];
            String editore=gVEctor[GETINDEXEDITORE];
            String autore=gVEctor[GETINDEXAUTORE];
            String lingua=gVEctor[GETINDEXLINGUA];
            String categoria = gVEctor[GETINDEXCATEGORIA];
            String data = gVEctor[GETINDEXDATA];
            String recensione=gVEctor[GETINDEXRECENSIONE];
            String copie = gVEctor[GETINDEXCOPIE];
            String desc=gVEctor[GETINDEXDESC];
            String disp = gVEctor[GETINDEXDISP];
            String prezzo = gVEctor[GETINDEXPREZZO];
            String id=gVEctor[GETINDEXID];

            Libro l=new Libro();
            l.setTitolo(titolo);
            l.setNrPagine(Integer.parseInt(numeroPagine));
            l.setCodIsbn(isbn);
            l.setEditore(editore);
            l.setAutore(autore);
            l.setLingua(lingua);
            l.setCategoria(categoria);
            l.setDataPubb(LocalDate.parse(data));
            l.setRecensione(recensione);
            l.setNrCopie(Integer.parseInt(copie));
            l.setDesc(desc);
            l.setDisponibilita(Integer.parseInt(disp));
            l.setPrezzo(Float.parseFloat(prezzo));
            l.setId(Integer.parseInt(id));


            gList.add(l);

        }
        csvReader.close();
        if (gList.isEmpty()) {
            throw new IdException(" user not found -> id null");
        }
        return  gList;
    }


}
