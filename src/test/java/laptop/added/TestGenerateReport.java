package laptop.added;

import laptop.database.GenerateDaoReportClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestGenerateReport {
    private final GenerateDaoReportClass gRC=new GenerateDaoReportClass();

    @ParameterizedTest
    @ValueSource(strings = {"libro","giornale","rivista","utenti"})
    void testGeneraView(String strings) throws SQLException {
        assertNotNull(gRC.getReportView(strings));
    }

}
