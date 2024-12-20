package laptop.database.csvoggetto;

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


    private final HashMap<Integer, Libro> cacheLibro;
    private  final HashMap<Integer, Giornale> cacheGiornale;
    private  final HashMap<Integer, Rivista> cacheRivista;
    private final File fdL=new File(LOCATIONL);
    private final File fdG=new File(LOCATIONG);
    private final File fdR=new File(LOCATIONR);
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";



    public CsvOggettoDao() throws IOException {
        this.cacheLibro = new HashMap<>();
        this.cacheGiornale = new HashMap<>();
        this.cacheRivista = new HashMap<>();


    }



    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public boolean inserisciLibro(Libro l) throws IOException, CsvValidationException {
        //provo con titolo ed autore ed editore
        //visto che id non buono in quanto non gli e lo assegno

        boolean duplicatedL = false;
        boolean duplicatedT = false;
        boolean duplicatedA = false;
        boolean duplicatedE = false;
        synchronized (this.cacheLibro)
        {



            for(Map.Entry<Integer,Libro>mapL:this.cacheLibro.entrySet())
            {
                if(mapL.getValue().getTitolo()!=null)
                    duplicatedT=mapL.getValue().getTitolo()!=null;
                if(mapL.getValue().getAutore()!=null)
                    duplicatedA=mapL.getValue().getAutore()!=null;
                if(mapL.getValue().getEditore()!=null)
                    duplicatedE=mapL.getValue().getEditore()!=null;
                duplicatedL=duplicatedT&&duplicatedA&&duplicatedE;

            }



        }
        if(!duplicatedL)
        {
            List<Libro> list=returnLibriByTAE(this.fdL,l.getTitolo(),l.getAutore(),l.getEditore(),l.getId());
            duplicatedL=(!list.isEmpty());
        }
        if(duplicatedL)
        {
            try{
                Logger.getLogger("try libro").log(Level.INFO,"id sbagliato !!");
                throw new IdException(" id libro sbagliato or duplicated");
            }catch (IdException e)
            {
                Logger.getLogger("catch libro").log(Level.SEVERE,"remove libro...");
                //rimuovo e se lista vuota
                removeLibroById(l);
            }
        }
        return inserimentoLibro(this.fdL,l);

    }

    private static synchronized List<Libro> returnLibriByTAE(File fd,String tit,String aut,String edit,int id) throws IOException, CsvValidationException {
        String[] gVector;
        List<Libro> list=new ArrayList<>();
        boolean recordFound;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {

            while ((gVector = csvReader.readNext()) != null) {
    
                    recordFound = gVector[GETINDEXTITOLOL].equals(tit) || gVector[GETINDEXAUTOREL].equals(aut) || gVector[GETINDEXEDITOREL].equals(edit)
                            || gVector[GETINDEXIDL].equals(String.valueOf(id)) || gVector[GETINDEXIDL].equals(String.valueOf(vis.getId()));

                if (recordFound) {

                    Libro l = getLibro(gVector);
                    list.add(l);

                }


            }
        }

        return list;


    }
    private static synchronized int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
       
        String []gVector;
        int id=0;
        switch (vis.getType())
        {
            case LIBRO-> {
                try (CSVReader reader = new CSVReader(new FileReader(LOCATIONL))) {
                    while ((gVector = reader.readNext()) != null)
                        id = Integer.parseInt(gVector[GETINDEXIDL]);
                }
            }
            case GIORNALE -> {
                try (CSVReader reader = new CSVReader(new FileReader(LOCATIONG))) {
                    while ((gVector = reader.readNext()) != null)
                        id = Integer.parseInt(gVector[GETINDEXIDG]);
                }
            }
            case RIVISTA -> {
                try (CSVReader reader = new CSVReader(new FileReader(LOCATIONR))) {
                    while ((gVector = reader.readNext()) != null)
                        id = Integer.parseInt(gVector[GETINDEXIDR]);
                }
            }
            default -> throw new IOException(" id/type is wrong");
        }
        return id;


    }
    private static synchronized boolean inserimentoLibro(File fd, Libro l) throws IOException, CsvValidationException {

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
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
        }

        return getIdMax()!=0;

    }
        @Override
    public boolean removeLibroById(Libro l) throws CsvValidationException, IOException {

        synchronized (this.cacheLibro) {
            this.cacheLibro.remove(l.getId());
        }
        return removeLibroId(this.fdL, l);

        }
    private static synchronized boolean removeLibroId(File fd, Libro l) throws IOException, CsvValidationException {
         return deleteByType(LIBRO,l,null,null,fd);
    }

    @Override
    public boolean inserisciGiornale(Giornale g) throws IOException, CsvValidationException {

        boolean duplicatedG=false;
        boolean duplicatedT=false;
        boolean duplicatedE=false;
        synchronized (this.cacheGiornale)
        {
            for(Map.Entry<Integer,Giornale>mapG:this.cacheGiornale.entrySet())
            {
                if(mapG.getValue().getTitolo()!=null)
                    duplicatedT=mapG.getValue().getTitolo()!=null;
                if(mapG.getValue().getEditore()!=null)
                    duplicatedE=mapG.getValue().getEditore()!=null;
                duplicatedG=duplicatedT&&duplicatedE;

            }

        }
        if(!duplicatedG)
        {
            List<Giornale> list=returnGiornaleByTE(this.fdG,g.getTitolo(),g.getEditore(),g.getId());
            duplicatedG=(!list.isEmpty());
        }
        if(duplicatedG)
            try{
                Logger.getLogger("try giornale").log(Level.INFO,"id giornale sbagliato !!");
                throw new IdException(" id giornale sbagliato or duplicated");
            }catch (IdException e)
            {
                Logger.getLogger("catch giornale").log(Level.SEVERE,"remove giornale...");
                //rimuovo e se lista vuota
                removeGiornaleById(g);
            }
        return inserimentoGiornale(this.fdG,g);
    }
    private static synchronized boolean inserimentoGiornale(File fd,Giornale g) throws IOException, CsvValidationException {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] gVector = new String[9];


            gVector[GETINDEXTITOLOG] = g.getTitolo();
            gVector[GETINDEXTIPOLOGIAG] = g.getCategoria();
            gVector[GETINDEXLINGUAG] = g.getLingua();
            gVector[GETINDEXEDITOREG] = g.getEditore();
            gVector[GETINDEXDATAG] = String.valueOf(g.getDataPubb());
            gVector[GETINDEXCOPIERG] = String.valueOf(g.getCopieRimanenti());
            gVector[GETINDEXDISPG] = String.valueOf(g.getDisponibilita());
            gVector[GETINDEXPREZZOG] = String.valueOf(g.getPrezzo());
            gVector[GETINDEXIDG] = String.valueOf(getIdMax() + 1);
            csvWriter.writeNext(gVector);
            csvWriter.flush();
        }
        return getIdMax()!=0;
    }


    private static synchronized List<Giornale> returnGiornaleByTE(File fd,String tit,String edit,int id) throws IOException, CsvValidationException {
        List<Giornale> list;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            boolean recordFound;

            list = new ArrayList<>();

            while ((gVector = csvReader.readNext()) != null) {

                recordFound = gVector[GETINDEXTITOLOG].equals(tit) || gVector[GETINDEXEDITOREG].equals(edit) || gVector[GETINDEXIDG].equals(String.valueOf(id))
                        || gVector[GETINDEXIDG].equals(String.valueOf(vis.getId()));

                if (recordFound) {

                    Giornale g = getGiornale(gVector);

                    list.add(g);
                }
            }
        }

        return list;

    }

    private static @NotNull Libro getLibro(String[] gVector)
    {
        String titolo = gVector[GETINDEXTITOLOL];
        String numeroPagine = gVector[GETINDEXNRPL];
        String isbn = gVector[GETINDEXISBNL];
        String editore = gVector[GETINDEXEDITOREL];
        String autore = gVector[GETINDEXAUTOREL];
        String lingua = gVector[GETINDEXLINGUAL];
        String categoria = gVector[GETINDEXCATEGORIAL];
        String data = gVector[GETINDEXDATAL];
        String recensione = gVector[GETINDEXRECENSIONEL];
        String copie = gVector[GETINDEXCOPIEL];
        String desc = gVector[GETINDEXDESCL];
        String disp = gVector[GETINDEXDISPL];
        String prezzo = gVector[GETINDEXPREZZOL];
        String idL=gVector[GETINDEXIDL];



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
        l.setId(Integer.parseInt(idL));

        return l;
    }


    private static @NotNull Giornale getGiornale(String[] gVector) {
        String titolo = gVector[GETINDEXTITOLOG];
        String tipologia = gVector[GETINDEXTIPOLOGIAG];
        String lingua = gVector[GETINDEXLINGUAG];
        String ed = gVector[GETINDEXEDITOREG];
        String data = gVector[GETINDEXDATAG];
        String copie = gVector[GETINDEXCOPIERG];
        String disp = gVector[GETINDEXDISPG];
        String prezzo = gVector[GETINDEXPREZZOG];
        String id=gVector[GETINDEXIDG];

        Giornale g = new Giornale();

        g.setTitolo(titolo);
        g.setCategoria(tipologia);
        g.setLingua(lingua);
        g.setEditore(ed);
        g.setDataPubb(LocalDate.parse(data));
        g.setCopieRimanenti(Integer.parseInt(copie));
        g.setDisponibilita(Integer.parseInt(disp));
        g.setPrezzo(Float.parseFloat(prezzo));
        g.setId(Integer.parseInt(id));
        return g;
    }

    private static @NotNull Rivista getRivista(String[] gVector)
    {
        String titolo = gVector[GETINDEXTITOLOR];
        String tipologia = gVector[GETINDEXTIPOLOGIAR];
        String autore = gVector[GETINDEXAUTORER];
        String lingua = gVector[GETINDEXLINGUAR];
        String editore = gVector[GETINDEXEDITORER];
        String desc = gVector[GETINDEXDESCRIZIONER];
        String data = gVector[GETINDEXDATAR];
        String disp = gVector[GETINDEXDISPR];
        String prezzo = gVector[GETINDEXPREZZOR];
        String copie = gVector[GETINDEXCOPIER];
        String id = gVector[GETINDEXIDR];

        Rivista r=new Rivista();

        r.setTitolo(titolo);
        r.setCategoria(tipologia);
        r.setAutore(autore);
        r.setLingua(lingua);
        r.setEditore(editore);
        r.setDescrizione(desc);
        r.setDataPubb(LocalDate.parse(data));
        r.setDisp(Integer.parseInt(disp));
        r.setPrezzo(Float.parseFloat(prezzo));
        r.setCopieRim(Integer.parseInt(copie));
        r.setId(Integer.parseInt(id));

        return r;

    }

    @Override
    public boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException {
        synchronized (this.cacheGiornale) {
            this.cacheGiornale.remove(g.getId(),g);
        }
        return removeGiornaleId(this.fdG, g);

    }
    private static synchronized boolean removeGiornaleId(File fd,Giornale g) throws IOException, CsvValidationException {
        return deleteByType(GIORNALE,null,g,null,fd);

    }

    @Override
    public boolean inserisciRivista(Rivista r) throws CsvValidationException, IOException {

        boolean duplicatedR=false;
        boolean duplicatedT=false;
        boolean duplicatedA=false;
        boolean duplicatedE=false;
        synchronized (this.cacheRivista)
        {
            for(Map.Entry<Integer,Rivista>mapR:this.cacheRivista.entrySet())
            {
                if(mapR.getValue().getTitolo()!=null)
                    duplicatedT=mapR.getValue().getTitolo()!=null;
                if(mapR.getValue().getAutore()!=null)
                    duplicatedA=mapR.getValue().getAutore()!=null;
                if(mapR.getValue().getEditore()!=null)
                    duplicatedE=mapR.getValue().getEditore()!=null;
                duplicatedR=duplicatedT&&duplicatedA&&duplicatedE;

            }


        }
        if(!duplicatedR)
        {
            List<Rivista> list=returnRivistaByTAE(this.fdR,r.getTitolo(),r.getAutore(),r.getEditore());
            duplicatedR=(!list.isEmpty());
        }
        if(duplicatedR)
            try{
                Logger.getLogger("try rivista").log(Level.INFO,"id rivista sbagliato !!");
                throw new IdException(" id rivista sbagliato or duplicated");
            }catch (IdException e)
            {
                Logger.getLogger("catch rivista").log(Level.SEVERE,"remove rivista...");
                //rimuovo e se lista vuota
                removeRivistaById(r);
            }
        return inserimentoRivista(this.fdR,r);
    }
    private static synchronized boolean inserimentoRivista(File fd,Rivista r) throws IOException, CsvValidationException {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(fd, true)))) {
            String[] gVector = new String[11];


            gVector[GETINDEXTITOLOR] = r.getTitolo();
            gVector[GETINDEXTIPOLOGIAR] = r.getCategoria();
            gVector[GETINDEXAUTORER] = r.getAutore();
            gVector[GETINDEXLINGUAR] = r.getLingua();
            gVector[GETINDEXEDITORER] = r.getEditore();
            gVector[GETINDEXDESCRIZIONER] = r.getDescrizione();
            gVector[GETINDEXDATAR] = String.valueOf(r.getDataPubb());
            gVector[GETINDEXDISPR] = String.valueOf(r.getDisp());
            gVector[GETINDEXPREZZOR] = String.valueOf(r.getPrezzo());
            gVector[GETINDEXCOPIER] = String.valueOf(r.getCopieRim());
            gVector[GETINDEXIDR] = String.valueOf(getIdMax() + 1);

            csvWriter.writeNext(gVector);
            csvWriter.flush();
        }
        return getIdMax()!=0;
    }

    private static synchronized List<Rivista> returnRivistaByTAE(File fd,String tit,String autor,String edit) throws IOException, CsvValidationException{
        List<Rivista> rivistaList;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            boolean recordFound;

            rivistaList = new ArrayList<>();
            while ((gVector = csvReader.readNext()) != null) {

                recordFound = gVector[GETINDEXTITOLOR].equals(tit) || gVector[GETINDEXAUTORER].equals(autor)
                        || gVector[GETINDEXEDITORER].equals(edit) || gVector[GETINDEXIDR].equals(String.valueOf(vis.getId()));
                if (recordFound) {

                    String titolo = gVector[GETINDEXTITOLOR];
                    String tipologia = gVector[GETINDEXTIPOLOGIAR];
                    String aut = gVector[GETINDEXAUTORER];
                    String lingua = gVector[GETINDEXLINGUAR];
                    String ed = gVector[GETINDEXEDITORER];
                    String desc = gVector[GETINDEXDESCRIZIONER];
                    String data = gVector[GETINDEXDATAR];
                    String disp = gVector[GETINDEXDISPR];
                    String prezzo = gVector[GETINDEXPREZZOR];
                    String copieRim = gVector[GETINDEXCOPIER];

                    Rivista r = new Rivista();
                    r.setTitolo(titolo);
                    r.setCategoria(tipologia);
                    r.setAutore(aut);
                    r.setLingua(lingua);
                    r.setEditore(ed);
                    r.setDescrizione(desc);
                    r.setDataPubb(LocalDate.parse(data));
                    r.setDisp(Integer.parseInt(disp));
                    r.setPrezzo(Float.parseFloat(prezzo));
                    r.setCopieRim(Integer.parseInt(copieRim));


                    rivistaList.add(r);

                }
            }
        }
        return rivistaList;



    }
    @Override
    public  boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException {

        synchronized (this.cacheRivista) {
            this.cacheRivista.remove(r.getId());
        }
       return removeRivistaId(this.fdR,r);
    }
    private static synchronized boolean removeRivistaId(File fd,Rivista r) throws IOException, CsvValidationException {
       return deleteByType(RIVISTA,null,null,r,fd);
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData(File fd) throws CsvValidationException, IOException, IdException {
        return retrieveData(fd);

    }
    private static synchronized ObservableList<Raccolta> retrieveData(File fd) throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> gList = FXCollections.observableArrayList();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;

            switch (vis.getType()) {
                case LIBRO -> {

                    while ((gVector = csvReader.readNext()) != null) {

                        gList.add(getLibro(gVector));

                    }
                    if (gList.isEmpty()) {
                        throw new IdException("list libro is empty");
                    }
                }

                case GIORNALE -> {
                    while ((gVector = csvReader.readNext()) != null) {
                        gList.add(getGiornale(gVector));

                    }
                    if (gList.isEmpty()) {
                        throw new IdException("lista giornale is empty");
                    }
                }
                case RIVISTA -> {
                    while ((gVector = csvReader.readNext()) != null) {
                        gList.add(getRivista(gVector));

                    }
                    if (gList.isEmpty()) {
                        throw new IdException("lista rivista is empty");
                    }

                }
                default -> throw new IdException("ogegtto sbagliato || lista empty");
            }
        }

        return gList;

}

    @Override
    public List<Libro> retrieveLibroData(Libro l) throws CsvValidationException, IOException {
        List<Libro> list=new ArrayList<>();
        synchronized (this.cacheLibro)
        {
            for(Map.Entry<Integer, Libro> id:this.cacheLibro.entrySet())
            {
                boolean recordFound=id.getValue().getTitolo().equals(l.getTitolo());
                if(recordFound) {
                    list.add(id.getValue());
                }
            }
        }
        if(list.isEmpty())
        {
            list=returnLibriByTAE(this.fdL,l.getTitolo(),l.getAutore(),l.getEditore(),l.getId());
            synchronized (this.cacheLibro)
            {
                for(Libro libro : list)
                    this.cacheLibro.put(l.getId(),libro);
            }

        }
        return list;
    }

    @Override
    public List<Giornale> retriveGiornaleData(Giornale g) throws CsvValidationException, IOException {
        List<Giornale> list=new ArrayList<>();
        synchronized (this.cacheGiornale)
        {
            for(Map.Entry<Integer, Giornale> id:this.cacheGiornale.entrySet())
            {
                boolean recordFound=id.getValue().getTitolo().equals(g.getTitolo());
                if(recordFound) {


                    list.add(id.getValue());
                }
            }
        }
        if(list.isEmpty())
        {
            list=returnGiornaleByTE(this.fdG,g.getTitolo(),g.getEditore(),g.getId());
            synchronized (this.cacheGiornale)
            {
                for(Giornale giornale : list)
                    this.cacheGiornale.put(g.getId(),giornale);
            }

        }
        return list;
    }

    @Override
    public List<Rivista> retrieveRivistaData( Rivista r) throws CsvValidationException, IOException {
        List<Rivista> list=new ArrayList<>();
        synchronized (this.cacheRivista)
        {
            for(Map.Entry<Integer, Rivista> id:this.cacheRivista.entrySet())
            {
                boolean recordFound=id.getValue().getTitolo().equals(r.getTitolo());
                if(recordFound) {


                    list.add(id.getValue());
                }
            }
        }
        if(list.isEmpty())
        {
            list=returnRivistaByTAE(this.fdR,r.getTitolo(),r.getAutore(),r.getEditore());
            synchronized (this.cacheRivista)
            {
                for(Rivista rivista : list)
                    this.cacheRivista.put(r.getId(),rivista);
            }

        }
        return list;
    }


    @Override
    public ObservableList<Libro> getLibroByIdTitoloAutore(Libro l) throws CsvValidationException, IOException, IdException {
        ObservableList<Libro> list=FXCollections.observableArrayList();
        synchronized (this.cacheLibro)
        {
            for(Map.Entry<Integer, Libro> id:this.cacheLibro.entrySet())
            {

                    boolean recordT =id.getValue().getTitolo().equals(l.getTitolo());
                    boolean recordA = id.getValue().getTitolo().equals(l.getAutore());
                   boolean recordFound = recordT && recordA;


                if (recordFound)
                      list.add(id.getValue());

            }
        }
        if(list.isEmpty())
        {
            list=retrieveLibroByIdAutoreTitolo(this.fdL,l);
            synchronized (this.cacheLibro)
            {
                for(Libro libro : list)
                    this.cacheLibro.put(l.getId(),libro);
            }

        }
        return list;


    }
    private static synchronized ObservableList<Libro> retrieveLibroByIdAutoreTitolo(File fd,Libro libro) throws IOException, CsvValidationException, IdException {
        ObservableList<Libro> list;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();


            while ((gVector = csvReader.readNext()) != null) {


                boolean recordFound = gVector[GETINDEXIDL].equals(String.valueOf(libro.getId())) || gVector[GETINDEXIDL].equals(String.valueOf(vis.getId()))
                        || gVector[GETINDEXTITOLOL].equals(libro.getTitolo()) || gVector[GETINDEXAUTOREL].equals(libro.getAutore())
                        || gVector[GETINDEXEDITOREL].equals(libro.getEditore());
                if (recordFound) {


                    list.add(getLibro(gVector));

                }

            }
        }
        if (list.isEmpty()) {
            throw new IdException(USERNOTFOUND);
        }

        return list;

    }


    @Override
    public ObservableList<Giornale> getGiornaleByIdTitoloEditore( Giornale g) throws CsvValidationException, IOException, IdException {
        ObservableList<Giornale> list=FXCollections.observableArrayList();
        synchronized (this.cacheGiornale) {

                for (Map.Entry<Integer, Giornale> id : this.cacheGiornale.entrySet()) {


                        boolean recordT = id.getValue().getTitolo().equals(g.getTitolo());
                        boolean recordA = id.getValue().getTitolo().equals(g.getEditore());
                        boolean recordFound = recordT && recordA;

                        if (recordFound)
                            list.add(id.getValue());
                    }



        }
        if(list.isEmpty())
        {
            list=retrieveGiornaleByIdTitoloEditore(this.fdG,g);
            synchronized (this.cacheGiornale)
            {
                for(Giornale giornale : list)
                    this.cacheGiornale.put(g.getId(),giornale);
            }

        }
        return list;

    }
    private static synchronized ObservableList<Giornale> retrieveGiornaleByIdTitoloEditore(File fd,Giornale giornale) throws IOException, CsvValidationException, IdException {
        ObservableList<Giornale> list;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;

            list = FXCollections.observableArrayList();

            while ((gVector = csvReader.readNext()) != null) {

                boolean recordFound = gVector[GETINDEXIDG].equals(String.valueOf(giornale.getId())) || gVector[GETINDEXIDG].equals(String.valueOf(vis.getId()))
                        || gVector[GETINDEXTITOLOG].equals(giornale.getTitolo()) || gVector[GETINDEXEDITOREG].equals(giornale.getEditore());
                if (recordFound) {


                    list.add(getGiornale(gVector));
                }
            }
        }
        if (list.isEmpty()) {
            throw new IdException(USERNOTFOUND);
        }


        return list;

    }

    @Override
    public ObservableList<Rivista> getRivistaByIdTitoloEditore( Rivista r) throws CsvValidationException, IOException, IdException {
        ObservableList<Rivista> list=FXCollections.observableArrayList();
        synchronized (this.cacheRivista)
        {
            for(Map.Entry<Integer, Rivista> id: cacheRivista.entrySet())
            {
                boolean recordT=id.getValue().getTitolo().equals(r.getTitolo());
                boolean recordA=id.getValue().getTitolo().equals(r.getEditore());
                boolean recordFound=recordT&&recordA;

                if(recordFound)
                    list.add(id.getValue());
            }
        }
        if(list.isEmpty())
        {
            list=retrieveRivistaByIdTitoloEditore(this.fdR,r);
            synchronized (this.cacheRivista)
            {
                for(Rivista rivista : list)
                    this.cacheRivista.put(r.getId(),rivista);
            }

        }
        return list;
    }
    private static synchronized ObservableList<Rivista> retrieveRivistaByIdTitoloEditore(File fd,Rivista rivista) throws CsvValidationException, IOException, IdException {
        ObservableList<Rivista> rivistaList;
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;

            rivistaList = FXCollections.observableArrayList();
            while ((gVector = csvReader.readNext()) != null) {
                boolean recordFound = gVector[GETINDEXIDR].equals(String.valueOf(rivista.getId())) || gVector[GETINDEXIDR].equals(String.valueOf(vis.getId()))
                        || gVector[GETINDEXTITOLOR].equals(rivista.getTitolo()) || gVector[GETINDEXAUTORER].equals(rivista.getAutore());
                if (recordFound) {

                    rivistaList.add(getRivista(gVector));

                }
            }
        }
        if (rivistaList.isEmpty()) {
            throw new IdException(USERNOTFOUND);
        }

        return rivistaList;


    }


    //used for reduce duplication
   private static synchronized  boolean deleteByType(String type,Libro l,Giornale g,Rivista r,File fd) throws IOException, CsvValidationException {
       boolean status=false;
        if (SystemUtils.IS_OS_UNIX) {
           FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
           Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
       }
       File tmpFile = new File(APPOGGIO);
       boolean found = false;
       try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)));
            CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true))))
         {
           String[] gVector;
           boolean recordFound;
           while ((gVector = reader.readNext()) != null) {

               switch (type) {
                   case LIBRO -> recordFound = gVector[GETINDEXIDL].equals(String.valueOf(l.getId())) ||
                           gVector[GETINDEXTITOLOL].equals(l.getTitolo()) ||
                           gVector[GETINDEXIDL].equals(String.valueOf(vis.getId()));
                   case GIORNALE -> recordFound = gVector[GETINDEXIDG].equals(String.valueOf(g.getId()))
                           || gVector[GETINDEXIDG].equals(String.valueOf(vis.getId()))
                           || gVector[GETINDEXTITOLOG].equals(g.getTitolo());
                   case RIVISTA -> recordFound = gVector[GETINDEXIDR].equals(String.valueOf(r.getId()))
                           || gVector[GETINDEXIDR].equals(String.valueOf(vis.getId()))
                           || gVector[GETINDEXTITOLOR].equals(r.getTitolo());
                   default -> throw new IOException(" wrong type of object");
               }
               if (!recordFound)
                   writer.writeNext(gVector);
               else
                   found = true;
           }
           writer.flush();
       }
       if (found) {
           Files.move(tmpFile.toPath(), fd.toPath(), REPLACE_EXISTING);
           status=true;

       } else {
           cleanUp(Path.of(tmpFile.toURI()));
       }
       return status;

   }

}


