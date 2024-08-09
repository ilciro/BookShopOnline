package laptop.database.csvPagamento;

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
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
    private static final String USERNOTFOUND = " user not found -> id null";


    public FatturaPagamentoCCredito()
    {
        this.cacheFattura=new HashMap<>();
        this.cacheCc=new HashMap<>();
    }



    @Override
    public void inserisciFattura(Fattura f) throws CsvValidationException, IOException {
        boolean duplicated;
        synchronized (this.cacheFattura)
        {
            duplicated=this.cacheFattura.get(f.getNumero())!=null;
        }
        if(duplicated)
        {
            try {
                List<Fattura> listFattura = retrieveFatturaById(this.fileFattura,f);
                duplicated = (!listFattura.isEmpty());
            }catch (IdException e) {
                e.getCause();
            }
        }
                creaFattura(this.fileFattura,f);



    }



    @Override
    public void inserisciCartaCredito(CartaDiCredito cc) throws CsvValidationException, IOException, IdException {
        boolean duplicated;
        synchronized (this.cacheCc)
        {
            duplicated=(this.cacheCc.get(cc.getNumeroCC())!=null);
        }
        if(duplicated)
        {
            try{
                List <CartaDiCredito> listCC=retrieveCcByNumero(this.fileCC,cc);
                duplicated=(!listCC.isEmpty());

            }catch (IdException e)
            {
              duplicated=false;
            }

        }

                creaCC(cc);



    }



    @Override
    public void inserisciPagamento(Pagamento p) throws CsvValidationException, IOException {

                creaPagamento(p);


    }

    @Override
    public ObservableList<CartaDiCredito> getAllDataCredito(String nome) throws CsvValidationException, IOException, IdException {
        return listCarteCredito(nome);

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

    private static int getIdMax() throws IOException, CsvValidationException {
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

    private static synchronized void creaPagamento(Pagamento p) throws IOException, CsvValidationException {
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(PAGAMENTO,true)));
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
            boolean recordFound=gVector[GETINDEXIDF].equals(String.valueOf(fattura.getNumero()));
            if(recordFound)
            {
                f.setNome(gVector[GETINDEXNOMEF]);
                f.setCognome(gVector[GETINDEXCOGNOMEF]);
                f.setVia(gVector[GETINDEXVIAF]);
                f.setCom(gVector[GETINDEXCOMF]);
                f.setAmmontare(Float.parseFloat(gVector[GETINDEXAMMONTAREF]));
                f.setNumero(String.valueOf(gVector[GETINDEXIDF]));
                list.add(f);

            }
        }
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




}
