package laptop.database;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Factory;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.utilities.ConnToDb;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibroDao {

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final Factory f;
    private static final String ECCEZIONE = " eccezione ottenuta";
    private static final String LIBRO = "libro";

    private  String query;



    public LibroDao()
    {
        this.f=new Factory();
    }


    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) {
        String[] info =new String[7];
        String[] prezzo =new String[6];

        ObservableList<Libro> catalogo = FXCollections.observableArrayList();

        query = "select * from LIBRO where idLibro=? or idLibro=? or titolo=? or autore=?";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {

            prepQ.setInt(1, l.getId());
            prepQ.setInt(2, vis.getId());
            prepQ.setString(3, l.getTitolo());
            prepQ.setString(4, l.getAutore());



            ResultSet rs = prepQ.executeQuery();
            while (rs.next()) {

                info[0]=rs.getString("titolo");
                info[1]=rs.getString("codIsbn");
                info[2]=rs.getString("editore");
                info[3]=rs.getString("autore");
                info[4]=rs.getString("lingua");
                info[5]=rs.getString("categoria");


                prezzo[0]= String.valueOf(rs.getInt("numeroPagine"));
                prezzo[1]=String.valueOf(rs.getInt("copieRimanenti"));
                prezzo[2]=String.valueOf(rs.getInt("disp"));
                prezzo[3]=String.valueOf(rs.getFloat("prezzo"));
                prezzo[4]=String.valueOf(rs.getInt("idLibro"));


                catalogo.add((Libro) f.creaLibro(info,rs.getDate("dataPubblicazione").toLocalDate(),rs.getString("recensione"),rs.getString("breveDescrizione"),prezzo));



            }
        } catch ( SQLException e) {
            Logger.getLogger("get libro id autore obes").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }


    public ObservableList<Raccolta> getLibroByIdTitoloAutoreRaccolta(Libro l) throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();

        query = "select * from LIBRO where idLibro=? or idLibro=? or titolo=? or autore=?";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query);
             ResultSet rs = prepQ.executeQuery()) {
            while (rs.next()) {

                f.createRaccoltaFinale1(LIBRO, rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));


                f.createRaccoltaFinale2(LIBRO, rs.getInt(2), rs.getInt(10), rs.getInt(12), rs.getFloat(13), rs.getInt(14));


                catalogo.add(f.createRaccoltaFinaleCompleta(LIBRO, rs.getDate(8).toLocalDate(), rs.getString(9), rs.getString(11)));


            }
        } catch (SQLException e) {
            Logger.getLogger("get libri").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }


    public ObservableList<Raccolta> getLibri() {
        ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();

        query = "select * from LIBRO ";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query);
             ResultSet rs = prepQ.executeQuery()) {
            while (rs.next()) {

                f.createRaccoltaFinale1(LIBRO, rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));


                f.createRaccoltaFinale2(LIBRO, rs.getInt(2), rs.getInt(10), rs.getInt(12), rs.getFloat(13), rs.getInt(14));


                catalogo.add(f.createRaccoltaFinaleCompleta(LIBRO, rs.getDate(8).toLocalDate(), rs.getString(9), rs.getString(11)));


            }
        } catch (SQLException e) {
            Logger.getLogger("get libri").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }

    public boolean inserisciLibro(Libro l)
    {
        int row=0;
        query="insert into LIBRO values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ= conn.prepareStatement(query)){

            prepQ.setString(1,l.getTitolo());
            prepQ.setInt(2,l.getNrPagine());
            prepQ.setString(3,l.getCodIsbn());
            prepQ.setString(4,l.getEditore());
            prepQ.setString(5,l.getAutore());
            prepQ.setString(6,l.getLingua());
            prepQ.setString(7,l.getCategoria());
            prepQ.setDate(8, Date.valueOf(l.getDataPubb()));
            prepQ.setString(9,l.getRecensione());
            prepQ.setInt(10,l.getNrCopie());
            prepQ.setString(11,l.getDesc());
            prepQ.setInt(12,l.getDisponibilita());
            prepQ.setFloat(13,l.getPrezzo());
            prepQ.setInt(14,0);

             row= prepQ.executeUpdate();


        } catch (SQLException e) {
            Logger.getLogger("insert libro").log(Level.SEVERE," mysql insert error", e);
        }
        return row == 1;
    }

    public boolean aggiornaLibro(Libro l) {
        int row=0;
        boolean status=false;
        query="update LIBRO set titolo=?,numeroPagine=?,codIsbn=?,editore=?," +
                "autore=?,lingua=?,categoria=?,dataPubblicazione=?," +
                "recensione=?,copieRimanenti=?,breveDescrizione=?,disp=?," +
                "prezzo=? where idLibro=? or idLibro=?";
        try (Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ=conn.prepareStatement(query)){
            prepQ.setString(1,l.getTitolo());
            prepQ.setInt(2,l.getNrPagine());
            prepQ.setString(3,l.getCodIsbn());
            prepQ.setString(4,l.getEditore());
            prepQ.setString(5,l.getAutore());
            prepQ.setString(6,l.getLingua());
            prepQ.setString(7,l.getCategoria());
            prepQ.setDate(8, Date.valueOf(l.getDataPubb()));
            prepQ.setString(9,l.getRecensione());
            prepQ.setInt(10,l.getNrCopie());
            prepQ.setString(11,l.getDesc());
            prepQ.setInt(12,l.getDisponibilita());
            prepQ.setFloat(13,l.getPrezzo());
            prepQ.setInt(14,l.getId());
            prepQ.setInt(15,vis.getId());
           row= prepQ.executeUpdate();



        } catch (SQLException e) {
            Logger.getLogger("update del libro").log(Level.SEVERE," error while updating book", e);
        }
        if(row==1)
            status=true;
        return status;

    }

    public boolean eliminaLibro(Libro l)
    {
        int row = 0;
        boolean status=false;
        query="delete from LIBRO where idLibro=? or idLibro=?";
        try (Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ= conn.prepareStatement(query)){
            
            prepQ.setInt(1,l.getId());
            prepQ.setInt(2,vis.getId());

            row= prepQ.executeUpdate();
            
        } catch (SQLException e) {
            Logger.getLogger("elimina").log(Level.SEVERE," error in mysql delete", e);
        }
        if(row==1)
            status=true;
        return status;
    }
}
