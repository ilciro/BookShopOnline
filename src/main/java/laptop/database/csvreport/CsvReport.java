package laptop.database.csvreport;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Report;
import org.jetbrains.annotations.NotNull;


import java.io.*;

public class CsvReport implements ReportInterface{

    private static final String LOCATIONR = "report/reportFinale.csv";
    private static final int GETINDEXIDR = 0;
    private static final int GETINDEXTIPOOGG = 1;
    private static final int GETINDEXTITOLOOGG = 2;
    private static final int GETINDEXNRPEZZI = 3;
    private static final int GETINDEXPREZZO = 4;
    private static final int GETINDEXTOTALE = 5;



    private static final File fileReport=new File(LOCATIONR);



    @Override
    public void inserisciReport(Report r) throws CsvValidationException, IOException {

       CSVWriter writer=new CSVWriter(new BufferedWriter(new FileWriter(fileReport,true)));

        String[] gVectore = new String[6];

        gVectore[GETINDEXIDR] = String.valueOf(getIdMax()+1);
        gVectore[GETINDEXTIPOOGG] = r.getTipologiaOggetto();
        gVectore[GETINDEXTITOLOOGG] = r.getTitoloOggetto();
        gVectore[GETINDEXNRPEZZI] = String.valueOf(r.getNrPezzi());
        gVectore[GETINDEXPREZZO] = String.valueOf(r.getPrezzo());
        gVectore[GETINDEXTOTALE] = String.valueOf(r.getTotale());
        writer.writeNext(gVectore);

        writer.flush();

        writer.close();

    }



    public static synchronized ObservableList<Report> returnReportIDTipoTitolo(int id, String tipo, String titolo) throws IOException, CsvValidationException {
        CSVReader reader=new CSVReader(new BufferedReader(new FileReader(fileReport)));
        String[] gVector ;
        boolean recordFound;

        ObservableList<Report> list= FXCollections.observableArrayList();
        while((gVector=reader.readNext())!=null)
        {
            if(tipo==null) {
                Report report = getReport(gVector);
                list.add(report);
            }
            else {
                recordFound = gVector[GETINDEXIDR].equals(String.valueOf(id)) || gVector[GETINDEXTIPOOGG].equals(tipo)
                        || gVector[GETINDEXTITOLOOGG].equals(titolo);
                if (recordFound) {
                    Report report = getReport(gVector);
                    list.add(report);
                }
            }
        }
        return list;
    }

    private static @NotNull Report getReport(String[] gVector) {
        Report report=new Report();
        report.setIdReport(Integer.parseInt(gVector[GETINDEXIDR]));
        report.setTipologiaOggetto(gVector[GETINDEXTIPOOGG]);
        report.setTitoloOggetto(gVector[GETINDEXTITOLOOGG]);
        report.setNrPezzi(Integer.parseInt(gVector[GETINDEXNRPEZZI]));
        report.setPrezzo(Float.parseFloat(gVector[GETINDEXPREZZO]));
        report.setTotale(Float.parseFloat(gVector[GETINDEXNRPEZZI])*Float.parseFloat(gVector[GETINDEXPREZZO]));
        return report;
    }


    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        CSVReader reader;
        String[] gVector;
        int id = 0;


        reader = new CSVReader(new FileReader(LOCATIONR));
        while ((gVector = reader.readNext()) != null) {
            id = Integer.parseInt(gVector[GETINDEXIDR]);
        }
        return id;

    }



}
