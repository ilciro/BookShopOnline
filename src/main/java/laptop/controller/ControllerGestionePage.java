package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;



public class ControllerGestionePage {
	private final RivistaDao rD;
	private final LibroDao lD;
	private final GiornaleDao gD;
	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";

	public void cancella(int id) throws SQLException {
		if (ControllerSystemState.getInstance().getType().equals(LIBRO)) {
			l.setId(id);
			lD.cancella(l);
		} else if (ControllerSystemState.getInstance().getType().equals(GIORNALE)) {
			g.setId(id);
			gD.cancella(g);
		} else if (ControllerSystemState.getInstance().getType().equals(RIVISTA)) {
			r.setId(id);
			rD.cancella(r);
		}
	}


	public ObservableList<Raccolta> getLista(String type)  {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();
		switch (type) {
			case LIBRO:
				catalogo.addAll(lD.getLibri());
				break;
			case GIORNALE:
				catalogo.addAll( gD.getGiornali());
				break;
			case RIVISTA:
				catalogo.addAll( rD.getRiviste());
				break;
			default:
				return catalogo;

		}
		return catalogo;
	}


	
	public ControllerGestionePage() throws IOException {
		rD=new RivistaDao();
		gD=new GiornaleDao();
		lD=new LibroDao();
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
	}
	public String settaHeader(String type) {
		String s;
		switch (type) {
			case LIBRO:
				s = "Benvenuto nella schermata dei libri";
				break;
			case RIVISTA:
				s = "Benvenuto nella schermata dele riviste";
				break;
			case GIORNALE:
				s = "Benvenuto nella schermata dei giornali";
				break;
			default:
				return "";
		}
		return s;
	}



}
