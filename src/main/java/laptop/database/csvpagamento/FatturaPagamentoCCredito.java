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


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




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
    private static final int GETINDEXIDP=0;
    private static final int GETINDEXMETODOP=1;
    private static final int GETINDEXESITOP=2;
    private static final int GETINDEXNOMEP=3;
    private static final int GETINDEXSPESAP=4;
    private static final int GETINDEXEIAMILP=5;
    private static final int GETINDEXACQUISTOP=6;
    private static final int GETINDEXIDPRODOTTOP=7;

    public FatturaPagamentoCCredito() throws IOException {
        this.fileFattura=new File("report/reportFattura.csv");
        if(!this.fileFattura.exists())
            Files.createFile(Path.of(this.fileFattura.toURI()));
        this.filePagamento=new File("report/reportPagamento.csv");
        if(!this.filePagamento.exists())
            Files.createFile(Path.of(this.filePagamento.toURI()));
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



        csvWriter.close();



    }

    @Override
    public void copiaFiles( Pagamento p) throws CsvValidationException, IOException, IdException {

        copia(p);
    }

    @Override
    public void inserisciPagamento(Pagamento p) throws IdException, CsvValidationException, IOException {

        creaPagamento(p);
    }
    private void creaPagamento(Pagamento p) throws IOException, CsvValidationException {
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(filePagamento,true)));
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

    public  synchronized void copia(Pagamento p) throws IOException, CsvValidationException, IdException {
        CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(this.filePagamento, true)));
        String [] gVector1 = new String[8];

        gVector1[GETINDEXIDP] = String.valueOf(getIdMax()+1);
        gVector1[GETINDEXMETODOP] = vis.getMetodoP();
        gVector1[GETINDEXESITOP] = String.valueOf(p.getEsito());
        gVector1[GETINDEXNOMEP] = p.getNomeUtente();
        gVector1[GETINDEXSPESAP] = String.valueOf(p.getAmmontare());
        gVector1[GETINDEXEIAMILP] = User.getInstance().getEmail();
        gVector1[GETINDEXACQUISTOP] = String.valueOf(p.getTipo());
        gVector1[GETINDEXIDPRODOTTOP] = String.valueOf(p.getId());

        writer.writeNext(gVector1);
        writer.flush();
        writer.close();
    }

    private static  List<Fattura> retrieveFattura(File fileFattura) throws IOException, CsvValidationException, IdException {
        CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileFattura)));
        List<Fattura> list=new ArrayList<>();
        String []gVector;
        while((gVector=reader.readNext())!=null) {
            Fattura f = new Fattura();

            f.setNome(gVector[GETINDEXNOMEF]);
            f.setCognome(gVector[GETINDEXCOGNOMEF]);
            f.setVia(gVector[GETINDEXVIAF]);
            f.setCom(gVector[GETINDEXCOMF]);
            f.setAmmontare(Float.parseFloat(gVector[GETINDEXAMMONTAREF]));
            f.setNumero(String.valueOf(gVector[GETINDEXIDF]));
            list.add(f);

        }

        reader.close();



        return list;

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
                        id = Integer.parseInt(gVector[GETINDEXIDP]) + 1;

                }


            }

            if (id == 0)
                throw new IdException("id ==0 ");

        }catch (IdException  e)
        {

            Logger.getLogger("id worng").log(Level.SEVERE, "id error!!!........\n");


        }

        return id;

    }

    /*
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final int GETINDEXNOMEF=0;
    private static final int GETINDEXCOGNOMEF=1;
    private static final int GETINDEXVIAF=2;
    private static final int GETINDEXCOMF=3;
    private static final int GETINDEXAMMONTAREF=4;
    private static final int GETINDEXIDF=5;

    private  final File fileFattura=new File("report/reportFattura.csv");


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
    private static final int GETINDEXCODICECARTACC=2;
    private static final int GETINDEXSCADENZACC=3;
    private static final int GETINDEXPINCC=4;
    private static final int GETINDEXAMMONTARECC=5;
    private static final int GETINDEXIDCC=6;


    private  final  File fileCC=new File(CARTACREDITO);

    private static final String CARTACREDITO="report/reportCartaCredito.csv";
    private static final String PAGAMENTO="report/reportPagamento.csv";




    private static final String IDNULL=" id is null !!";

    private final HashMap<String,Fattura> cacheFattura;
    private final HashMap<String,CartaDiCredito> cacheCc;
    private final HashMap<String,Pagamento> cachePag;
    private static final String USERNOTFOUND = " user not found -> id null";


    public FatturaPagamentoCCredito()
    {
        this.cacheFattura=new HashMap<>();
        this.cacheCc=new HashMap<>();
        this.cachePag=new HashMap<>();
    }



    @Override
    public void inserisciFattura(Fattura f) throws CsvValidationException, IOException, IdException {
        boolean duplicated;
        synchronized (this.cacheFattura)
        {
            duplicated=(this.cacheFattura.get(f.getNome()))!=null;
        }
        if(!duplicated)
        {

            List<Fattura> listFattura = retrieveFatturaById(this.fileFattura,f);
            duplicated = (!listFattura.isEmpty());

        }
        if(duplicated)
            throw new IdException("duplicated id");
        creaFattura(this.fileFattura,f);



    }

    @Override
    public void copiaFiles(File fd1, File fd2, Pagamento p) throws CsvValidationException, IOException {
        copia(fd1,fd2,p);
    }



    @Override
    public void inserisciCartaCredito(CartaDiCredito cc) throws CsvValidationException, IOException, IdException {
        boolean duplicated;
        synchronized (this.cacheCc)
        {
            duplicated=(this.cacheCc.get(cc.getNumeroCC())!=null);
        }
        if(!duplicated)
        {
            List <CartaDiCredito> listCC=retrieveCcByNumero(this.fileCC,cc);
            duplicated=(!listCC.isEmpty());

        }
        if(duplicated)
            throw new IdException(" id is duplicated");

        creaCC(cc);



    }





    @Override
    public void inserisciPagamento(Pagamento p) throws CsvValidationException, IOException, IdException {
        boolean duplicated;

        synchronized (this.cachePag)
        {
            duplicated=(this.cachePag.get(String.valueOf(p.getId()))!=null);
        }
        if(duplicated)
        {
            throw new IdException(" pagamento not found!!");
        }

        //creaPagamento(p);


    }

    @Override
    public List<Fattura> fatturaList(File fd, Fattura f) throws CsvValidationException, IOException, IdException {
        return List.of();
    }

    @Override
    public ObservableList<CartaDiCredito> getAllDataCredito(String nome) throws CsvValidationException, IOException, IdException {

        ObservableList<CartaDiCredito> list=FXCollections.observableArrayList();
        synchronized (this.cacheCc)
        {
            for (String id:this.cacheCc.keySet())
            {
                CartaDiCredito cc=this.cacheCc.get(id);
                boolean recordFound=cc.getNomeUser().equals(nome);
                if(recordFound)
                    list.add(cc);
            }
        }
        if(list.isEmpty())
        {
            list=listCarteCredito(nome);
            synchronized (this.cacheCc)
            {
                for (CartaDiCredito cc:list)
                    this.cacheCc.put(nome,cc);
            }
        }
        return list;

    }







    private static synchronized void creaFattura(File fd,Fattura f) throws IOException, CsvValidationException {
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(fd,true)));
        String[] gVectore=new String[6];
        gVectore[GETINDEXNOMEF]=f.getNome();
        gVectore[GETINDEXCOGNOMEF]=f.getCognome();
        gVectore[GETINDEXVIAF]=f.getVia();
        gVectore[GETINDEXCOMF]=f.getCom();
        gVectore[GETINDEXAMMONTAREF]= String.valueOf(f.getAmmontare());
        gVectore[GETINDEXIDF]= String.valueOf(getIdMax()+1);
        csvWriter.writeNext(gVectore);
        csvWriter.flush();
        csvWriter.close();
    }

    private static synchronized int getIdMax() throws IOException, CsvValidationException {
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
                reader = new CSVReader(new FileReader(PAGAMENTO));
                String lastLine="";
                while ((gVector = reader.readNext()) != null) {
                    lastLine=reader.toString();
                    if(lastLine!=null)
                        id = Integer.parseInt(gVector[GETINDEXIDP])+1;

                }


            }



            if (id == 0)
                throw new IdException(IDNULL);


        }catch (IdException  e)
        {

            Logger.getLogger("id worng").log(Level.SEVERE, "id error!!!........\n");


        }

        return id;

    }

    private static synchronized void creaCC(CartaDiCredito cc) throws IOException, CsvValidationException {
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(CARTACREDITO,true)));
        String[] gVectore=new String[7];
        gVectore[GETINDEXNOMEPCC]=cc.getNomeUser();
        gVectore[GETINDEXCOGNOMEPCC]=cc.getCognomeUser();
        gVectore[GETINDEXCODICECARTACC]=cc.getNumeroCC();
        gVectore[GETINDEXSCADENZACC]= String.valueOf(cc.getScadenza());
        gVectore[GETINDEXPINCC]=cc.getCiv();
        gVectore[GETINDEXAMMONTARECC]= String.valueOf(cc.getAmmontare());
        gVectore[GETINDEXIDCC]= String.valueOf(getIdMax()+1);
        csvWriter.writeNext(gVectore);
        csvWriter.flush();
        csvWriter.close();
    }





    private static synchronized ObservableList<CartaDiCredito> listCarteCredito(String nome) throws CsvValidationException, IOException, IdException {
        ObservableList<CartaDiCredito> gList = FXCollections.observableArrayList();
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(CARTACREDITO)));
        String[] gVEctor;


        while ((gVEctor = csvReader.readNext()) != null) {
            boolean found=gVEctor[GETINDEXNOMEPCC].equals(nome);
            if(found) {
                String nomeU = gVEctor[GETINDEXNOMEPCC];
                String cognome = gVEctor[GETINDEXCOGNOMEPCC];
                String codice = gVEctor[GETINDEXCODICECARTACC];
                String scadenza = gVEctor[GETINDEXSCADENZACC];
                String pin = gVEctor[GETINDEXPINCC];
                String ammontare = gVEctor[GETINDEXAMMONTARECC];

                CartaDiCredito cc=new CartaDiCredito();
                cc.setNomeUser(nomeU);
                cc.setCognomeUser(cognome);
                cc.setNumeroCC(codice);
                cc.setScadenza(Date.valueOf(LocalDate.parse(scadenza)));
                cc.setCiv(pin);
                cc.setAmmontare(Double.parseDouble(ammontare));



                gList.add(cc);
            }

        }
        csvReader.close();
        if (gList.isEmpty()) {
            throw new IdException("CartaCredito non trovate!!");
        }
        return gList;
    }



    public  synchronized void copia(File fd,File fd1,Pagamento p) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fd)));
        CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(fd1, true)));
        String [] gVector1 = new String[8];

        String lastLine="";


        while (reader.readNext()!= null ) {

            lastLine= reader.toString();


            if(lastLine!=null) {


                gVector1[GETINDEXIDP] = String.valueOf(getIdMax());
                gVector1[GETINDEXMETODOP] = vis.getMetodoP();
                gVector1[GETINDEXESITOP] = String.valueOf(p.getEsito());
                gVector1[GETINDEXNOMEP] = p.getNomeUtente();
                gVector1[GETINDEXSPESAP] = String.valueOf(p.getAmmontare());
                gVector1[GETINDEXEIAMILP] = User.getInstance().getEmail();
                gVector1[GETINDEXACQUISTOP] = String.valueOf(p.getTipo());
                gVector1[GETINDEXIDPRODOTTOP] = String.valueOf(p.getId());
            }


        }

        writer.writeNext(gVector1);
        writer.flush();
        writer.close();
    }


    private static synchronized List<Fattura> retrieveFatturaById(File fileFattura,Fattura fattura) throws IOException, CsvValidationException, IdException {
        CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileFattura)));
        List<Fattura> list=new ArrayList<>();
        String []gVector;
        while((gVector=reader.readNext())!=null) {
            Fattura f = new Fattura();
         //   boolean recordFound=gVector[GETINDEXNOMEF].equals(String.valueOf(fattura.getNome()));
           // if(recordFound)
            //{
                f.setNome(gVector[GETINDEXNOMEF]);
                f.setCognome(gVector[GETINDEXCOGNOMEF]);
                f.setVia(gVector[GETINDEXVIAF]);
                f.setCom(gVector[GETINDEXCOMF]);
                f.setAmmontare(Float.parseFloat(gVector[GETINDEXAMMONTAREF]));
                f.setNumero(String.valueOf(gVector[GETINDEXIDF]));
                list.add(f);

            }
       // }
        reader.close();
        if (list.isEmpty()) {
            throw new IdException(USERNOTFOUND);
        }

        return list;

    }

    private static  synchronized  List<CartaDiCredito> retrieveCcByNumero(File fileCC, CartaDiCredito cartaC) throws IOException, CsvValidationException, IdException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fileCC)));
        String []gVector;
        List <CartaDiCredito> list=new ArrayList<>();
        while ((gVector=reader.readNext())!=null)
        {
            CartaDiCredito cc=new CartaDiCredito();
            boolean recordFound=gVector[GETINDEXCODICECARTACC].equals(String.valueOf(cartaC.getNumeroCC()));
            if(recordFound)
            {
                cc.setNomeUser(gVector[GETINDEXNOMEPCC]);
                cc.setCognomeUser(gVector[GETINDEXCOGNOMEPCC]);
                cc.setNumeroCC(gVector[GETINDEXCODICECARTACC]);
                cc.setScadenza(Date.valueOf(gVector[GETINDEXSCADENZACC]));
                cc.setCiv(gVector[GETINDEXPINCC]);
                cc.setAmmontare(Double.parseDouble(gVector[GETINDEXAMMONTARECC]));
                //id not set per class config
                list.add(cc);
            }
        }
        reader.close();
        if (list.isEmpty()) {
            throw new IdException(USERNOTFOUND);
        }

        return list;
    }



    */

}