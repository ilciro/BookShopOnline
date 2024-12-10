package laptop.database.csvpagamento;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

import laptop.model.CartaDiCredito;
import laptop.model.Fattura;
import laptop.model.Pagamento;
import laptop.model.User;
import org.apache.commons.lang.SystemUtils;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class FatturaPagamentoCCredito implements PagamentoInterface{
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final int GETINDEXNOMEF=0;
    private static final int GETINDEXCOGNOMEF=1;
    private static final int GETINDEXVIAF=2;
    private static final int GETINDEXCOMF=3;
    private static final int GETINDEXAMMONTAREF=4;
    private static final int GETINDEXIDF=5;
    private  final File fileFattura;
    private final File filePagamento;
    private final File fileCartaCredito;
    private static final int GETINDEXIDP=0;
    private static final int GETINDEXMETODOP=1;
    private static final int GETINDEXNOMEP=2;
    private static final int GETINDEXSPESAP=3;
    private static final int GETINDEXEIAMILP=4;
    private static final int GETINDEXACQUISTOP=5;
    private static final int GETINDEXIDPRODOTTOP=6;
    private static final int GETINDEXNOMEPCC=0;
    private static final int GETINDEXCOGNOMEPCC=1;
    private static final int GETINDEXCODICECARTA=2;
    private static final int GETINDEXSCADCC=3;
    private static final int GETINDEXPINCC=4;
    private static final int GETINDEXAMMONTARE=5;
    private static final int GETINDEXIDCC=6;

    private final HashMap<String,Fattura> cacheFattura;
    private final HashMap<String,Pagamento> cachePagamento;
    private final HashMap<String,CartaDiCredito> cacheCC;
    private static final String APPOGGIO="report/appoggio.csv";
    private static final String PERMESSI="rwx------";
    private static final String PREFIX="prefix";
    private static final String SUFFIX="suffix";
    private static final String PAGAMENTO="report/reportPagamento.csv";
    private static final String FATTURA="report/reportFattura.csv";
    private static final String CARTACREDITO="report/reportCartaCredito.csv";
    private static final String IDWRONG=" id wrong ..!!";
    private static final String IDERROR="id error !!..";

    public FatturaPagamentoCCredito() throws IOException {
        this.fileFattura=new File(FATTURA);
        if(!this.fileFattura.exists())
            Files.createFile(Path.of(this.fileFattura.toURI()));
        this.filePagamento=new File(PAGAMENTO);
        if(!this.filePagamento.exists())
            Files.createFile(Path.of(this.filePagamento.toURI()));
        this.fileCartaCredito=new File(CARTACREDITO);
        if(!this.fileCartaCredito.exists())
            Files.createFile(Path.of(this.fileCartaCredito.toURI()));
        this.cacheFattura=new HashMap<>();
        this.cachePagamento=new HashMap<>();
        this.cacheCC=new HashMap<>();
    }


    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public void inserisciFattura(Fattura f) throws CsvValidationException, IOException{

       //sembra funzionare
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fileFattura, true)))) {

            String[] gVectore = new String[6];

            gVectore[GETINDEXNOMEF] = f.getNome();
            gVectore[GETINDEXCOGNOMEF] = f.getCognome();
            gVectore[GETINDEXVIAF] = f.getVia();
            gVectore[GETINDEXCOMF] = f.getCom();
            gVectore[GETINDEXAMMONTAREF] = String.valueOf(f.getAmmontare());
            gVectore[GETINDEXIDF] = String.valueOf(getIdMax() + 1);
            csvWriter.writeNext(gVectore);

            csvWriter.flush();

        }


    }



    @Override
    public void inserisciPagamento(Pagamento p) throws  CsvValidationException, IOException {

        creaPagamento(p);
    }





    @Override
    public void cancellaFattura(Fattura f) throws CsvValidationException, IOException {
        synchronized (this.cacheFattura) {
            this.cacheFattura.remove(String.valueOf(f.getNome()));
        }
        removeFattura(f);

    }

    private static synchronized void removeFattura(Fattura f) throws IOException, CsvValidationException {
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFound(f, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), Path.of(FATTURA), REPLACE_EXISTING);
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }


    }

    private static boolean isFound(Fattura f, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(FATTURA)));
        CSVWriter writer= new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))) {
            String[] gVector;

            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {

                recordFound = gVector[GETINDEXIDF].equals(String.valueOf(f.getIdFattura()));


                if (!recordFound)
                    writer.writeNext(gVector);
                else
                    found = true;
            }
            writer.flush();

        }
        return found;
    }


    @Override
    public boolean cancellaPagamento(Pagamento p) throws CsvValidationException, IOException {
        synchronized (this.cachePagamento) {
            this.cachePagamento.remove(String.valueOf(p.getNomeUtente()));
            this.cachePagamento.remove(String.valueOf(p.getEmail()));
        }
        return removePagamento(p);

    }

    @Override
    public void inserisciCartaCredito(CartaDiCredito cc) throws IOException, CsvValidationException {
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(CARTACREDITO, true)))) {
            String[] gVector = new String[7];

            gVector[GETINDEXNOMEPCC] = cc.getNomeUser();
            gVector[GETINDEXCOGNOMEPCC] = cc.getCognomeUser();
            gVector[GETINDEXCODICECARTA] = cc.getNumeroCC();
            gVector[GETINDEXSCADCC] = String.valueOf(cc.getScadenza());
            gVector[GETINDEXPINCC] = cc.getCiv();
            gVector[GETINDEXAMMONTARE] = String.valueOf(cc.getPrezzoTransazine());
            gVector[GETINDEXIDCC] = String.valueOf(getIdMaxCC() + 1);
            writer.writeNext(gVector);

            writer.flush();

        }


    }

    @Override
    public ObservableList<CartaDiCredito> getListaCartaCreditoByNome(CartaDiCredito cc) throws CsvValidationException, IOException {
        ObservableList<CartaDiCredito> list= FXCollections.observableArrayList();
        synchronized (this.cacheCC)
        {

            for(Map.Entry<String,CartaDiCredito> id:cacheCC.entrySet())
            {
                boolean recordFound=id.getValue().getNumeroCC().equals(cc.getNumeroCC());
                if(recordFound)
                    list.add(id.getValue());
            }
        }
        if(list.isEmpty())
        {
            list=retrieveCartaCreditoByName(this.fileCartaCredito,cc);
            synchronized (this.cacheCC)
            {
                for(CartaDiCredito carta : list)
                    this.cacheCC.put(String.valueOf(cc.getNumeroCC()),carta);
            }

        }
        return list;

    }

    @Override
    public Pagamento ultimoPagamento() throws IOException, CsvValidationException {
        ObservableList<Pagamento> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(this.filePagamento)))) {
            list=FXCollections.observableArrayList();
            String[] gVector;

            while ((gVector = reader.readNext()) != null) {
                Pagamento p = new Pagamento();
                p.setIdPag(Integer.parseInt(gVector[GETINDEXIDP]));
                p.setMetodo(gVector[GETINDEXMETODOP]);
                p.setNomeUtente(gVector[GETINDEXNOMEP]);
                p.setAmmontare(Float.parseFloat(gVector[GETINDEXSPESAP]));
                p.setEmail(gVector[GETINDEXEIAMILP]);
                p.setTipo(gVector[GETINDEXACQUISTOP]);
                p.setIdOggetto(Integer.parseInt(gVector[GETINDEXIDPRODOTTOP]));
                list.add(p);


            }
        }

        return list.get(list.size()-1);
    }

    @Override
    public Fattura ultimaFattura() throws CsvValidationException, IOException {
        ObservableList<Fattura> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(this.fileFattura)))) {
            list = FXCollections.observableArrayList();
            String[] gVector;
            while ((gVector = reader.readNext()) != null) {
                Fattura f = new Fattura();
                f.setNome(gVector[GETINDEXNOMEF]);
                f.setCognome(gVector[GETINDEXCOGNOMEF]);
                f.setVia(gVector[GETINDEXVIAF]);
                f.setCom(gVector[GETINDEXCOMF]);
                f.setAmmontare(Float.parseFloat(gVector[GETINDEXAMMONTAREF]));
                f.setIdFattura(Integer.parseInt(gVector[GETINDEXIDF]));
                list.add(f);


            }
        }
        return list.get(list.size()-1);

    }

    @Override
    public ObservableList<Pagamento> getPagamenti(Pagamento p) throws CsvValidationException, IOException {
        ObservableList<Pagamento> list= FXCollections.observableArrayList();
        synchronized (this.cachePagamento)
        {


            for(Map.Entry<String,Pagamento> id:cachePagamento.entrySet())
            {
                boolean recordFound=(id.getValue().getIdPag()==p.getIdPag());
                if(recordFound)
                    list.add(id.getValue());
            }
        }
        if(list.isEmpty())
        {
            list=retriveListPagamento(this.filePagamento,p);
            synchronized (this.cachePagamento)
            {
                for(Pagamento pagamento : list)
                    this.cachePagamento.put(String.valueOf(p.getEmail()),pagamento);
            }

        }
        return list;

    }

    private static  synchronized  ObservableList<Pagamento> retriveListPagamento(File fd, Pagamento p) throws IOException, CsvValidationException {
        ObservableList<Pagamento> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();
            while ((gVector = reader.readNext()) != null) {
                boolean recordFound = gVector[GETINDEXEIAMILP].equals(p.getEmail());
                if (recordFound) {
                    Pagamento pag = new Pagamento();
                    pag.setIdPag(Integer.parseInt(gVector[GETINDEXIDP]));
                    pag.setMetodo(gVector[GETINDEXMETODOP]);
                    pag.setAmmontare(Float.parseFloat(gVector[GETINDEXSPESAP]));
                    pag.setTipo(gVector[GETINDEXACQUISTOP]);
                    pag.setIdOggetto(Integer.parseInt(gVector[GETINDEXIDPRODOTTOP]));
                    list.add(pag);


                }
            }
        }

        return list;

    }

    private static synchronized ObservableList<CartaDiCredito> retrieveCartaCreditoByName(File fileCartaCredito, CartaDiCredito cc) throws IOException, CsvValidationException {
        ObservableList<CartaDiCredito> list;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileCartaCredito)))) {
            String[] gVector;
            list = FXCollections.observableArrayList();
            while ((gVector = reader.readNext()) != null) {
                boolean recordFound = gVector[GETINDEXCODICECARTA].equals(cc.getNumeroCC()) || gVector[GETINDEXNOMEPCC].equalsIgnoreCase(cc.getNomeUser());
                if (recordFound) {
                    CartaDiCredito carta = new CartaDiCredito();
                    cc.setNomeUser(gVector[GETINDEXNOMEPCC]);
                    cc.setCognomeUser(gVector[GETINDEXCOGNOMEPCC]);
                    cc.setNumeroCC(gVector[GETINDEXCODICECARTA]);
                    cc.setScadenza(Date.valueOf(gVector[GETINDEXSCADCC]));
                    cc.setCiv(gVector[GETINDEXPINCC]);
                    cc.setAmmontare(Double.parseDouble(gVector[GETINDEXAMMONTARE]));
                    gVector[GETINDEXIDCC] = String.valueOf(getIdMaxCC() + 1);
                    list.add(carta);


                }
            }
        }

        return list;
    }

    private static synchronized boolean removePagamento(Pagamento p) throws IOException, CsvValidationException {
        boolean status = false;
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = isFound(p, tmpFile);
        if (found) {
            Files.move(tmpFile.toPath(), Path.of(PAGAMENTO), REPLACE_EXISTING);
            status=true;
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }
        return status;

    }

    private static boolean isFound(Pagamento p, File tmpFile) throws IOException, CsvValidationException {
        boolean found = false;

        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTO)))) {
            String[] gVector;
            try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)))
            ) {
                boolean recordFound;
                while ((gVector = reader.readNext()) != null) {

                    recordFound = gVector[GETINDEXIDP].equals(String.valueOf(p.getIdPag()))
                            || gVector[GETINDEXEIAMILP].equals(p.getEmail());


                    if (!recordFound)
                        writer.writeNext(gVector);
                    else
                        found = true;
                }
                writer.flush();
            }
        }
        return found;
    }


    private void creaPagamento(Pagamento p) throws IOException, CsvValidationException {
        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamento, true)))) {
            String[] gVectore = new String[7];
            //fare if su tipo pagamento

            gVectore[GETINDEXIDP] = String.valueOf(getIdMaxPagamento() + 1);
            gVectore[GETINDEXMETODOP] = p.getMetodo();
            gVectore[GETINDEXNOMEP] = p.getNomeUtente();
            gVectore[GETINDEXSPESAP] = String.valueOf(vis.getSpesaT());
            gVectore[GETINDEXEIAMILP] = User.getInstance().getEmail();
            gVectore[GETINDEXACQUISTOP] = p.getTipo();
            gVectore[GETINDEXIDPRODOTTOP] = String.valueOf(p.getIdOggetto());

            csvWriter.writeNext(gVectore);
            csvWriter.flush();
        }

    }


    private static int getIdMaxCC() throws IOException, CsvValidationException {
        int id;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(CARTACREDITO)))) {
            String[] gVector;
            id = 0;

            try {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDCC]);

                }
                if (id == 0)
                    throw new IdException(" id is 0!!");
            } catch (IdException e) {
                Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

            }
        }
        return id;
    }

    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        String[] gVector;
        int id = 0;


        try {


            if (vis.getMetodoP().equalsIgnoreCase("cash")) {
               try(CSVReader reader = new CSVReader(new FileReader(FATTURA))) {
                   while ((gVector = reader.readNext()) != null) {
                       id = Integer.parseInt(gVector[GETINDEXIDF]);
                   }
               }


            } else if (vis.getMetodoP().equalsIgnoreCase("cCredito")) {
                try (CSVReader reader = new CSVReader(new FileReader(PAGAMENTO))) 
                {
                    while ((gVector = reader.readNext()) != null) {
                        id = Integer.parseInt(gVector[GETINDEXIDP]);

                    }
                }
            

            }

            if (id == 0)
                throw new IdException("id == 0 ");

        }catch (IdException  e)
        {

            Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

        }

        return id;

    }
    private static int getIdMaxPagamento() throws IOException, CsvValidationException {
        int id;
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(PAGAMENTO)))) {
            String[] gVector;
            id = 0;

            try {
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDP]);
                }
                if (id == 0)
                    throw new IdException(" id is 0!!");
            } catch (IdException e) {
                Logger.getLogger(IDWRONG).log(Level.SEVERE, IDERROR);

            }
        }
        return id;
    }




}