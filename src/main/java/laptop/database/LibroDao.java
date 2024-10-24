package laptop.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.model.raccolta.Factory;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.utilities.ConnToDb;

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

        query="insert into LIBRO values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return executeQuery(l,query,null);

    }

    public boolean aggiornaLibro(Libro l) {

        query="update LIBRO set titolo=?,numeroPagine=?,codIsbn=?,editore=?," +
                "autore=?,lingua=?,categoria=?,dataPubblicazione=?," +
                "recensione=?,copieRimanenti=?,breveDescrizione=?,disp=?," +
                "prezzo=? where idLibro=? or idLibro=?";

        return executeQuery(l,null,query);

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

    private String[] retLibro(Libro l)
    {
        String [] appoggio=new String[13];

        appoggio[0]=l.getTitolo();
        appoggio[1]=String.valueOf(l.getNrPagine());
        appoggio[2]=l.getCodIsbn();
        appoggio[3]=l.getEditore();
        appoggio[4]=l.getAutore();
        appoggio[5]=l.getLingua();
        appoggio[6]=l.getCategoria();
        appoggio[7]= String.valueOf(l.getDataPubb());
        appoggio[8]=l.getRecensione();
        appoggio[9]= String.valueOf(l.getNrCopie());
        appoggio[10]=l.getDesc();
        appoggio[11]= String.valueOf(l.getDisponibilita());
        appoggio[12]= String.valueOf(l.getPrezzo());


        return appoggio;
    }

    private boolean executeQuery(Libro l , String query,String query2)
    {
        int row=0;
        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(query)){


            //prendo stessp libro e torno stringa




            prepQ.setString(1,retLibro(l)[0]);
            prepQ.setInt(2, Integer.parseInt(retLibro(l)[1]));
            prepQ.setString(3,retLibro(l)[2]);
            prepQ.setString(4,retLibro(l)[3]);
            prepQ.setString(5,retLibro(l)[4]);
            prepQ.setString(6,retLibro(l)[5]);
            prepQ.setString(7,retLibro(l)[6]);
            prepQ.setDate(8, Date.valueOf(retLibro(l)[7]));
            prepQ.setString(9,retLibro(l)[8]);
            prepQ.setInt(10, Integer.parseInt(retLibro(l)[9]));
            prepQ.setString(11,retLibro(l)[10]);
            prepQ.setInt(12, Integer.parseInt(retLibro(l)[11]));
            prepQ.setFloat(13, Float.parseFloat(retLibro(l)[12]));
            prepQ.setInt(14,0);
            if(query2!=null)
                prepQ.setInt(18,vis.getId());

            row= prepQ.executeUpdate();


        } catch (SQLException e) {
            Logger.getLogger("insert libro").log(Level.SEVERE," mysql insert error", e);
        }
        return row == 1;
    }

}
