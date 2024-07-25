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

public class PagamentoCsv implements PagamentoInterface{
    private static final String LOCATION="report/reportPagamento.csv";
    private static final String DELETED="file deleted ";
    private static final String GENERAREPORT=" report fattura ";
    private static final String FILECANCELLATO=" file cancellato";
    private static final String FILENOTEXISTS=" file not exists";
    private static final String MAKINGFILE=" making file";
    private static final String ECCEZIONE=" eccezione ottenuta ";

    private static final String QUERY="select idPagamento,metodo,esito,nomeUtente,spesaTotale,email,tipoAcquisto,idProdotto from PAGAMENTO ";

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
                    String[] data = new String[9];
                    while (rs.next()) {
                        data[0] = String.valueOf(rs.getInt(1));
                        data[1] = rs.getString(2);
                        data[2] = String.valueOf(rs.getInt(3));
                        data[3] = rs.getString(4);
                        data[4] = String.valueOf(rs.getFloat(5));
                        data[5] = rs.getString(6);
                        data[6]=rs.getString(7);
                        data[7]=String.valueOf(rs.getInt(8));

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
