package laptop.database.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvOggettoDao implements DaoInterface{

    private static final String LOCATIONG = "report/reportGiornali.csv";
    private static final int GETINDEXTITOLOG = 0;
    private static final int GETINDEXTIPOLOGIAG = 1;
    private static final int GETINDEXLINGUAG = 2;
    private static final int GETINDEXEDITOREG = 3;
    private static final int GETINDEXDATAG = 4;
    private static final int GETINDEXCOPIERG = 5;
    private static final int GETINDEXDISPG = 6;
    private static final int GETINDEXPREZZOG = 7;
    private static final int GETINDEXIDG = 8;
    private static final String QUERYG = "select titolo,tipologia,lingua,editore,dataPubblicazione,copieRimanenti,disp,prezzo,idGiornale from GIORNALE";
    private static final File fdg = new File(LOCATIONG);

    private static final String LOCATIONL = "report/reportLibri.csv";
    private static final int GETINDEXTITOLOL = 0;
    private static final int GETINDEXNRPL = 1;
    private static final int GETINDEXISBNL = 2;
    private static final int GETINDEXEDITOREL = 3;
    private static final int GETINDEXAUTOREL = 4;
    private static final int GETINDEXLINGUAL = 5;
    private static final int GETINDEXCATEGORIAL = 6;
    private static final int GETINDEXDATAL = 7;
    private static final int GETINDEXRECENSIONEL = 8;
    private static final int GETINDEXCOPIEL = 9;
    private static final int GETINDEXDESCL = 10;
    private static final int GETINDEXDISPL = 11;
    private static final int GETINDEXPREZZOL = 12;
    private static final int GETINDEXIDL = 13;
    private static final String QUERYL=" select titolo,numeroPagine,codIsbn,editore,autore,lingua,categoria,dataPubblicazione,recensione,copieRimanenti,breveDescrizione,disp,prezzo,idLibro from LIBRO";
    private static final File fdl=new File(LOCATIONL);

    private static final String LOCATIONR = "report/reportRiviste.csv";
    private static final int GETINDEXTITOLOR = 0;
    private static final int GETINDEXTIPOLOGIAR = 1;
    private static final int GETINDEXAUTORER = 2;
    private static final int GETINDEXLINGUAR = 3;
    private static final int GETINDEXEDITORER = 4;
    private static final int GETINDEXDESCRIZIONER = 5;
    private static final int GETINDEXDATAR = 6;
    private static final int GETINDEXDISPR = 7;
    private static final int GETINDEXPREZZOR = 8;
    private static final int GETINDEXCOPIER=9;
    private static final int GETINDEXIDR=10;
    private static final String QUERYR = "select titolo,tipologia,autore,lingua,editore,descrizione,dataPubblicazione,disp,prezzo,copieRimanenti,idRivista from RIVISTA";
    private static final File fdr = new File(LOCATIONR);
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";

    private static ControllerSystemState vis= ControllerSystemState.getInstance();

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

        switch (vis.getType())
        {
            case LIBRO:
                try {
                    cleanUp(Path.of(fdl.toURI()));
                    Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATIONL + " deleted");
                    throw new IOException(" file cancellato");
                } catch (IOException e) {
                    Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATIONL + " file not exists");
                    if (fdl.createNewFile()) {
                        Logger.getLogger("report libro").log(Level.SEVERE, "\n" + LOCATIONL + " making file");
                        try (Connection conn = ConnToDb.connectionToDB();
                             PreparedStatement prepQ = conn.prepareStatement(QUERYL)) {
                            ResultSet rs = prepQ.executeQuery(QUERYL);
                            CSVWriter writer = new CSVWriter(new FileWriter(LOCATIONL));
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
                break;
            case GIORNALE:
                try {
                    cleanUp(Path.of(fdg.toURI()));
                    Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATIONG + " deleted");
                    throw new IOException(" file cancellato");
                } catch (IOException e) {
                    Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATIONG + " file not exists");
                    if (fdg.createNewFile()) {
                        Logger.getLogger("report giornale").log(Level.SEVERE, "\n" + LOCATIONG + " making file");
                        try (Connection conn = ConnToDb.connectionToDB();
                             PreparedStatement prepQ = conn.prepareStatement(QUERYG)) {
                            ResultSet rs = prepQ.executeQuery(QUERYG);
                            CSVWriter writer = new CSVWriter(new FileWriter(LOCATIONG));
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
                break;
            case RIVISTA:
                try {
                    cleanUp(Path.of(fdr.toURI()));
                    Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATIONR + " deleted");
                    throw new IOException(" file cancellato");
                } catch (IOException e) {
                    Logger.getLogger("genera report").log(Level.SEVERE, "\n " + LOCATIONR + " file not exists");
                    if (fdr.createNewFile()) {
                        Logger.getLogger("report rivista").log(Level.SEVERE, "\n" + LOCATIONR + " making file");
                        try (Connection conn = ConnToDb.connectionToDB();
                             PreparedStatement prepQ = conn.prepareStatement(QUERYR)) {
                            ResultSet rs = prepQ.executeQuery(QUERYR);
                            CSVWriter writer = new CSVWriter(new FileWriter(LOCATIONR));
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
                break;
            default:break;
        }

    }

    private synchronized ObservableList<Raccolta> retrieveData(File fd) throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        switch (vis.getType())
        {
            case LIBRO:

                while ((gVEctor = csvReader.readNext()) != null) {
                    String titolo = gVEctor[GETINDEXTITOLOL];
                    String numeroPagine=gVEctor[GETINDEXNRPL];
                    String isbn=gVEctor[GETINDEXISBNL];
                    String editore=gVEctor[GETINDEXEDITOREL];
                    String autore=gVEctor[GETINDEXAUTOREL];
                    String lingua=gVEctor[GETINDEXLINGUAL];
                    String categoria = gVEctor[GETINDEXCATEGORIAL];
                    String data = gVEctor[GETINDEXDATAL];
                    String recensione=gVEctor[GETINDEXRECENSIONEL];
                    String copie = gVEctor[GETINDEXCOPIEL];
                    String desc=gVEctor[GETINDEXDESCL];
                    String disp = gVEctor[GETINDEXDISPL];
                    String prezzo = gVEctor[GETINDEXPREZZOL];
                    String id=gVEctor[GETINDEXIDL];

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
                break;
            case GIORNALE:
                while ((gVEctor = csvReader.readNext()) != null) {


                    String titolo = gVEctor[GETINDEXTITOLOG];
                    String tipologia = gVEctor[GETINDEXTIPOLOGIAG];
                    String lingua = gVEctor[GETINDEXLINGUAG];
                    String editore = gVEctor[GETINDEXEDITOREG];
                    String data = gVEctor[GETINDEXDATAG];
                    String copie = gVEctor[GETINDEXCOPIERG];
                    String disp = gVEctor[GETINDEXDISPG];
                    String prezzo = gVEctor[GETINDEXPREZZOG];
                    String id=gVEctor[GETINDEXIDG];
                    Giornale g = new Giornale();
                    g.setTitolo(titolo);
                    g.setTipologia(tipologia);
                    g.setLingua(lingua);
                    g.setEditore(editore);
                    g.setDataPubb(LocalDate.parse(data));
                    g.setCopieRimanenti(Integer.parseInt(copie));
                    g.setDisponibilita(Integer.parseInt(disp));
                    g.setPrezzo(Float.parseFloat(prezzo));
                    g.setId(Integer.parseInt(id));


                    gList.add(g);

                }
                csvReader.close();
                if (gList.isEmpty()) {
                    throw new IdException(" user not found -> id null");
                }
                break;
            case RIVISTA:
                while ((gVEctor = csvReader.readNext()) != null) {


                    String titolo = gVEctor[GETINDEXTITOLOR];
                    String tipologia = gVEctor[GETINDEXTIPOLOGIAR];
                    String autore = gVEctor[GETINDEXAUTORER];
                    String lingua = gVEctor[GETINDEXLINGUAR];
                    String editore = gVEctor[GETINDEXEDITORER];
                    String desc = gVEctor[GETINDEXDESCRIZIONER];
                    String data = gVEctor[GETINDEXDATAR];
                    String disp = gVEctor[GETINDEXDISPR];
                    String prezzo = gVEctor[GETINDEXPREZZOR];
                    String copie=gVEctor[GETINDEXCOPIER];
                    String id=gVEctor[GETINDEXIDR];

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
                break;
            default:break;
        }

        return  gList;
    }




}
