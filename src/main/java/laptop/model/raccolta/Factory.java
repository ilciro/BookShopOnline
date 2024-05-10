package laptop.model.raccolta;

import java.time.LocalDate;
import java.util.logging.Level;

import laptop.exception.IdException;





public class Factory {
	private final MatchParam mp;
	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private static final String LIBRO = "libro";
	private static final String GIORNALE = "giornale";
	private static final String RIVISTA = "rivista";
	//usato per passare paramentri
	
	
	// controlli il tipo

	
	
	public String[] createRaccolta1(String titolo,String isbn,String editore,String autore,String lingua,String categoria)
	{
		 String[] infoGenerali ;

		
		 infoGenerali=mp.popola1(titolo, isbn, editore, autore, lingua, categoria);

		 return infoGenerali;
		 
	}
	public String[] createRaccolta2(int numPag,int nrCopie,int disp,float prezzo,int id)
	{
		 String[] infoCosti;

		 infoCosti= mp.popola2(numPag, nrCopie, disp, prezzo,id);

		 return infoCosti;
	}
	
	
	public void createRaccoltaFinale1(String tipologiaO,String titolo,String isbn,String editore,String autore,String lingua,String categoria)
	{
		if(tipologiaO.equals(LIBRO))
		{
				l.setInfoGenerali(createRaccolta1(titolo, isbn, editore, autore, lingua,categoria));
		}
		if(tipologiaO.equals(GIORNALE))
		{

				g.setInfoGenerali(createRaccolta1(titolo, null, editore, null, lingua, categoria));

		}
		if(tipologiaO.equals(RIVISTA))
		{
				r.setInfoGenerali(createRaccolta1(titolo, null, editore, autore, lingua, categoria));
		}
		
	}
	
	public void createRaccoltaFinale2(String tipologiaO,int numPag,int nrCopie,int disp,float prezzo,int id)
	{
		if(tipologiaO.equals(LIBRO))	
		{
				l.setInfoCostiDisp(createRaccolta2(numPag,nrCopie,disp,prezzo,id));
				
		}
		if (tipologiaO.equals(GIORNALE))
			{

				g.setCopieRimanenti(nrCopie);
			g.setDisponibilita(disp);
			g.setPrezzo(prezzo);
			g.setId(id);

			
			
			}	
		 if (tipologiaO.equals(RIVISTA))
		{
			r.setDisp(disp);
			r.setPrezzo(prezzo);
			r.setCopieRim(nrCopie);
			r.setId(id);
		}
	}
	
	
	
	public Raccolta createRaccoltaFinaleCompleta(String tipologiaO,LocalDate dataPubb,String recensione,String descrizione)
	{
		switch(tipologiaO)
		{
			case LIBRO:
				l.setDataPubb(dataPubb);
				l.setRecensione(recensione);
				l.setDesc(descrizione);
				return  new Libro(l.getInfoGenerali(),l.getDataPubb(),l.getRecensione(),l.getDesc(),l.getInfoCostiDisp());
			case GIORNALE:
				g.setDataPubb(dataPubb);
				return new Giornale(g.getInfoGenerali(),g.getDataPubb(),g.getCopieRimanenti(),g.getDisponibilita(),g.getPrezzo(),g.getId());
			case RIVISTA:
				r.setDataPubb(dataPubb);
				r.setDescrizione(descrizione);
				return new Rivista(r.getInfoGenerali(), r.getDescrizione(), r.getDataPubb(),r.getDisp(),r.getPrezzo() ,r.getCopieRim(),r.getId());
		
			default:
				try {
					
					throw new IdException("id null -> object not created");
				}catch(IdException e)
				{
					java.util.logging.Logger.getLogger("report libro").log(Level.SEVERE,"\n eccezione ottenuta .",e);

				}
				//crefdvc rds
				return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	public Raccolta creaLibro(String[] info,LocalDate dataPubb,String recensione,String desc,String []infoCosti)
	{

		return new Libro(info,dataPubb,recensione,desc,infoCosti);
	}
	
	public Raccolta creaGiornale(String[] info,LocalDate dataPubb,int nrCopie, int disponibilita, float prezzo, int id)
	{
		return new Giornale(info,dataPubb,nrCopie,disponibilita,prezzo,id);
	}
	public Raccolta creaRivista(String [] info,	String descrizione, LocalDate dataPubb2, int disp, float prezzo, int copieRim,int id)
	{
		return new Rivista(info,descrizione, dataPubb2,disp,prezzo,copieRim,id);
	}


	
	public Factory()
	{
		mp=new MatchParam();
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
	}

}

