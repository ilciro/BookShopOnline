package laptop.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Report;

import laptop.utilities.ConnToDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDao {

    private  String query;

    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";






    public void reportLGR(String type)
    {
        switch (type)
        {
            case LIBRO->query="create or replace view REPORTL (idProdotto,titolo,categoria,spesaTotale) as select l.idLibro,l.titolo,l.categoria,sum(p.spesaTotale) from LIBRO l join PAGAMENTO  p on l.idLibro=p.idProdotto group by l.idLibro;";
            case GIORNALE->query="create or replace view REPORTG (idProdotto,titolo,categoria,spesaTotale) as select g.idLibro,g.titolo,g.categoria,sum(p.spesaTotale) from GIORNALE g join PAGAMENTO  p on g.idGiornale=p.idProdotto group by g.idGiornale;";
            case RIVISTA->query="create or replace view REPORTR (idProdotto,titolo,categoria,spesaTotale) as select r.idRivista,l.titolo,l.categoria,sum(p.spesaTotale) from RIVISTA r join PAGAMENTO  p on r.idRivista=p.idProdotto group by r.idRivista;";
            default -> Logger.getLogger("report web").log(Level.SEVERE," view not created");
        }
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement preQ=conn.prepareStatement(query))
        {
            preQ.executeUpdate();
        }catch (SQLException e)
        {
            Logger.getLogger("crete view ").log(Level.SEVERE," could not create view reportL!!");
        }
    }
    public ObservableList<Report> report(String type)
    {
        ObservableList<Report> list = FXCollections.observableArrayList();
        switch (type)
        {
            case LIBRO -> query="select * from REPORTL";
            case GIORNALE -> query="select * from REPORTG";
            case RIVISTA->query="select * from REPORTR";
            default -> Logger.getLogger("report").log(Level.SEVERE," type in cot correct !!");
        }
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prep=conn.prepareStatement(query)){
            
            ResultSet rs= prep.executeQuery();
            while(rs.next())
            {
                list.add(addReport(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4)));

            }
            
        } catch (SQLException e) {
            Logger.getLogger(" report ").log(Level.SEVERE," REPORTL is empty");
        }
        return list;
    }
    private Report addReport(int id,String titolo,String categoria,float spesa)
    {
        Report report=new Report();
      
        report.setIdReport(id);
        report.setTitoloOggetto(titolo);
        report.setTipologiaOggetto(categoria);
        report.setTotale(spesa);
        return report;
    }
}