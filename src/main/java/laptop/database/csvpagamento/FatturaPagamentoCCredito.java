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
import java.sql.*;
import java.time.LocalDate;
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

    private static final String LOCATIONF="report/reportFattura.csv";


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


    private static final String LOCATIONCC="report/reportCartaCredito.csv";



    private static final String LOCATIONP="report/reportPagamento.csv";

    private static final String IDNULL=" id is null !!";



    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }




    @Override
    public void inserisciFattura(Fattura f) throws CsvValidationException, IOException {
                creaFattura(f);
    }

    @Override
    public void inserisciCartaCredito(CartaDiCredito cc) throws IOException, CsvValidationException {
        creaCC(cc);
    }

    @Override
    public void inserisciPagamento(Pagamento p) throws IOException, CsvValidationException {
        creaPagamento(p);
    }

    @Override
    public ObservableList<CartaDiCredito> getAllDataCredito(String nome) throws CsvValidationException, IOException, IdException {
        return listCarteCredito(nome);
    }





    private static synchronized void creaFattura(Fattura f) throws IOException, CsvValidationException {
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONF,true)));
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
                reader = new CSVReader(new FileReader(LOCATIONF));
                while ((gVector = reader.readNext()) != null) {

                    id = Integer.parseInt(gVector[GETINDEXIDF]);
                }


            } else if (vis.getMetodoP().equalsIgnoreCase("cCredito")) {
                reader = new CSVReader(new FileReader(LOCATIONCC));
                while ((gVector = reader.readNext()) != null) {
                    id = Integer.parseInt(gVector[GETINDEXIDCC]);

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
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONCC,true)));
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
        CSVWriter csvWriter=new CSVWriter(new BufferedWriter(new FileWriter(LOCATIONP,true)));
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
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(LOCATIONCC)));
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


}
