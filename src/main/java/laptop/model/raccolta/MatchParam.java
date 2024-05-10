package laptop.model.raccolta;



public class MatchParam {
	
	public String[] popola1(String titolo,String isbn,String editore,String autore,String lingua,String categoria)
	{
		
		String[] appoggio=new String[7];

		
			appoggio[0]=titolo;
			appoggio[1]=isbn;
			appoggio[2]=editore;
			appoggio[3]=autore;
			appoggio[4]=lingua;
			appoggio[5]=categoria;
			
		
		return appoggio;		
		
	}
	
	public String[] popola2(int numPag,int nrCopie,int disp,float prezzo,int id)
	{
		

		String[] appoggio1=new String[5];
		
		 appoggio1[0]=String.valueOf(numPag);
		 appoggio1[1]=String.valueOf(nrCopie);
		 appoggio1[2]=String.valueOf(disp);
		 appoggio1[3]=String.valueOf(prezzo);
		 appoggio1[4]=String.valueOf(id);

		 


		return appoggio1;
		
	}


	
	
	

}
