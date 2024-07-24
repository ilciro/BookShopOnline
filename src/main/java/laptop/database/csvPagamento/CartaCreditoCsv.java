package laptop.database.csvPagamento;

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

public class CartaCreditoCsv implements PagamentoInterface{

    private final int GETINDEXNOMEP=0;
    private final int GETIDEXCOGNOMEP=1;
    private final int GETINDEXCODICE=2;
    private final int GETINDEXSCAD=3;
    private final int GETINDEXPIN=4;
    private final int GETINDEXAMMONTARE=5;
    private final int GETINDEXIDCARTA=6;

    private static final String LOCATION="report/reportCartaCredito.csv";
    private static final String DELETED="file deleted ";
    private static final String GENERAREPORT=" report fattura ";
    private static final String FILECANCELLATO=" file cancellato";
    private static final String FILENOTEXISTS=" file not exists";
    private static final String MAKINGFILE=" making file";
    private static final String ECCEZIONE=" eccezione ottenuta ";

    private static final String QUERY="select nomeP,cognomeP,codiceCarta,scadenza,pin,ammontare,idCarta from CARTACREDITO";

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

    }

    private static synchronized void generaReport() throws IOException {
        try {
            cleanUp(Path.of(fd.toURI()));
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATION + DELETED);
            throw new IOException(FILECANCELLATO);
        } catch (IOException e) {
            Logger.getLogger(GENERAREPORT).log(Level.SEVERE, "\n " + LOCATION + FILENOTEXISTS);
            if (fd.createNewFile()) {
                Logger.getLogger("report carta credito").log(Level.SEVERE, "\n" + LOCATION + MAKINGFILE);
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(QUERY)) {
                    ResultSet rs = prepQ.executeQuery(QUERY);
                    CSVWriter writer = new CSVWriter(new FileWriter(LOCATION));
                    rs.getMetaData();
                    String[] data = new String[7];
                    while (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        data[3] = String.valueOf(rs.getDate(4));
                        data[4] = rs.getString(5);
                        data[5] = String.valueOf(rs.getFloat(6));
                        data[6]=String.valueOf((rs.getInt(7)));

                        writer.writeNext(data);
                    }
                    writer.flush();
                    writer.close();
                } catch (SQLException | IOException ex) {
                    Logger.getLogger("report carta credito exception").log(Level.SEVERE, ECCEZIONE, ex);
                }
            }
        }

    }
}
