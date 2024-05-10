package laptop.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.logging.Level;

import laptop.controller.ControllerSystemState;
import laptop.model.raccolta.Factory;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.utilities.ConnToDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibroDao implements DaoInterface{

	private final Factory f;
	private String query;


	private boolean state = false;
	private final ControllerSystemState vis = ControllerSystemState.getInstance();

	private static final String LIBRO = "libro";
	private static final String ECCEZIONE = "ECCEZIONE generata:";

	private static final String REPORTLIBRI="src/main/resources/riepilogoLibri.txt";

	private static final String REPORTLIBRIWEB="src/main/webapp/WEB-INF/riepilogoLibri.txt";
	private final GenerateDaoReportClass gRC;

	public LibroDao() throws IOException {
		f = new Factory();

		gRC=new GenerateDaoReportClass(LIBRO);

	}


	public Libro getData(Libro l) {

		query = "select * from LIBRO where idLibro=? or idLibro=?";

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setInt(1, l.getId());
			prepQ.setInt(2, vis.getId());
			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {
				f.createRaccoltaFinale1(LIBRO, rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));


				f.createRaccoltaFinale2(LIBRO, rs.getInt(2), rs.getInt(10), rs.getInt(12), rs.getFloat(13), rs.getInt(14));

				l = (Libro) f.createRaccoltaFinaleCompleta(LIBRO, rs.getDate(8).toLocalDate(), rs.getString(9), rs.getString(11));


			}
		} catch (SQLException e) {
			java.util.logging.Logger.getLogger("get data").log(Level.INFO, ECCEZIONE, e);
		}
		return l;

	}

	public ObservableList<Raccolta> getLibri() {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();

		query = "select * from LIBRO";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query);
			 ResultSet rs = prepQ.executeQuery()) {
			while (rs.next()) {

				f.createRaccoltaFinale1(LIBRO, rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));


				f.createRaccoltaFinale2(LIBRO, rs.getInt(2), rs.getInt(10), rs.getInt(12), rs.getFloat(13), rs.getInt(14));


				catalogo.add(f.createRaccoltaFinaleCompleta(LIBRO, rs.getDate(8).toLocalDate(), rs.getString(9), rs.getString(11)));


			}
		} catch (SQLException e) {
			java.util.logging.Logger.getLogger("get libri").log(Level.INFO, ECCEZIONE, e);
		}
		return catalogo;
	}

	public ObservableList<Raccolta> getLibriIdTitoloAutore(Libro l) {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();

		query = "select * from LIBRO where idLibro=? or idLibro=? or titolo=? or autore=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setInt(1, l.getId());
			prepQ.setInt(2, vis.getId());
			prepQ.setString(3, l.getTitolo());
			prepQ.setString(4, l.getAutore());

			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {
				f.createRaccoltaFinale1(LIBRO, rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));


				f.createRaccoltaFinale2(LIBRO, rs.getInt(2), rs.getInt(10), rs.getInt(12), rs.getFloat(13), rs.getInt(14));


				catalogo.add(f.createRaccoltaFinaleCompleta(LIBRO, rs.getDate(8).toLocalDate(), rs.getString(9), rs.getString(11)));


			}
		} catch (SQLException e) {
			java.util.logging.Logger.getLogger("get libri obs").log(Level.INFO, ECCEZIONE, e);
		}
		return catalogo;
	}

	public ObservableList<Libro> getLibroIdTitoloAutore(Libro l) {

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
		} catch (SQLException e) {
			java.util.logging.Logger.getLogger("get libro id autore obes").log(Level.INFO, ECCEZIONE, e);
		}
		return catalogo;
	}


	public void aggiornaDisponibilita(Libro l) throws SQLException {
		//vedere il segno che cambia
		int d = vis.getQuantita();
		int i = l.getNrCopie();
		int rim = i - d;


		query = "update LIBRO set copieRimanenti=? where  idLibro=?";

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setInt(1, rim);
			prepQ.setInt(2, l.getId());
			prepQ.executeUpdate();
		} catch (SQLException e) {
			java.util.logging.Logger.getLogger("aggiorna disp l").log(Level.INFO, ECCEZIONE, e);
		}


	}

	// Creo il libro nel terzo caso d'uso per l'aggiunta manuale
	public boolean creaLibrio(Libro l) throws SQLException {

		query = "INSERT INTO `LIBRO`"
				+ "(`titolo`,"
				+ "`numeroPagine`,"
				+ "`codIsbn`,"
				+ "`editore`,"
				+ "`autore`,"
				+ "`lingua`,"
				+ "`categoria`,"
				+ "`dataPubblicazione`,"
				+ "`recensione`,"
				+ " copierimanenti,"
				+ "`breveDescrizione`,"
				+ "`disp`,"
				+ "`prezzo`)"
				+ "VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setString(1, l.getTitolo());
			prepQ.setInt(2, l.getNrPagine());
			prepQ.setString(3, l.getCodIsbn());
			prepQ.setString(4, l.getEditore());
			prepQ.setString(5, l.getAutore());
			prepQ.setString(6, l.getLingua());
			prepQ.setString(7, l.getCategoria());
			prepQ.setDate(8, Date.valueOf(l.getDataPubb().toString()));
			prepQ.setString(9, l.getRecensione());
			//copie vendute
			prepQ.setInt(10, l.getNrCopie());
			prepQ.setString(11, l.getDesc());
			prepQ.setInt(12, l.getDisponibilita());
			prepQ.setFloat(13, l.getPrezzo());
			int row = prepQ.executeUpdate();
			if (row == 1) {
				state = true; // true

			}
		} catch (SQLException e) {

			state = false;
			java.util.logging.Logger.getLogger("crea libro").log(Level.INFO, ECCEZIONE, e);
		}

		return state;

	}

	public int cancella(Libro l) throws SQLException {
		int row;
		query = "delete from LIBRO where idLibro=? or codIsbn=?";


		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setInt(1, l.getId());
			prepQ.setString(2, l.getCodIsbn());
			row = prepQ.executeUpdate();
		}

		java.util.logging.Logger.getLogger("Cancella libro").log(Level.INFO, "libro cancellato {0}", row);
		gRC.ripristinaOrdine(LIBRO);
		return row;

	}

	public boolean aggiornaLibro(Libro l) throws NullPointerException {


		int rowAffected = 0;
		boolean status = false;


		query = " UPDATE LIBRO SET  `titolo` =?, `numeroPagine` = ?, `codIsbn` = ?, `editore` = ?, `autore` = ?, `lingua` = ?, `categoria` = ?, `dataPubblicazione` = ?, `recensione` = ?, `copieRimanenti` = ?, `breveDescrizione` =?, `disp` = ?,`prezzo` = ? WHERE `idLibro`= ? or `idLibro`= ?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setString(1, l.getTitolo());
			prepQ.setInt(2, l.getNrPagine());
			prepQ.setString(3, l.getCodIsbn());
			prepQ.setString(4, l.getEditore());
			prepQ.setString(5, l.getAutore());
			prepQ.setString(6, l.getLingua());
			prepQ.setString(7, l.getCategoria());
			prepQ.setString(8, l.getDataPubb().toString());
			prepQ.setString(9, l.getRecensione());
			prepQ.setInt(10, l.getNrCopie());
			prepQ.setString(11, l.getDesc());
			prepQ.setInt(12, l.getDisponibilita());
			prepQ.setFloat(13, l.getPrezzo());
			prepQ.setInt(14, l.getId());
			prepQ.setInt(15,vis.getId());


			rowAffected = prepQ.executeUpdate();


			if (rowAffected == 1) {
				status = true;
			}


		} catch (SQLException e) {
			java.util.logging.Logger.getLogger("Aggiornamento libro").log(Level.INFO, ECCEZIONE, e);

		}


		java.util.logging.Logger.getLogger("Aggiornamento libro").log(Level.INFO, "row affected {0}", rowAffected);
		return status;

	}

	public void generaReport() throws IOException {


		Path path = Path.of(REPORTLIBRI);
		Path path1 = Path.of(REPORTLIBRIWEB);
		gRC.checkFilePath(path);
		if(Boolean.TRUE.equals(gRC.generateReport(LIBRO)))
			gRC.checkFilePath(path1);
		Files.copy(path, path1, StandardCopyOption.REPLACE_EXISTING);
	}






	public void incrementaDisponibilita(Libro l){
			int d = vis.getQuantita();
			int i = l.getNrCopie();

			int rim = i + d;
			query = "update LIBRO set copieRimanenti= ? where codIsbn=? or idLibro=?";


			try (Connection conn = ConnToDb.connectionToDB();
				 PreparedStatement prepQ = conn.prepareStatement(query)) {
				prepQ.setInt(1, rim);
				prepQ.setString(2, l.getCodIsbn());
				prepQ.setInt(3, l.getId());
				prepQ.executeUpdate();
			} catch (SQLException e) {
				java.util.logging.Logger.getLogger("Test incremeta disp").log(Level.INFO, ECCEZIONE, e);
			}
		}



	public void aggiornaData(Libro l, Date sqlDate) throws SQLException {
		int row;
		query = "update LIBRO set dataPubblicazione=? where idLibro=? or idLibro=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setDate(1, sqlDate);
			prepQ.setInt(2, l.getId());
			prepQ.setInt(3, vis.getId());
			row = prepQ.executeUpdate();

		}

		java.util.logging.Logger.getLogger("aggiorna data").log(Level.INFO, "libri aggiornati {0}.", row);

	}
	public int getIdMax()
	{
		return gRC.getIdMax(LIBRO);
	}



}

