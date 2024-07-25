package laptop.database.csvpagamento;

import com.opencsv.CSVWriter;
import laptop.utilities.ConnToDb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FatturaCsv implements PagamentoInterface{

    private final int GETINDEXNOME=0;
    private final int GETINDEXCOGNOME=1;
    private final int GETINDEXVIA=2;
    private final int GETINDEXCOM=3;
    private final int GETINDEXAMMONTARE=4;
    private final int GETINDEXID=5;

    private static final String LOCATION="report/reportFattura.csv";
    private static final String DELETED="file deleted ";
    private static final String GENERAREPORT=" report fattura ";
    private static final String FILECANCELLATO=" file cancellato";
    private static final String FILENOTEXISTS=" file not exists";
    private static final String MAKINGFILE=" making file";
    private static final String ECCEZIONE=" eccezione ottenuta ";

    private static final String QUERY="select nome,cognome,via,comunicazioni,ammontare,idFattura from FATTURA";

    private static final File fd=new File(LOCATION);
    private static void cleanUp(Path path) throws IOException {
        Files.delete(path);
    }


    @Override
    public void report() throws IOException {
        generaReport();
    }

    @Override
    public void inserisci() {
        java.util.logging.Logger.getLogger("Test General connection standard").log(Level.INFO, "Connesso standard a sys........\n");

    }

    private static synchronized void generaReport() throws IOException {
        try {
            cleanUp(Path.of(fd.toURI()));
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATION + DELETED);
            throw new IOException(FILECANCELLATO);
        } catch (IOException e) {
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATION + FILENOTEXISTS);
            if (fd.createNewFile()) {
                Logger.getLogger("report fattura").log(Level.SEVERE, "\n" + LOCATION + MAKINGFILE);
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(QUERY)) {
                    ResultSet rs = prepQ.executeQuery(QUERY);
                    CSVWriter writer = new CSVWriter(new FileWriter(LOCATION));
                    rs.getMetaData();
                    String[] data = new String[6];
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        data[3] = rs.getString(4);
                        data[4] = String.valueOf(rs.getFloat(5));
                        data[5] = String.valueOf(rs.getInt(6));

                        writer.writeNext(data);
                    }
                    writer.flush();
                    writer.close();
                } catch (SQLException | IOException ex) {
                    Logger.getLogger("report fattura exception").log(Level.SEVERE, ECCEZIONE, ex);
                }
            }
        }

    }
}
