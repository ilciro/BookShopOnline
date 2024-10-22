package laptop.database.csvreport;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.Report;

import java.io.IOException;

public interface ReportInterface {

    void inserisciReport(Report r) throws CsvValidationException, IOException;

}
