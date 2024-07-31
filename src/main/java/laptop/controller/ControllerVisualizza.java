package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csv.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;


public class ControllerVisualizza {
	
	private final LibroDao lD;
	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private final RivistaDao rD;
	private final GiornaleDao gD;
	
	
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;
	private final CsvOggettoDao csv=new CsvOggettoDao();
	
	public ControllerVisualizza() throws IOException {
		l = new Libro();
		g=new Giornale();
		r=new Rivista();
		lD=new LibroDao();
		gD=new GiornaleDao();
		rD=new RivistaDao();
	}
	public void setID(String i)
	{		
		
		vis.setId(Integer.parseInt(i));
	}
	public int getID()
	{
		java.util.logging.Logger.getLogger("Test getId").log(Level.INFO, "id {0}",vis.getId());

		return vis.getId();
	}
	public Libro getDataL(int i) throws SQLException, CsvValidationException, IOException, IdException {
		// imposto che è un libro nel controller
		vis.setId(i);
		l.setId(vis.getId());

		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
            return csv.retrieveAllLibroData(new File("report/reportLibro.csv"),l.getId(),"");
		}
		else 		return  lD.getData(l);
	}
	public Giornale getDataG(int i) throws SQLException
	{
		// imposto che è un libro nel controller
		vis.setId(i);
		g.setId(vis.getId());
		return  gD.getData(g);
	}
	public Rivista getDataR(int i) throws SQLException
	{
		// imposto che è un libro nel controller
		vis.setId(i);
		r.setId(vis.getId());
		return  rD.getData(r);
	}
	
}