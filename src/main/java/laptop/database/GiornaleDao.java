package laptop.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import laptop.controller.ControllerSystemState;
import laptop.model.raccolta.Factory;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;
import laptop.utilities.ConnToDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GiornaleDao {

	private final Factory f;


	private String query;



	private final ControllerSystemState vis = ControllerSystemState.getInstance();
	private static final String GIORNALE = "giornale";
	private static final String ECCEZIONE = "eccezione generata:";


	public GiornaleDao() {
		f = new Factory();


	}


	public ObservableList<Raccolta> getGiornali(){
		ObservableList<Raccolta> catalogo=FXCollections.observableArrayList();
		query = "select * from GIORNALE";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {

				f.createRaccoltaFinale1(GIORNALE, rs.getString(1), null, rs.getString(4), null, rs.getString(4), rs.getString(2));


				f.createRaccoltaFinale2(GIORNALE, 0, rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getInt(9));

				catalogo.add(f.createRaccoltaFinaleCompleta(GIORNALE, rs.getDate(5).toLocalDate(), rs.getString(6), null));


			}
		} catch (SQLException | NullPointerException e) {
			Logger.getLogger("get giornale id").log(Level.INFO, ECCEZIONE, e);
		}
		return catalogo;

	}




	public ObservableList<Giornale> getGiornaleIdTitoloAutore(Giornale g) {
		ObservableList<Giornale> catalogo = FXCollections.observableArrayList();
		String[] info = new String[7];



		query = "select * from GIORNALE where idGiornale=? or idGiornale=? or titolo=? or editore=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setInt(1, g.getId());
			prepQ.setInt(2, vis.getId());
			prepQ.setString(3, g.getTitolo());
			prepQ.setString(4, g.getEditore());

			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {

				String titolo=rs.getString("titolo");
				String categoria=rs.getString("categoria");
				String lingua=rs.getString("lingua");
				String editore=rs.getString("editore");
				LocalDate data= rs.getDate("dataPubblicazione").toLocalDate();
				int copie=rs.getInt("copieRimanenti");
				int disp=rs.getInt("disp");
				float prezzo=rs.getFloat("prezzo");
				info[0] = titolo;
				info[1]="";
				info[2] = editore;
				info[3]="";
				info[4] = lingua;
				info[5] = categoria;
				catalogo.add((Giornale) f.creaGiornale(info, data,copie,disp,prezzo, rs.getInt("idGiornale")));

			}

		} catch (SQLException e) {
			Logger.getLogger("get giornale titolo id").log(Level.INFO, ECCEZIONE, e);
		}
		return catalogo;
	}


	public void aggiornaDisponibilita(Giornale g) {
		//vedere il segno che cambia
		int d = vis.getQuantita();
		int i = g.getCopieRimanenti();
		int rim = i - d;


		query = "update GIORNALE set copieRimanenti=? where  idGiornale=?";

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setInt(1, rim);
			prepQ.setInt(2, g.getId());
			prepQ.executeUpdate();
		} catch (SQLException e) {
			Logger.getLogger("aggiorna disp l").log(Level.INFO, ECCEZIONE, e);
		}


	}

	// Creo il Giornale nel terzo caso d'uso per l'aggiunta manuale
	public boolean creaGiornale(Giornale g) {
		int row = 0;

		query = "INSERT INTO `GIORNALE`"
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setString(1, g.getTitolo());
			prepQ.setString(2, g.getCategoria());
			prepQ.setString(3, g.getLingua());
			prepQ.setString(4, g.getEditore());
			prepQ.setDate(5, Date.valueOf(g.getDataPubb().toString()));
			prepQ.setInt(6, g.getCopieRimanenti());
			prepQ.setInt(7, g.getDisponibilita());
			prepQ.setFloat(8, g.getPrezzo());
			prepQ.setInt(9,0);


			row = prepQ.executeUpdate();


		} catch (SQLException e) {
			Logger.getLogger("creazione giornale").log(Level.INFO, ECCEZIONE, e);
		}


		return row==1;


	}

	public int cancella(Giornale g) throws SQLException {
		int row;
		query = "delete from GIORNALE where idGiornale=? or idGiornale=?";

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setInt(1, g.getId());
			prepQ.setInt(2, vis.getId());
			row = prepQ.executeUpdate();
		}

		Logger.getLogger("Cancella Giornale").log(Level.INFO, "Giornale cancellato {0}", row);
		return row;

	}

	public boolean aggiornaGiornale(Giornale g)  {
		int row = 0;

		query = " UPDATE `GIORNALE`"
				+ "SET"
				+ "`titolo` =?,"
				+ "`categoria` = ?,"
				+ "`lingua` = ?,"
				+ "`editore` = ?,"
				+ "`dataPubblicazione` = ?,"
				+ "`copieRimanenti` = ?,"
				+ "`disp` = ?,"
				+ "`prezzo` = ?"
				+ "WHERE `idGiornale` = ? or idGiornale=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setString(1, g.getTitolo());
			prepQ.setString(2, g.getCategoria());
			prepQ.setString(3, g.getLingua());
			prepQ.setString(4, g.getEditore());
			prepQ.setString(5, g.getDataPubb().toString());
			prepQ.setInt(6, g.getCopieRimanenti());
			prepQ.setInt(7, g.getDisponibilita());
			prepQ.setFloat(8, g.getPrezzo());
			prepQ.setInt(9, g.getId());
			prepQ.setInt(10, vis.getId());


			row = prepQ.executeUpdate();
		} catch (SQLException e) {
			Logger.getLogger("update g").log(Level.INFO, ECCEZIONE, e);
		}
		return row==1;
	}









	public boolean eliminaGiornale(Giornale g)
	{
		int row = 0;

		query="delete from GIORNALE where idGiornale=? or idGiornale=?";
		try (Connection conn=ConnToDb.connectionToDB();
			 PreparedStatement prepQ= conn.prepareStatement(query)){

			prepQ.setInt(1,g.getId());
			prepQ.setInt(2,vis.getId());

			row= prepQ.executeUpdate();

		} catch (SQLException e) {
			Logger.getLogger("elimina").log(Level.SEVERE," error in mysql delete", e);
		}

		return row==1;
	}
}

