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
    private static final String DELETED=" deleted";
    private static final String GENERAREPORT="genera report";
    private static final String FILECANCELLATO=" file cancellato";
    private static final String FILENOTEXISTS=" file not exists";
    private static final String MAKINGFILE=" making file";
    private static final String ECCEZIONE="\n eccezione ottenuta .";
    private static final String USERNOTFOUND=" user not found -> id null";



    private static final ControllerSystemState vis= ControllerSystemState.getInstance();

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

    @Override
    public void inserisciLibro(Libro l) throws IOException, CsvValidationException {
        inserimentoLibro(l);
    }
    @Override
    public void inserisciGiornale(Giornale g) throws IOException, CsvValidationException {
        inserimentoGiornale(g);
    }
    @Override
    public void inserisciRivista(Rivista r) throws IOException, CsvValidationException {
        inserimentoRivista(r);
    }

    @Override
    public void eliminaOggetto(File fd) throws CsvValidationException, IOException {
        cancellaById(fd);
    }

    @Override
    public void modificaLibro(File fd,Libro l) throws CsvValidationException, IOException {
        aggiornaLibro(fd,l);
    }

    @Override
    public void modificaRivista(File file, Rivista r) throws CsvValidationException, IOException {
        aggiornaRivista(file,r);
    }

    @Override
    public void modificaGiornale(File file, Giornale g) throws CsvValidationException, IOException {
        aggiornaGiornale(file,g);
    }


    private static synchronized void report() throws IOException {

        switch (vis.getType())
        {
            case LIBRO:
                reportLibro();
                break;
            case GIORNALE:
                reportGiornale();
                break;
            case RIVISTA:
                reportRivista();
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
                    throw new IdException(USERNOTFOUND);
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
                    throw new IdException(USERNOTFOUND);
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
                    throw new IdException(USERNOTFOUND);
                }
                break;
            default:break;
        }

        return  gList;
    }
    private  static synchronized void reportLibro() throws IOException {
        try {
            cleanUp(Path.of(fdl.toURI()));
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATIONL + DELETED);
            throw new IOException(FILECANCELLATO);
        } catch (IOException e) {
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATIONL + FILENOTEXISTS);
            if (fdl.createNewFile()) {
                Logger.getLogger("report libro").log(Level.SEVERE, "\n" + LOCATIONL + MAKINGFILE);
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
                } catch (SQLException | IOException ex) {
                    Logger.getLogger("report libro").log(Level.SEVERE, ECCEZIONE, ex);
                }
            }
        }
    }
    private static synchronized void reportGiornale() throws IOException {
        try {
            cleanUp(Path.of(fdg.toURI()));
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATIONG + DELETED);
            throw new IOException(FILECANCELLATO);
        } catch (IOException e) {
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATIONG + FILENOTEXISTS);
            if (fdg.createNewFile()) {
                Logger.getLogger("report giornale").log(Level.SEVERE, "\n" + LOCATIONG + MAKINGFILE);
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
                } catch (SQLException | IOException ex) {
                    Logger.getLogger("exception report giornale").log(Level.SEVERE, ECCEZIONE, ex);
                }
            }
        }
    }
    private static synchronized void reportRivista() throws IOException {
        try {
            cleanUp(Path.of(fdr.toURI()));
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATIONR + DELETED);
            throw new IOException(FILECANCELLATO);
        } catch (IOException e) {
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATIONR + FILENOTEXISTS);
            if (fdr.createNewFile()) {
                Logger.getLogger("report rivista").log(Level.SEVERE, "\n" + LOCATIONR + MAKINGFILE);
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
                } catch (SQLException | IOException ex) {
                    Logger.getLogger("report rivista").log(Level.SEVERE, ECCEZIONE, ex);
                }
            }
        }
    }
    private static synchronized void inserimentoLibro(Libro l) throws IOException, CsvValidationException {

        cleanUp(Path.of(LOCATIONL));

        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONL, true)));
        String[] gVector = new String[14];

        gVector[GETINDEXTITOLOL] = l.getTitolo();
        gVector[GETINDEXNRPL] = String.valueOf(l.getNrPagine());
        gVector[GETINDEXISBNL] = l.getCodIsbn();
        gVector[GETINDEXEDITOREL] = l.getEditore();
        gVector[GETINDEXAUTOREL] = l.getAutore();
        gVector[GETINDEXLINGUAL] = l.getLingua();
        gVector[GETINDEXCATEGORIAL] = l.getCategoria();
        gVector[GETINDEXDATAL] = String.valueOf(l.getDataPubb());
        gVector[GETINDEXRECENSIONEL] = l.getRecensione();
        gVector[GETINDEXCOPIEL] = String.valueOf(l.getNrCopie());
        gVector[GETINDEXDESCL] = l.getDesc();
        gVector[GETINDEXDISPL] = String.valueOf(l.getDisponibilita());
        gVector[GETINDEXPREZZOL] = String.valueOf(l.getPrezzo());
        gVector[GETINDEXIDL] = String.valueOf(getIdMax() + 1);
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();
    }
    private static synchronized void inserimentoGiornale(Giornale g) throws IOException, CsvValidationException {
        cleanUp(Path.of(LOCATIONG));

        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONG, true)));
        String[] gVector = new String[9];

        gVector[GETINDEXTITOLOG] = g.getTitolo();
        gVector[GETINDEXTIPOLOGIAG] = g.getTipologia();
        gVector[GETINDEXLINGUAG] = g.getLingua();
        gVector[GETINDEXEDITOREG] = g.getEditore();
        gVector[GETINDEXDATAG] = String.valueOf(g.getDataPubb());
        gVector[GETINDEXCOPIERG] = String.valueOf(g.getCopieRimanenti());
        gVector[GETINDEXDISPG] = String.valueOf(g.getDisponibilita());
        gVector[GETINDEXPREZZOG] = String.valueOf(g.getPrezzo());
        gVector[GETINDEXIDG] = String.valueOf(getIdMax()+1);
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();
    }
    private static synchronized void inserimentoRivista(Rivista r) throws IOException, CsvValidationException {
        cleanUp(Path.of(LOCATIONR));

        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONR, true)));
        String [] gVector = new String[11];

        gVector[GETINDEXTITOLOR] = r.getTitolo();
        gVector[GETINDEXTIPOLOGIAR] = r.getTipologia();
        gVector[GETINDEXAUTORER] = r.getAutore();
        gVector[GETINDEXLINGUAR] = r.getLingua();
        gVector[GETINDEXEDITORER] =r.getEditore();
        gVector[GETINDEXDESCRIZIONER] =r.getDescrizione();
        gVector[GETINDEXDATAR] = String.valueOf(r.getDataPubb());
        gVector[GETINDEXDISPR] = String.valueOf(r.getDisp());
        gVector[GETINDEXPREZZOR] = String.valueOf(r.getPrezzo());
        gVector[GETINDEXCOPIER]=String.valueOf(r.getCopieRim());
        gVector[GETINDEXIDR]=String.valueOf(getIdMax()+1);
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();
    }
    public static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader;
        String[] gVector;
        int id = 0;
        switch (vis.getType()) {
            case LIBRO: {
                reader = new CSVReader(new FileReader(LOCATIONL));
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDL]);
                }
                break;
            }
            case GIORNALE:
                {
                    reader = new CSVReader(new FileReader(LOCATIONG));
                    while ((gVector = reader.readNext()) != null) {
                        id=Integer.parseInt(gVector[GETINDEXIDG]);
                    }
                    break;
                }
            case RIVISTA:
            {
                reader = new CSVReader(new FileReader(LOCATIONR));
                while ((gVector = reader.readNext()) != null) {
                    id=Integer.parseInt(gVector[GETINDEXIDR]);
                }
                break;
            }
            default:
                java.util.logging.Logger.getLogger("Test General connection standard").log(Level.INFO, "Connesso standard a sys........\n");

        }
        return id;

    }
    private static synchronized void cancellaById(File fd) throws IOException, CsvValidationException {
        assert(vis.getId()<getIdMax() || vis.getId()!=0);
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
            Files.createTempFile("prefix", "suffix", attr); // Compliant
        }
        File tmpFD = new File("report/appoggio.csv");
        boolean found = false;
        // create csvReader object passing file reader as a parameter
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] giornaleVector;
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tmpFD, true)));
        //check on path
        boolean userVectorFound = false;

        while ((giornaleVector = csvReader.readNext()) != null) {
            switch (fd.getPath()) {
                case LOCATIONL -> userVectorFound = giornaleVector[GETINDEXIDL].equals(String.valueOf(vis.getId()));
                case LOCATIONG -> userVectorFound = giornaleVector[GETINDEXIDG].equals(String.valueOf(vis.getId()));
                case LOCATIONR -> userVectorFound = giornaleVector[GETINDEXIDR].equals(String.valueOf(vis.getId()));
                default-> Logger.getLogger("Test cancella").log(Level.SEVERE,"Path not matching ..");

            }
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
    private static synchronized void aggiornaLibro(File fd,Libro l) throws CsvValidationException, IOException {
        assert(vis.getId()<getIdMax() || vis.getId()!=0);
        cancellaById(fd);
        inserimentoLibro(l);
    }

    private static synchronized void aggiornaRivista(File file, Rivista r) throws CsvValidationException, IOException{
        assert(vis.getId()<getIdMax() || vis.getId()!=0);
        cancellaById(file);
        inserimentoRivista(r);
    }
    private static synchronized void aggiornaGiornale(File file, Giornale g) throws CsvValidationException, IOException{
        assert(vis.getId()<getIdMax() || vis.getId()!=0);
        cancellaById(file);
        inserimentoGiornale(g);
    }

}
