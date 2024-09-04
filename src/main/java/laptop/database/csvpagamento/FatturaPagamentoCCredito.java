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
import org.jetbrains.annotations.NotNull;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static final int GETINDEXESITOP=2;
    private static final int GETINDEXNOMEP=3;
    private static final int GETINDEXSPESAP=4;
    private static final int GETINDEXEIAMILP=5;
    private static final int GETINDEXACQUISTOP=6;
    private static final int GETINDEXIDPRODOTTOP=7;
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

    public FatturaPagamentoCCredito() throws IOException {
        this.fileFattura=new File("report/reportFattura.csv");
        if(!this.fileFattura.exists())
            Files.createFile(Path.of(this.fileFattura.toURI()));
        this.filePagamento=new File("report/reportPagamento.csv");
        if(!this.filePagamento.exists())
            Files.createFile(Path.of(this.filePagamento.toURI()));
        this.fileCartaCredito=new File("report/reportCartaCredito.csv");
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
    public void inserisciFattura(Fattura f) throws CsvValidationException, IOException, IdException {

       //sembra funzionare
        CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fileFattura, true)));

        String[] gVectore = new String[6];

        gVectore[GETINDEXNOMEF] = f.getNome();
        gVectore[GETINDEXCOGNOMEF] = f.getCognome();
        gVectore[GETINDEXVIAF] = f.getVia();
        gVectore[GETINDEXCOMF] = f.getCom();
        gVectore[GETINDEXAMMONTAREF] = String.valueOf(f.getAmmontare());
        gVectore[GETINDEXIDF] = String.valueOf(getIdMax() + 1);
        csvWriter.writeNext(gVectore);

        csvWriter.flush();

        csvWriter.close();



    }



    @Override
    public void inserisciPagamento(Pagamento p) throws IdException, CsvValidationException, IOException {

        creaPagamento(p);
    }



    private static @NotNull Fattura getFattura(String[] gVector) {
        String nomeF= gVector[GETINDEXNOMEF];
        String cognomeF= gVector[GETINDEXCOGNOMEF];
        String via= gVector[GETINDEXVIAF];
        String com= gVector[GETINDEXCOMF];
        String ammontare= gVector[GETINDEXAMMONTAREF];
        String id= gVector[GETINDEXIDF];

        Fattura f=new Fattura();
        f.setNome(nomeF);
        f.setCognome(cognomeF);
        f.setVia(via);
        f.setCom(com);
        f.setAmmontare(Float.parseFloat(ammontare));
        f.setNumero(id);
        return f;
    }


    @Override
    public void cancellaFattura(Fattura f) throws CsvValidationException, IOException {
        synchronized (this.cacheFattura) {
            this.cacheFattura.remove(String.valueOf(f.getNome()));
        }
        removeFattura(this.fileFattura);

    }

    private static synchronized void removeFattura(File fileFattura) throws IOException, CsvValidationException {
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = false;
        CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileFattura)));
        String[] gVector;
        CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)));
        boolean recordFound;
        while ((gVector = reader.readNext()) != null) {

            recordFound=gVector[GETINDEXIDF].equals(String.valueOf(getIdMax()));


            if (!recordFound)
                writer.writeNext(gVector);
            else
                found = true;
        }
        writer.flush();
        reader.close();
        writer.close();
        if (found) {
            Files.move(tmpFile.toPath(), fileFattura.toPath(), REPLACE_EXISTING);
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }


    }




    @Override
    public void cancellaPagamento(Pagamento p) throws CsvValidationException, IOException {
        synchronized (this.cachePagamento) {
            this.cachePagamento.remove(String.valueOf(p.getNomeUtente()));
        }
        removePagamento(this.filePagamento);

    }

    @Override
    public void inserisciCartaCredito(CartaDiCredito cc) throws IOException, CsvValidationException {
        CSVWriter writer=new CSVWriter(new BufferedWriter(new FileWriter(this.fileCartaCredito,true)));
        String[] gVector=new String[7];

        gVector[GETINDEXNOMEPCC]=cc.getNomeUser();
        gVector[GETINDEXCOGNOMEPCC]=cc.getCognomeUser();
        gVector[GETINDEXCODICECARTA]=cc.getNumeroCC();
        gVector[GETINDEXSCADCC]= String.valueOf(cc.getScadenza());
        gVector[GETINDEXPINCC]=cc.getCiv();
        gVector[GETINDEXAMMONTARE]= String.valueOf(cc.getPrezzoTransazine());
        gVector[GETINDEXIDCC]= String.valueOf(getIdMaxCC()+1);
        writer.writeNext(gVector);

        writer.flush();

        writer.close();



    }

    @Override
    public ObservableList<CartaDiCredito> getListaCartaCreditoByNome(File fd, CartaDiCredito cc) throws CsvValidationException, IOException {
        ObservableList<CartaDiCredito> list= FXCollections.observableArrayList();
        synchronized (this.cacheCC)
        {
            for(String id:this.cacheCC.keySet())
            {
                CartaDiCredito recordInCache=this.cacheCC.get(id);
                boolean recordFound=recordInCache.getNumeroCC().equals(cc.getNumeroCC());
                if(recordFound)
                    list.add(recordInCache);
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

    private static synchronized ObservableList<CartaDiCredito> retrieveCartaCreditoByName(File fileCartaCredito, CartaDiCredito cc) throws IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fileCartaCredito)));
        String[] gVector;
        ObservableList<CartaDiCredito> list=FXCollections.observableArrayList();
        while((gVector=reader.readNext())!=null)
        {
            boolean recordFound=gVector[GETINDEXCODICECARTA].equals(cc.getNumeroCC()) || gVector[GETINDEXNOMEPCC].equalsIgnoreCase(cc.getNomeUser());
            if(recordFound)
            {
                CartaDiCredito carta=new CartaDiCredito();
                cc.setNomeUser(gVector[GETINDEXNOMEPCC]);
                cc.setCognomeUser(gVector[GETINDEXCOGNOMEPCC]);
                cc.setNumeroCC(gVector[GETINDEXCODICECARTA]);
                cc.setScadenza(Date.valueOf(gVector[GETINDEXSCADCC]));
                cc.setCiv(gVector[GETINDEXPINCC]);
                cc.setAmmontare(Double.parseDouble(gVector[GETINDEXAMMONTARE]));
                gVector[GETINDEXIDCC]= String.valueOf(getIdMaxCC()+1);
                list.add(carta);


            }
        }
        reader.close();

        return list;
    }

    private static synchronized void removePagamento(File filePagamento) throws IOException, CsvValidationException {
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(PERMESSI));
            Files.createTempFile(PREFIX, SUFFIX, attr); // Compliant
        }
        File tmpFile = new File(APPOGGIO);
        boolean found = false;
        CSVReader reader = new CSVReader(new BufferedReader(new FileReader(filePagamento)));
        String[] gVector;
        CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(tmpFile, true)));
        boolean recordFound;
        while ((gVector = reader.readNext()) != null) {

            recordFound=gVector[GETINDEXIDP].equals(String.valueOf(getIdMax()+2));


            if (!recordFound)
                writer.writeNext(gVector);
            else
                found = true;
        }
        writer.flush();
        reader.close();
        writer.close();
        if (found) {
            Files.move(tmpFile.toPath(), filePagamento.toPath(), REPLACE_EXISTING);
        } else {
            cleanUp(Path.of(tmpFile.toURI()));
        }

    }


    private void creaPagamento(Pagamento p) throws IOException, CsvValidationException {
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamento,true)));
        String[] gVectore=new String[8];
        //fare if su tipo pagamento
        gVectore[GETINDEXIDP]= String.valueOf(getIdMax()+1);
        gVectore[GETINDEXMETODOP]=p.getMetodo();
        gVectore[GETINDEXESITOP]= String.valueOf(p.getEsito());
        gVectore[GETINDEXNOMEP]= p.getNomeUtente();
        gVectore[GETINDEXSPESAP]= String.valueOf(vis.getSpesaT());
        gVectore[GETINDEXEIAMILP]= User.getInstance().getEmail();
        gVectore[GETINDEXACQUISTOP]=p.getTipo();
        gVectore[GETINDEXIDPRODOTTOP]= String.valueOf(p.getIdOggetto());

        csvWriter.writeNext(gVectore);
        csvWriter.flush();
        csvWriter.close();

    }


    private static int getIdMaxCC() throws IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader("report/reportCartaCredito.csv")));
        String []gVector;
        int id=0;

        try {
            while ((gVector = reader.readNext()) != null) {
                id= Integer.parseInt(gVector[GETINDEXIDCC]);

            }
            if(id==0)
                throw new IdException(" id is 0!!");
        }catch(IdException e)
        {
            Logger.getLogger("id worng").log(Level.SEVERE, "id error!!!........\n");

        }
        return id;
    }

    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader;
        String[] gVector;
        int id = 0;


        try {


            if (vis.getMetodoP().equalsIgnoreCase("cash")) {
                reader = new CSVReader(new FileReader("report/reportFattura.csv"));
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDF]);
                }


            } else if (vis.getMetodoP().equalsIgnoreCase("cCredito")) {
                reader = new CSVReader(new FileReader("report/reportPagamento.csv"));
                while ((gVector = reader.readNext()) != null) {
                        id = Integer.parseInt(gVector[GETINDEXIDP]);

                }

            }

            if (id == 0)
                throw new IdException("id == 0 ");

        }catch (IdException  e)
        {

            Logger.getLogger("id worng").log(Level.SEVERE, "id error!!!........\n");

        }

        return id;

    }



}