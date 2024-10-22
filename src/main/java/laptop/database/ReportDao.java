package laptop.database;

import com.mysql.cj.xdevapi.PreparableStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.model.Report;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import laptop.utilities.ConnToDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDao {

    private  String query;
    private final static ControllerSystemState vis=ControllerSystemState.getInstance();
    private final Libro l;
    private final LibroDao lD;
    private final Giornale g;
    private final GiornaleDao gD;
    private final Rivista r;
    private final RivistaDao rD;
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    public void insertInReport()
    {

        query="insert into REPORT(tipoOggetto,titolo,nrPezzi,prezzo,totale) values(?,?,?,?,?) ";
        try(Connection conn= ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query)) {


            switch (vis.getType())
            {
                case "libro"->{
                    l.setId(vis.getId());
                    prepQ.setString(1,vis.getType());
                    prepQ.setString(2,lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
                    prepQ.setInt(3,vis.getQuantita());
                    prepQ.setFloat(4,lD.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo());
                    prepQ.setFloat(5,lD.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo()*vis.getQuantita());

                }
                case "giornale"->
                {
                    g.setId(vis.getId());
                    prepQ.setString(1,vis.getType());
                    prepQ.setString(2,gD.getGiornaleIdTitoloAutore(g).get(0).getTitolo());
                    prepQ.setInt(3,vis.getQuantita());
                    prepQ.setFloat(4,gD.getGiornaleIdTitoloAutore(g).get(0).getPrezzo());
                    prepQ.setFloat(5,gD.getGiornaleIdTitoloAutore(g).get(0).getPrezzo()*vis.getQuantita());

                }
                case "rivista"->
                {
                    r.setId(vis.getId());
                    prepQ.setString(1,vis.getType());
                    prepQ.setString(2,rD.getRivistaIdTitoloAutore(r).get(0).getTitolo());
                    prepQ.setInt(3,vis.getQuantita());
                    prepQ.setFloat(4,rD.getRivistaIdTitoloAutore(r).get(0).getPrezzo());
                    prepQ.setFloat(5,rD.getRivistaIdTitoloAutore(r).get(0).getPrezzo()*vis.getQuantita());

                }
                default -> Logger.getLogger("report").log(Level.SEVERE, " error n typw");

            }
            prepQ.execute();

        } catch (SQLException e) {
           Logger.getLogger("report exeption ").log(Level.SEVERE, " sql exception", e);
        }
    }

    public ObservableList<Report> getReportFromDB() {
    /*
      
        query=" SET GLOBAL SQL_MODE=(SELECT REPLACE(@@SQL_MODE,'ONLY_FULL_GROUP_BY',''));";
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.executeUpdate();

        }catch (SQLException e)
        {
           Logger.getLogger(" disable checks").log(Level.SEVERE," checks not disabled" , e);
        }
        */

  	ObservableList<Report> list= FXCollections.observableArrayList();
        query="select idReport,tipoOggetto,titolo,sum(nrPezzi),prezzo,sum(totale) from REPORT group by titolo;";
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ= conn.prepareStatement(query)) {

            ResultSet rs= prepQ.executeQuery();
            while(rs.next())
            {
                Report r=new Report();
                r.setIdReport(rs.getInt(1));
                r.setTipologiaOggetto(rs.getString(2));
                r.setTitoloOggetto(rs.getString(3));
                r.setNrPezzi(rs.getInt(4));
                r.setPrezzo(rs.getFloat(5));
                r.setTotale(rs.getFloat(6));
                list.add(r);
            }
        } catch (SQLException e) {
            Logger.getLogger("genera report from db").log(Level.SEVERE," error in sql ",e);
        }
        return list;
    }

    public ObservableList<Report> getReportFromDBLGR()
    {
        ObservableList<Report> list=FXCollections.observableArrayList();

        query="select idReport,tipoOggetto,titolo,sum(nrPezzi),prezzo,sum(totale) from REPORT  where tipoOggetto=? group by titolo";
        try(Connection conn=ConnToDb.connectionToDB();
            PreparedStatement prepQ= conn.prepareStatement(query)) {

            switch (vis.getType())
            {
                case LIBRO-> prepQ.setString(1,LIBRO);
                case GIORNALE -> prepQ.setString(1,GIORNALE);
                case RIVISTA -> prepQ.setString(1,RIVISTA);
                default -> Logger.getLogger(" report ").log(Level.SEVERE," exception has occured!!");
            }

            ResultSet rs= prepQ.executeQuery();
            while(rs.next())
            {
                Report r=new Report();
                r.setIdReport(rs.getInt(1));
                r.setTipologiaOggetto(rs.getString(2));
                r.setTitoloOggetto(rs.getString(3));
                r.setNrPezzi(rs.getInt(4));
                r.setPrezzo(rs.getFloat(5));
                r.setTotale(rs.getFloat(6));
                list.add(r);
            }
        } catch (SQLException e) {
            Logger.getLogger("genera report from db").log(Level.SEVERE," error in sql ",e);
        }
        return list;
    }


    public ReportDao()
    {
        l=new Libro();
        lD=new LibroDao();
        g=new Giornale();
        gD=new GiornaleDao();
        r=new Rivista();
        rD=new RivistaDao();
    }
}
