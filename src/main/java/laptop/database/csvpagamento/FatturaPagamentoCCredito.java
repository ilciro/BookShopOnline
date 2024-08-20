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



}