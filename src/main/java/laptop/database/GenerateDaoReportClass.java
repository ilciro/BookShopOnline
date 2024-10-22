package laptop.database;
/*
 * this class was created for
 * reduce complexity and duplication for sonarcloud
 */

import laptop.model.TempUser;
import laptop.utilities.ConnToDb;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateDaoReportClass {

    private  File fd;
    private  File fd1;


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    private static final String RIEPILOGOGIORNALI = "src/main/resources/riepilogoGiornali.txt";
    private static final String RIEPILOGOGIORNALIWEB = "src/main/webapp/WEB-INF/riepilogoGiornali.txt";
    private static final String REPORTLIBRI="src/main/resources/riepilogoLibri.txt";
    private static final String REPORTLIBRIWEB="src/main/webapp/WEB-INF/riepilogoLibri.txt";
    private static final String RIEPILOGORIVISTE="src/main/resources/riepilogoRiviste.txt";
    private static final String RIEPILOGORIVISTEWEB="src/main/webapp/WEB-INF/riepilogoRiviste.txt";
    private static final String TXT_FILE_NAME="src/main/resources/riepilogoUtenti.txt";
    private static final String TXT_FILE_NAME_WEB="src/main/webapp/WEB-INF/riepilogoUtenti.txt";
    private static final String ECCEZIONE_METODO="Test Eccezione genera report";


    public GenerateDaoReportClass(String type)
    {
        	switch (type)
            {
                case GIORNALE -> {
                    this.fd = new File(RIEPILOGOGIORNALI);
                    this.fd1 = new File(RIEPILOGOGIORNALIWEB);
                }
                case LIBRO -> {
                    this.fd=new File(REPORTLIBRI);
                    this.fd1=new File(REPORTLIBRIWEB);
                }
                case RIVISTA -> {
                    this.fd=new File(RIEPILOGORIVISTE);
                    this.fd1=new File(RIEPILOGORIVISTEWEB);
                }
                case UTENTI->{
                    this.fd=new File(TXT_FILE_NAME);
                    this.fd1=new File(TXT_FILE_NAME_WEB);
                }
                default -> Logger.getLogger("GenerateDaoReportClass").log(Level.SEVERE, " choice not correct !!");




            }

    }
    public GenerateDaoReportClass(){}

    private String path;

    private  static final  String RIVISTA="rivista";
    private  static final String LIBRO="libro";
    private  static final String GIORNALE="giornale";
    private static final String UTENTI="utenti";



    public boolean generateReport(String type) throws IOException {
        boolean status=false;

        switch (type) {
            case LIBRO -> {

                setQuery("select  idLibro ,titolo,copieRimanenti,prezzo  from LIBRO");
                setPath(REPORTLIBRI);
                status = writetoFileLGR(LIBRO, getPath());
            }
            case GIORNALE -> {
                setQuery("select  idGiornale ,titolo,copieRimanenti,prezzo  from GIORNALE");
                setPath(RIEPILOGOGIORNALI);
                status = writetoFileLGR(GIORNALE, getPath());
            }
            case RIVISTA -> {

                setQuery("select  idRivista ,titolo,copieRimanenti,prezzo  from RIVISTA");
                setPath(RIEPILOGORIVISTE);
                status = writetoFileLGR(RIVISTA, getPath());
            }
            case UTENTI-> {

            setQuery("select  * from USERS");
            path = TXT_FILE_NAME;
            status = writeToFileU(path);
         }
            default -> Logger.getLogger("generateReport").log(Level.SEVERE, " query not correct !!");
        }
       return !status;

    }

    private boolean writetoFileLGR(String type,String path) throws IOException {
        boolean status=false;
        if(type.equals(LIBRO)|| type.equals(GIORNALE)||type.equals(RIVISTA)) {

            try (BufferedWriter b = new BufferedWriter(new FileWriter(path))) {
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(getQuery())) {
                    ResultSet rs = prepQ.executeQuery();
                    while (rs.next()) {
                        b.write("Id :\t" + rs.getInt(1) + "titolo :\t" + rs.getString(2) + "ricavo totale :\t" + rs.getInt(3) * rs.getFloat(4) + "\n");

                    }

                } catch (SQLException e) {
                    Logger.getLogger(ECCEZIONE_METODO).log(Level.INFO, "Error in SQL", e);
                }
                b.flush();
            }

            if (!path.isEmpty())
                status = true;
        }

            return status;

    }
    private boolean writeToFileU(String path) throws IOException
    {
        boolean status=false;
        try (BufferedWriter b = new BufferedWriter(new FileWriter(path))) {
            query = "select * from USERS";


            try (Connection conn = ConnToDb.connectionToDB();
                 PreparedStatement prepQ = conn.prepareStatement(query)) {

                ResultSet rs = prepQ.executeQuery();


                while (rs.next()) {
/*
                    TempUser.getInstance().setId(rs.getInt(1));
                    TempUser.getInstance().setIdRuolo(rs.getString(2));
                    TempUser.getInstance().setNomeT(rs.getString(3));
                    TempUser.getInstance().setCognomeT(rs.getString(4));
                    TempUser.getInstance().setEmailT(rs.getString(5));
                    TempUser.getInstance().setDescrizioneT(rs.getString(7));
                    TempUser.getInstance().setDataDiNascitaT(rs.getDate(8).toLocalDate());
                    b.write(TempUser.getInstance().getId() + "\t" + TempUser.getInstance().getIdRuolo() + "\t" + TempUser.getInstance().getNomeT() + "\t" + TempUser.getInstance().getCognomeT() +
                            "\t" + TempUser.getInstance().getEmailT() + "\t" + TempUser.getInstance().getDescrizioneT() + "\t" + TempUser.getInstance().getDataDiNascitaT().toString() + "\n");


 */
                }
            } catch (SQLException e1) {
                Logger.getLogger("lista utenti").log(Level.SEVERE, "\n eccezione ottenuta .", e1);

            }
            b.flush();

        }
        if(!path.isEmpty())
            status=true;
        return status;
    }

    public void checkFilePath(Path path) throws IOException {

        try {
            cleanUp(path);

            if (!fd.exists())
                throw new IOException("file " + fd.getPath() + " not exists -> creating");
            if (fd.exists()) {
                cleanUp(path);
                throw new IOException("file " + fd.getPath() + " -> deleted not exists -> creating");
            }

        } catch (IOException e) {
            Logger.getLogger(ECCEZIONE_METODO).log(Level.INFO, "creating file {0}.", fd1.getPath());

            if (fd.createNewFile()) {
                Logger.getLogger(ECCEZIONE_METODO).log(Level.INFO, "creating file {0}.", fd1.getPath());
            }

        }

    }

    public String getReportView(String type) throws SQLException {
        String reportFinale = null;

        switch (type) {
            case LIBRO -> {
                setQuery("create or replace view reportLibri (id,titolo,ricavoMassimo) as select idLibro,titolo,(copieRimanenti*prezzo) from LIBRO");
                try(Connection conn=ConnToDb.connectionToDB();
                    PreparedStatement prepQ=conn.prepareStatement(getQuery())) {
                    prepQ.executeUpdate();
                }catch (SQLException e)
                {

                    Logger.getLogger("Test report book view ").log(Level.SEVERE, "view book not created!");

                }
                reportFinale=leggiReport(LIBRO);


            }
            case GIORNALE -> {
                setQuery("create or replace view reportGiornali (id,titolo,ricavoMassimo) as select idGiornale,titolo,(copieRimanenti*prezzo) from GIORNALE");
                try(Connection conn=ConnToDb.connectionToDB();
                    PreparedStatement prepQ=conn.prepareStatement(getQuery())) {
                    prepQ.executeUpdate();
                }catch (SQLException e)
                {
                    Logger.getLogger("Test report daily view ").log(Level.SEVERE, "view giornali not created!");

                }
                reportFinale=leggiReport(GIORNALE);
            }
            case RIVISTA -> {
                setQuery("create or replace view reportRiviste (id,titolo,ricavoMassimo) as select idRivista,titolo,(copieRimanenti*prezzo) from RIVISTA");
                try(Connection conn=ConnToDb.connectionToDB();
                    PreparedStatement prepQ=conn.prepareStatement(getQuery())) {
                    prepQ.executeUpdate();
                }catch (SQLException e)
                {
                    Logger.getLogger("Test report magazine view ").log(Level.SEVERE, "view magazine not created!");

                }
                reportFinale=leggiReport(RIVISTA);
            }
            case UTENTI -> {

                    setQuery("create or replace view reportUtenti (id,ruolo,nome,cognome) as select idUser,idRuolo,nome,cognome from USERS");
                    try(Connection conn=ConnToDb.connectionToDB();
                        PreparedStatement prepQ=conn.prepareStatement(getQuery())) {
                        prepQ.executeUpdate();
                    }catch (SQLException e)
                    {
                        Logger.getLogger("Test report users view ").log(Level.SEVERE, "view users not created!");

                    }
                    reportFinale=leggiReport(UTENTI);

            }
            default -> Logger.getLogger("getReportView").log(Level.INFO, "choice for view is wrong");
        }
        return reportFinale;
    }

    private static void cleanUp(Path path) throws  IOException {
        Files.delete(path);
    }
    private String leggiReport(String type) throws SQLException {
        StringBuilder builder = new StringBuilder();
        switch (type) {
            case LIBRO -> setQuery("select * from reportLibri");
            case GIORNALE -> setQuery("select * from reportGiornali");
            case RIVISTA -> setQuery("select * from reportRiviste");
            case UTENTI -> setQuery("select * from reportUtenti");
            default -> Logger.getLogger("leggi report").log(Level.INFO, "choice for view is wrong");

        }



            if(type.equals(LIBRO)||type.equals(GIORNALE)|| type.equals(RIVISTA)) {
                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement prepQ = conn.prepareStatement(getQuery())) {
                    ResultSet rs = prepQ.executeQuery();
                    while (rs.next()) {
                        builder.append("id :");
                        builder.append(rs.getInt(1));
                        builder.append("\t");
                        builder.append("titolo :");
                        builder.append(rs.getString(2));
                        builder.append("\t");
                        builder.append("ricavoMassimo :");
                        builder.append(rs.getInt(1));
                        builder.append("\n");

                    }

                }
            }
            if(type.equals(UTENTI)) {
                try(Connection conn=ConnToDb.connectionToDB();
                    PreparedStatement prepQ=conn.prepareStatement(getQuery()))
                {
                    ResultSet rs= prepQ.executeQuery();
                    while(rs.next())
                    {
                        builder.append("nome :");
                        builder.append(rs.getString(3));
                        builder.append("\t");
                        builder.append("cognome :");
                        builder.append(rs.getString(4));
                        builder.append("\t");
                        builder.append("ruolo :");
                        builder.append(rs.getString(2));
                        builder.append("id :");
                        builder.append(rs.getInt(1));
                        builder.append("\t");
                        builder.append("\n");

                    }
                }
            }

        return builder.toString();
    }

    public int getIdMax(String type)
    {
        int idMax = 0;
        switch (type) {
            case LIBRO -> query = "select count(*) as massimo from LIBRO";
            case GIORNALE -> query = "select count(*) as massimo from GIORNALE";
            case RIVISTA -> query = "select count(*) as massimo from RIVISTA";
            case UTENTI -> query="select count(*) as massimo from USERS";
            default -> Logger.getLogger("idMax").log(Level.INFO, "id max not found");

        }
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query)) {
            
            ResultSet rs=prepQ.executeQuery();
            while (rs.next())
               idMax=rs.getInt("massimo");

        } catch (SQLException e) {
            Logger.getLogger("get idMAx").log(Level.INFO, "id max not avalaible");

        }

        return idMax;
    }
    public void ripristinaOrdine(String type)
    {

        switch (type)
        {
            case LIBRO -> query="ALTER TABLE LIBRO AUTO_INCREMENT = 1";
            case GIORNALE -> query="ALTER TABLE GIORNALE AUTO_INCREMENT =1";
            case RIVISTA -> query="ALTER TABLE RIVISTA AUTO_INCREMENT=1";
            case UTENTI -> query="ALTER TABLE USERS AUTO_INCREMENT=1";
            default -> Logger.getLogger("ripristino ").log(Level.SEVERE," id restore failed");

        }
        try (Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query)){
            prepQ.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger("ripristina ordine").log(Level.INFO, "order managed");

        }
    }




}
