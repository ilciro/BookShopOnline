package laptop.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.UsersDao;

public class ControllerReportPage {
	private final LibroDao lD;
	private final GiornaleDao gD;
	private final RivistaDao rD;
	protected String fileLibro = "src/main/resources/riepilogoLibri.txt";
	protected String fileGiornale ="src/main/resources/riepilogoGiornali.txt";
	protected String fileRiviste = "src/main/resources/riepilogoRiviste.txt";
	protected String fileUtenti = "src/main/resources/riepilogoUtenti.txt";

	private void generaReportLibri () throws IOException
	{
		lD.generaReport();
		
	}
	private void generaReportUtenti() throws IOException {
		UsersDao.getListaUtenti();
	}
	private void generaReportRiviste () throws IOException
	{
		rD.generaReport();
		
	}
	private void generaReportGiornali () throws IOException
	{
		gD.generaReport();
		
	}

	public String reportTotale() throws  IOException {
        return leggiLibro() +
				"\n" +
				leggiRivista() +
				"\n" +
				leggiGiornale() +
				"\n" +
				leggiUtenti() +
				"\n";

	}


	/*
	uso queste operazioni er leggere ogni singolo file
	 */
	public String leggiLibro() throws  IOException {
		generaReportLibri();
		StringBuilder builder = new StringBuilder();
		String line;
		try (BufferedReader readerU = new BufferedReader(new FileReader(fileLibro))) {
			while ((line = readerU.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}

		} catch (IOException e) {
			throw new IOException(e);
		}
		return builder.toString();
	}
	public String leggiGiornale() throws  IOException {
		generaReportGiornali();
		StringBuilder builder = new StringBuilder();
		String line;
		try (BufferedReader readerU = new BufferedReader(new FileReader(fileGiornale))) {
			while ((line = readerU.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}

		} catch (IOException e) {
			throw new IOException(e);
		}
		return builder.toString();
	}
	public String leggiRivista() throws  IOException {
		generaReportRiviste();
		StringBuilder builder = new StringBuilder();
		String line;
		try (BufferedReader readerU = new BufferedReader(new FileReader(fileRiviste))) {
			while ((line = readerU.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}

		} catch (IOException e) {
			throw new IOException(e);
		}
		return builder.toString();
	}
	public String leggiUtenti() throws IOException {
		generaReportUtenti();

	StringBuilder builder = new StringBuilder();
		String line;
		try (BufferedReader readerU = new BufferedReader(new FileReader(fileUtenti))) {
			while ((line = readerU.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}

		} catch (IOException e) {
			throw new IOException(e);
		}
		return builder.toString();
	}



	public String reportRaccolta() throws IOException {
		return leggiLibro()+"\n"+leggiGiornale()+"\n"+leggiRivista()+"\n";
	}
	public ControllerReportPage() throws IOException {
		lD=new LibroDao();
		rD=new RivistaDao();
		gD=new GiornaleDao();
	}



}
