package laptop.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;

public class ControllerUserPage {
	private final ControllerSystemState vis=ControllerSystemState.getInstance();

	
	public void getUtenti() throws IOException {

		 UsersDao.getListaUtenti();
	}
	
	public ControllerUserPage()
	{

		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerUserPage");

	}

	public String getCsvData()
	{
		StringBuilder builder=new StringBuilder();
		try {
			CSVReader reader=new CSVReader(new BufferedReader(new FileReader("report/reportUtente.csv")));
			String[] gVector;
			while((gVector=reader.readNext())!=null){
				builder.append(Arrays.toString(gVector));
				builder.append("\n");
			}

		}catch (CsvValidationException | IOException e)
		{
			e.getCause();
		}
		return builder.toString();
	}

}
