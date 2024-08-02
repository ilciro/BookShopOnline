package laptop.database.csvOggetto;

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
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.time.LocalDate;


import java.util.Set;
import java.util.logging.Level;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvOggettoDao implements DaoInterface {

    private static final String LOCATIONG = "report/reportGiornale.csv";
    private static final int GETINDEXTITOLOG = 0;
    private static final int GETINDEXTIPOLOGIAG = 1;
    private static final int GETINDEXLINGUAG = 2;
    private static final int GETINDEXEDITOREG = 3;
    private static final int GETINDEXDATAG = 4;
    private static final int GETINDEXCOPIERG = 5;
    private static final int GETINDEXDISPG = 6;
    private static final int GETINDEXPREZZOG = 7;
    private static final int GETINDEXIDG = 8;

    private static final String LOCATIONL = "report/reportLibro.csv";
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


    private static final String LOCATIONR = "report/reportRivista.csv";
    private static final int GETINDEXTITOLOR = 0;
    private static final int GETINDEXTIPOLOGIAR = 1;
    private static final int GETINDEXAUTORER = 2;
    private static final int GETINDEXLINGUAR = 3;
    private static final int GETINDEXEDITORER = 4;
    private static final int GETINDEXDESCRIZIONER = 5;
    private static final int GETINDEXDATAR = 6;
    private static final int GETINDEXDISPR = 7;
    private static final int GETINDEXPREZZOR = 8;
    private static final int GETINDEXCOPIER = 9;
    private static final int GETINDEXIDR = 10;

    private static final String LIBRO = "libro";
    private static final String GIORNALE = "giornale";
    private static final String RIVISTA = "rivista";

    private static final String USERNOTFOUND = " user not found -> id null";


    private static final ControllerSystemState vis = ControllerSystemState.getInstance();

    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
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
    public void modificaLibro(File fd, Libro l) throws CsvValidationException, IOException, IdException {
        aggiornaLibro(fd, l);
    }

    @Override
    public void modificaRivista(File file, Rivista r) throws CsvValidationException, IOException, IdException {
        aggiornaRivista(file, r);
    }

    @Override
    public void modificaGiornale(File file, Giornale g) throws CsvValidationException, IOException, IdException {
        aggiornaGiornale(file, g);
    }

    @Override
    public Libro retrieveAllLibroData(File fd, int id, String titolo) throws CsvValidationException, IOException, IdException {
        return retrieveDataL(fd, id, titolo);
    }

    @Override
    public Giornale retrieveAllGiornaleData(File fd, int id, String editore) throws CsvValidationException, IOException, IdException {
        return retrieveDataG(fd, id, editore);
    }

    @Override
    public Rivista retrieveAllRivistaData(File fd, int id, String editore, String autore) throws CsvValidationException, IOException, IdException {
        return retrieveDataR(fd, id, editore, autore);
    }


    private synchronized ObservableList<Raccolta> retrieveData(File fd) throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        switch (vis.getType()) {
            case LIBRO:

                while ((gVEctor = csvReader.readNext()) != null) {
                    String titolo = gVEctor[GETINDEXTITOLOL];
                    String numeroPagine = gVEctor[GETINDEXNRPL];
                    String isbn = gVEctor[GETINDEXISBNL];
                    String editore = gVEctor[GETINDEXEDITOREL];
                    String autore = gVEctor[GETINDEXAUTOREL];
                    String lingua = gVEctor[GETINDEXLINGUAL];
                    String categoria = gVEctor[GETINDEXCATEGORIAL];
                    String data = gVEctor[GETINDEXDATAL];
                    String recensione = gVEctor[GETINDEXRECENSIONEL];
                    String copie = gVEctor[GETINDEXCOPIEL];
                    String desc = gVEctor[GETINDEXDESCL];
                    String disp = gVEctor[GETINDEXDISPL];
                    String prezzo = gVEctor[GETINDEXPREZZOL];
                    String id = gVEctor[GETINDEXIDL];

                    Libro l = new Libro();
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
                    String id = gVEctor[GETINDEXIDG];
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
                    String copie = gVEctor[GETINDEXCOPIER];
                    String id = gVEctor[GETINDEXIDR];

                    Rivista r = new Rivista();
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
            default:
                break;
        }

        return gList;
    }

    private static synchronized void inserimentoLibro(Libro l) throws IOException, CsvValidationException {


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
        gVector[GETINDEXIDL] = String.valueOf(getIdMaxL() + 1);
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();
    }

    private static synchronized void inserimentoGiornale(Giornale g) throws IOException, CsvValidationException {

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
        gVector[GETINDEXIDG] = String.valueOf(getIdMaxG() + 1);
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();
    }

    private static synchronized void inserimentoRivista(Rivista r) throws IOException, CsvValidationException {

        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONR, true)));
        String[] gVector = new String[11];

        gVector[GETINDEXTITOLOR] = r.getTitolo();
        gVector[GETINDEXTIPOLOGIAR] = r.getTipologia();
        gVector[GETINDEXAUTORER] = r.getAutore();
        gVector[GETINDEXLINGUAR] = r.getLingua();
        gVector[GETINDEXEDITORER] = r.getEditore();
        gVector[GETINDEXDESCRIZIONER] = r.getDescrizione();
        gVector[GETINDEXDATAR] = String.valueOf(r.getDataPubb());
        gVector[GETINDEXDISPR] = String.valueOf(r.getDisp());
        gVector[GETINDEXPREZZOR] = String.valueOf(r.getPrezzo());
        gVector[GETINDEXCOPIER] = String.valueOf(r.getCopieRim());
        gVector[GETINDEXIDR] = String.valueOf(getIdMaxR() + 1);
        csvWriter.writeNext(gVector);
        csvWriter.flush();
        csvWriter.close();
    }

    private static synchronized int getIdMaxL() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader = new CSVReader(new FileReader(LOCATIONL));
        String[] gVector;
        int id = 0;
        while ((gVector = reader.readNext()) != null)
            id = Integer.parseInt(gVector[GETINDEXIDL]);

        return id;

    }

    private static synchronized int getIdMaxG() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader = new CSVReader(new FileReader(LOCATIONG));
        String[] gVector;
        int id = 0;
        while ((gVector = reader.readNext()) != null)
            id = Integer.parseInt(gVector[GETINDEXIDG]);

        return id;

    }

    private static synchronized int getIdMaxR() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader = new CSVReader(new FileReader(LOCATIONR));
        String[] gVector;
        int id = 0;
        while ((gVector = reader.readNext()) != null)
            id = Integer.parseInt(gVector[GETINDEXIDR]);

        return id;

    }

    private static synchronized void cancellaById(File fd) throws IOException, CsvValidationException {

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
                default ->
                        java.util.logging.Logger.getLogger("test cancella by id").log(Level.SEVERE, " file not exists \n");

            }
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

    private static synchronized void aggiornaLibro(File fd, Libro l) throws CsvValidationException, IOException, IdException {
        cancellaById(fd);
        inserimentoLibro(l);
    }

    private static synchronized void aggiornaRivista(File file, Rivista r) throws CsvValidationException, IOException, IdException {

        cancellaById(file);
        inserimentoRivista(r);
    }

    private static synchronized void aggiornaGiornale(File file, Giornale g) throws CsvValidationException, IOException, IdException {

        cancellaById(file);
        inserimentoGiornale(g);
    }

    private synchronized Libro retrieveDataL(File fd, int idL, String t) throws CsvValidationException, IOException, IdException {

        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        Libro l = new Libro();

        while ((gVEctor = csvReader.readNext()) != null) {
            boolean recordFound = gVEctor[GETINDEXIDL].equals(String.valueOf(idL)) || gVEctor[GETINDEXTITOLOL].equals(t);
            if (recordFound) {
                String tit = gVEctor[GETINDEXTITOLOL];
                String numeroPagine = gVEctor[GETINDEXNRPL];
                String isbn = gVEctor[GETINDEXISBNL];
                String editore = gVEctor[GETINDEXEDITOREL];
                String autore = gVEctor[GETINDEXAUTOREL];
                String lingua = gVEctor[GETINDEXLINGUAL];
                String categoria = gVEctor[GETINDEXCATEGORIAL];
                String data = gVEctor[GETINDEXDATAL];
                String recensione = gVEctor[GETINDEXRECENSIONEL];
                String copie = gVEctor[GETINDEXCOPIEL];
                String desc = gVEctor[GETINDEXDESCL];
                String disp = gVEctor[GETINDEXDISPL];
                String prezzo = gVEctor[GETINDEXPREZZOL];
                String id = gVEctor[GETINDEXIDL];

                l.setTitolo(tit);
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


            }
        }
        csvReader.close();


        return l;
    }

    private static synchronized Giornale retrieveDataG(File fd, int idG, String editore) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        Giornale g = new Giornale();
        while ((gVEctor = csvReader.readNext()) != null) {
            boolean recordFound = gVEctor[GETINDEXIDG].equals(String.valueOf(idG)) || gVEctor[GETINDEXEDITOREG].equals(editore);
            if (recordFound) {
                String titolo = gVEctor[GETINDEXTITOLOG];
                String tipologia = gVEctor[GETINDEXTIPOLOGIAG];
                String lingua = gVEctor[GETINDEXLINGUAG];
                String ed = gVEctor[GETINDEXEDITOREG];
                String data = gVEctor[GETINDEXDATAG];
                String copie = gVEctor[GETINDEXCOPIERG];
                String disp = gVEctor[GETINDEXDISPG];
                String prezzo = gVEctor[GETINDEXPREZZOG];
                String id = gVEctor[GETINDEXIDG];

                g.setTitolo(titolo);
                g.setTipologia(tipologia);
                g.setLingua(lingua);
                g.setEditore(ed);
                g.setDataPubb(LocalDate.parse(data));
                g.setCopieRimanenti(Integer.parseInt(copie));
                g.setDisponibilita(Integer.parseInt(disp));
                g.setPrezzo(Float.parseFloat(prezzo));
                g.setId(Integer.parseInt(id));

            }
        }
        return g;
    }

    private static synchronized Rivista retrieveDataR(File fd, int idR, String editore, String autore) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)));
        String[] gVEctor;
        Rivista r = new Rivista();
        while ((gVEctor = csvReader.readNext()) != null) {
            boolean recordFound = gVEctor[GETINDEXIDR].equals(String.valueOf(idR)) || gVEctor[GETINDEXEDITORER].equals(editore) || gVEctor[GETINDEXAUTORER].equalsIgnoreCase(autore);
            if (recordFound) {
                String titolo=gVEctor[GETINDEXTITOLOR];
                String tipologia=gVEctor[GETINDEXTIPOLOGIAR];
                String aut=gVEctor[GETINDEXAUTORER];
                String lingua=gVEctor[GETINDEXLINGUAR];
                String ed=gVEctor[GETINDEXEDITORER];
                String desc=gVEctor[GETINDEXDESCRIZIONER];
                String data=gVEctor[GETINDEXDATAR];
                String disp=gVEctor[GETINDEXDISPR];
                String prezzo=gVEctor[GETINDEXPREZZOR];
                String copieRim=gVEctor[GETINDEXCOPIER];
                String id=gVEctor[GETINDEXIDR];

                r.setTitolo(titolo);
                r.setTipologia(tipologia);
                r.setAutore(aut);
                r.setLingua(lingua);
                r.setEditore(ed);
                r.setDescrizione(desc);
                r.setDataPubb(LocalDate.parse(data));
                r.setDisp(Integer.parseInt(disp));
                r.setPrezzo(Float.parseFloat(prezzo));
                r.setCopieRim(Integer.parseInt(copieRim));
                r.setId(Integer.parseInt(id));



            }
        }
        return r;
    }
}

