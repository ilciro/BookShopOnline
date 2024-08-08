package laptop.controller;

import java.time.LocalDate;

import laptop.model.raccolta.Libro;

public class ControllerBookData {
	private final Libro l;

	
	public Libro  checkBookData(String []info,String recensione,String descrizione,LocalDate data,String[] infoCosti)
	{
		l.setTitolo(info[0]);
		l.setNrPagine(Integer.parseInt(infoCosti[0]));
		l.setCodIsbn(infoCosti[1]);
		l.setEditore(info[4]);
		l.setAutore(info[2]);
		l.setLingua(info[3]);
		l.setCategoria(info[5]);
		l.setDataPubb(data);
		l.setRecensione(recensione);
		l.setDesc(descrizione);
		l.setDisponibilita(Integer.parseInt(infoCosti[3]));
		l.setPrezzo(Float.parseFloat(infoCosti[4]));
		l.setNrCopie(Integer.parseInt(infoCosti[5]));
		l.setId(ControllerSystemState.getInstance().getId());



		return l;
	}
	public ControllerBookData()
	{
		l=new Libro();
	}
}
