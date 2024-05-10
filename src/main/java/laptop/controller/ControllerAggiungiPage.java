package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import laptop.database.GiornaleDao;

import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Rivista;



public class ControllerAggiungiPage {

	private final GiornaleDao gD;
	private boolean status = false;
	private final Rivista r;
	private final RivistaDao rD;
	private final LibroDao lD;
	private final ControllerBookData cBD;

	
	//funzione di aggiunta dei libri
	//e verifica dei dati inseriti 
	

	public boolean checkDataG(Giornale g) throws SQLException 
	{
		if(g.getDataPubb()==null )
		{
			return status;
		}
		else
		{
			status = gD.creaGiornale(g);

		}			
		return status;

	}
	public boolean checkDataR(String [] info,  LocalDate data,
			int dispo, float prezzo, int copie, String desc) throws  SQLException {
		
		

		
		if(data!=null )
		{
			
		r.setTitolo(info[0]);
		r.setTipologia(info[1]);
		r.setAutore(info[2]);
		r.setLingua("italiano");
		r.setEditore(info[4]);
		r.setDescrizione(desc);
		r.setDataPubb(data);
		r.setDisp(dispo);
		r.setPrezzo(prezzo);
		r.setCopieRim(copie);

		rD.creaRivista(r);
		
		status = true ;
		}
		
		return status;
	}

	public boolean checkData(String[] info,String recensione,String descrizione,LocalDate data,String[] infoCosti) throws SQLException
	{


        if (infoCosti[1].length() <= 10 && data != null) {

            status = lD.creaLibrio(cBD.checkBookData(info, recensione, descrizione, data, infoCosti));

        }
        return status;
    }
	// qui chiamo la funzione del dao
	
	public ControllerAggiungiPage() throws IOException {
		gD=new GiornaleDao();
		r=new Rivista();
		rD=new RivistaDao();
		lD=new LibroDao();
		cBD=new ControllerBookData();

	}

}
